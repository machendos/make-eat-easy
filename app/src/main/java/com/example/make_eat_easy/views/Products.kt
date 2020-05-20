package com.example.make_eat_easy.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.make_eat_easy.R
import com.example.make_eat_easy.adapters.ProductsAdapter
import com.example.make_eat_easy.databinding.ProductsBinding
import com.example.make_eat_easy.dialogues.AddCategoryDialog
import com.example.make_eat_easy.dialogues.AddProductDialog
import com.example.make_eat_easy.dialogues.EditProductDialog
import com.example.make_eat_easy.util.ProductTouchHelper
import com.example.make_eat_easy.viewmodels.ProductViewModel

class Products : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ProductsBinding =
            DataBindingUtil.setContentView(this, R.layout.products)
        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val productsAdapter = ProductsAdapter(viewModel)

        viewModel.productRepository.measures.observe(this) {
            productsAdapter.notifyDataSetChanged()
        }

        viewModel.productsCategoryList.observe(this) {
            productsAdapter.notifyDataSetChanged()
        }

        val editProductDialog = EditProductDialog(this, viewModel, binding.productsList)
        val addProductDialog = AddProductDialog(this, viewModel)
        val addCategoryDialog = AddCategoryDialog(this, viewModel)

        productsAdapter.setProductClickListener { editProductDialog.show(it) }
        binding.goToAddProduct.setOnClickListener { addProductDialog.show() }
        binding.goToAddCategory.setOnClickListener { addCategoryDialog.show() }

        val recyclerView = binding.productsList
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productsAdapter

        ItemTouchHelper(ProductTouchHelper(viewModel, productsAdapter, binding.productsList)).attachToRecyclerView(
            recyclerView
        )

    }
}