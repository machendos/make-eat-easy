package com.example.make_eat_easy.viewmodels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.firebase.ProductsRepository
import com.example.make_eat_easy.models.CategoryProduct

class ProductViewModel : ViewModel() {

    val productRepository = ProductsRepository()

    val productsCategoryList = MediatorLiveData<MutableList<CategoryProduct>>()

    init {

        productsCategoryList.value = mutableListOf()

        productsCategoryList.addSource(productRepository.categories) {

            Log.d("asdasd", "viewModel categories")
            Log.d("asdasd", it.joinToString(transform = {product -> product.toString() }))

            it.forEach { updatedCategory ->
                val index = productsCategoryList.value!!.indexOfFirst { oldCategory ->
                    !oldCategory.isProduct && oldCategory.order >= updatedCategory.order
                }
                productsCategoryList.value!!.add(
                    if (index == -1) 0 else index,
                    CategoryProduct(updatedCategory)
                )
                Log.d("asdasd", productsCategoryList.value!!.joinToString(transform = {product -> product.toString() }))
            }

            productsCategoryList.value = productsCategoryList.value
        }

        productsCategoryList.addSource(productRepository.products) {

            Log.d("asdasd", "viewModel products")
            Log.d("asdasd", it.joinToString(transform = {product -> product.toString() }))

            it.forEach { updatedProduct ->
                val index = productsCategoryList.value!!.indexOfFirst { oldProduct ->
                    oldProduct.isProduct && oldProduct.order >= updatedProduct.order && oldProduct.categoryId == updatedProduct.categoryId ||
                            !oldProduct.isProduct && oldProduct.categoryId > updatedProduct.categoryId
                }
                productsCategoryList.value!!.add(
                    if (index == -1) 0 else index,
                    CategoryProduct(updatedProduct)
                )

                Log.d("asdasd", productsCategoryList.value!!.joinToString(transform = {product -> product.toString() }))

                productsCategoryList.value = productsCategoryList.value
            }

        }
    }

}