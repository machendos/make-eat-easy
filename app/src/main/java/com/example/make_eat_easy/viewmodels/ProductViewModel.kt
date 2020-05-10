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

        productsCategoryList.addSource(productRepository.categories) { it ->

            productsCategoryList.value =
                it.map { category -> CategoryProduct(category) } as MutableList
            productsCategoryList.value!!.addAll(productRepository.products.value!!.map { product ->
                CategoryProduct(
                    product
                )
            } as MutableList)
            productsCategoryList.value!!.sortBy { it.order }

            productsCategoryList.value = productsCategoryList.value
        }

        productsCategoryList.addSource(productRepository.products) {

            productsCategoryList.value =
                productRepository.categories.value!!.map { category -> CategoryProduct(category) } as MutableList
            productsCategoryList.value!!.addAll(productRepository.products.value!!.map { product ->
                CategoryProduct(
                    product
                )
            } as MutableList)
            productsCategoryList.value!!.sortBy { it.order }

            productsCategoryList.value = productsCategoryList.value
        }
    }

    fun addProduct(productName: String, measureName: String, categoryName: String) {

        val measure =
            productRepository.measures.value!!.find { it.measureName == measureName }
        val measureId = measure?.measureId ?: productRepository.addMeasure(measureName)

        if (categoryName == "") {
            val order =
                productsCategoryList.value!![productsCategoryList.value!!.lastIndex].order + 1
            productRepository.addProduct(productName, null, measureId, order)
            return
        }

        val category =
            productRepository.categories.value!!.find { it.categoryName == categoryName }

        if (category == null) {
            val (categoryId, categoryOrder) = productRepository.addCategory(categoryName)
            productRepository.addProduct(productName, categoryId, measureId, categoryOrder + 1)

        } else {
            val elementsForIncrement = mutableListOf<CategoryProduct>()
            var currOrder = category.order + 1
            productsCategoryList.value!!.forEach {
                if (it.order == currOrder) {
                    Log.d("asdasd->>index", it.productName)
                    currOrder = it.order + 1
                    elementsForIncrement.add(it)
                }
            }
            productRepository.incrementOrder(elementsForIncrement)
            productRepository.addProduct(
                productName,
                category.categoryId,
                measureId,
                category.order + 1
            )


        }


    }

}