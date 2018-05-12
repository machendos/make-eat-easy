package com.example.make_eat_easy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.adapters.ProductsAdapter
import com.example.make_eat_easy.databinding.AddCategoryDialogBinding
import com.example.make_eat_easy.databinding.AddProductDialogBinding
import com.example.make_eat_easy.databinding.ProductEditDialogBinding
import com.example.make_eat_easy.databinding.ProductsBinding
import com.example.make_eat_easy.viewmodels.ProductViewModel

class Products : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ProductsBinding = DataBindingUtil.setContentView(this, R.layout.products)
        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val productAdapter = ProductsAdapter(viewModel)
        productAdapter.productClickListener = {


            val bindingDialog: ProductEditDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.product_edit_dialog,
                null,
                false
            )

            val oldProduct = viewModel.productsCategoryList.value!![it]
            bindingDialog.product = viewModel.productsCategoryList.value!![it]

            val dialog = AlertDialog.Builder(this)
                .setView(bindingDialog.root)
                .setPositiveButton("Ok") { _, _ ->
                    val newProductName = bindingDialog.productName.text.toString()
                    viewModel.updateProduct(oldProduct.productId, newProductName)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()


            bindingDialog.productDeleteButton.setOnClickListener {
                viewModel.deleteProduct(oldProduct.productId)
                dialog.dismiss()

            }

            Toast.makeText(
                this,
                viewModel.productsCategoryList.value!![it].productName,
                Toast.LENGTH_LONG
            ).show()
        }



        viewModel.productRepository.measures.observe(this) {
            productAdapter.notifyDataSetChanged()
        }

        viewModel.productsCategoryList.observe(this) {
            productAdapter.notifyDataSetChanged()
        }

        binding.goToAddProduct.setOnClickListener {

            val bindingDialog: AddProductDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.add_product_dialog, null, false
            )

            bindingDialog.categoryName.setAdapter(ArrayAdapter<String>(
                this,
                R.layout.element_autocomplete,
                R.id.autocomplete_element,
                viewModel.productRepository.categories.value!!.map { it.categoryName }
            ))

            bindingDialog.measureName.setAdapter(ArrayAdapter<String>(
                this,
                R.layout.element_autocomplete,
                R.id.autocomplete_element,
                viewModel.productRepository.measures.value!!.map { it.measureName }
            ))

            AlertDialog.Builder(this)
                .setView(bindingDialog.root)
                .setTitle("Add new product")
                .setPositiveButton("Add") { _, _ ->

                    viewModel.addProduct(
                        bindingDialog.productName.text.toString(),
                        bindingDialog.measureName.text.toString(),
                        bindingDialog.categoryName.text.toString()
                    )


                    Toast.makeText(
                        this,
                        bindingDialog.productName.text.toString(),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }.setNegativeButton("Cancel") { _, _ -> }
                .show()
        }

        binding.goToAddCategory.setOnClickListener {

            val bindingDialog: AddCategoryDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.add_category_dialog, null, false
            )

            AlertDialog.Builder(this)
                .setView(bindingDialog.root)
                .setTitle("Add new category")
                .setPositiveButton("Add") { _, _ ->

                    viewModel.addCategory(bindingDialog.addCategoryName.text.toString())

                }.setNegativeButton("Cancel") { _, _ -> }
                .show()
        }

        val recyclerView = binding.productsList
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return if (viewHolder.itemViewType == ProductsAdapter.CATEGORY) 0 else ItemTouchHelper.Callback.makeMovementFlags(
                    dragFlags,
                    swipeFlags
                )
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val targetPosition = target.adapterPosition
                if (targetPosition == 0) return false
                val draggedPosition = viewHolder.adapterPosition

                val list = viewModel.productsCategoryList.value!!

                val draggedElement = list[draggedPosition]
                val targetElement = list[targetPosition]

                val draggedOrder = draggedElement.order
                draggedElement.order = targetElement.order
                targetElement.order = draggedOrder

                list[draggedPosition] = targetElement
                list[targetPosition] = draggedElement

                productAdapter.notifyItemMoved(draggedPosition, targetPosition)
                return false
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    var currCaregoryId: Int? = null
                    viewModel.productsCategoryList.value!!.forEach {
                        if (it.isProduct) it.categoryId = currCaregoryId
                        else currCaregoryId = it.categoryId
                    }

                    viewModel.productRepository.updateProductOrders(viewModel.productsCategoryList.value!!)
                }
                super.onSelectedChanged(viewHolder, actionState)
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val id = viewModel
                    .productsCategoryList
                    .value!![viewHolder.adapterPosition]
                    .productId

                viewModel.productRepository.deleteProduct(id)

            }

        }).attachToRecyclerView(recyclerView)

    }
}