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

        productsCategoryList.addSource(productRepository.categories) {

            productsCategoryList.value = it.map { category -> CategoryProduct(category) } as MutableList

            productRepository.products.value!!.forEach { product ->

                var targetIndex = productsCategoryList.value!!.size

                run loop@ {
                    productsCategoryList.value!!.forEachIndexed { index, element ->

                        if (
                            element.isProduct && element.order >= product.order && element.categoryId == product.categoryId ||
                            index > 0 && !element.isProduct && productsCategoryList.value!![index -1].categoryId == product.categoryId
                        ) {
                            targetIndex = index
                            return@loop
                        }
                    }

                }

                productsCategoryList.value!!.add(targetIndex, CategoryProduct(product))
            }

            productsCategoryList.value = productsCategoryList.value
        }

        productsCategoryList.addSource(productRepository.products) {

            productsCategoryList.value = productRepository.categories.value!!.map { category -> CategoryProduct(category) } as MutableList

            productRepository.products.value!!.forEach { product ->

                var targetIndex = productsCategoryList.value!!.size

                run loop@ {
                    productsCategoryList.value!!.forEachIndexed { index, element ->

                        if (
                            element.isProduct && element.order >= product.order && element.categoryId == product.categoryId ||
                            index > 0 && !element.isProduct && productsCategoryList.value!![index -1].categoryId == product.categoryId
                        ) {
                            targetIndex = index
                            return@loop
                        }
                    }

                }

                productsCategoryList.value!!.add(targetIndex, CategoryProduct(product))
            }

            productsCategoryList.value = productsCategoryList.value
        }
    }

}