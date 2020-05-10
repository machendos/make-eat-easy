package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.firebase.ProductsRepository
import com.example.make_eat_easy.models.CategoryProduct

class ProductViewModel : ViewModel() {

    val productRepository = ProductsRepository()

    val productsCategoryList = MediatorLiveData<MutableList<CategoryProduct>>()

    init {

        productsCategoryList.value = mutableListOf()

        fun onChangeProductCategory() {
            productsCategoryList.value =
                productRepository
                    .categories
                    .value!!
                    .map {
                            category -> CategoryProduct(category)
                    } as MutableList

            val convertedProduct = productRepository
                .products
                .value!!
                .map { product -> CategoryProduct(product) } as MutableList

            productsCategoryList.value!!.addAll(convertedProduct)

            productsCategoryList.value!!.sortBy { it.order }
            productsCategoryList.value = productsCategoryList.value

        }

        productsCategoryList.addSource(productRepository.categories) {
            onChangeProductCategory()
        }

        productsCategoryList.addSource(productRepository.products) {
            onChangeProductCategory()
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