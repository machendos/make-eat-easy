package com.example.make_eat_easy.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.R
import com.example.make_eat_easy.adapters.ProductsAdapter
import com.example.make_eat_easy.databinding.ProductsBinding
import com.example.make_eat_easy.viewmodels.ProductViewModel

class Products : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ProductsBinding = DataBindingUtil.setContentView(this, R.layout.products)
        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val productAdapter = ProductsAdapter(viewModel)

        viewModel.productRepository.measures.observe(this) {
            productAdapter.notifyDataSetChanged()
        }

        viewModel.productsCategoryList.observe(this) {
            productAdapter.notifyDataSetChanged()
        }

        val recyclerView = binding.productsList
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

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
                val draggedPosition = viewHolder.adapterPosition
                val targetPosition = target.adapterPosition

                val list = viewModel.productsCategoryList.value!!

                val draggedElement = list[draggedPosition]
                val targetElement = list[targetPosition]

                val draggedOrder = draggedElement.order
                draggedElement.order = targetElement.order
                targetElement.order = draggedElement.order

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
