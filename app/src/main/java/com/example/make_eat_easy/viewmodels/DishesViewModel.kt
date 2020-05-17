package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.models.CategoryDish
import com.example.make_eat_easy.repository.DishesRepository
import com.example.make_eat_easy.repository.ProductsRepository

class DishesViewModel : ViewModel() {

    val dishesRepository = DishesRepository()
    val productRepository = ProductsRepository()

    val dishesCategoryList = MediatorLiveData<MutableList<CategoryDish>>()

    init {

        dishesCategoryList.value = mutableListOf()

        fun onChangeDishCategory() {
            dishesCategoryList.value =
                dishesRepository.categories.value!!.map {
                    category -> CategoryDish(category)
                } as MutableList

            val convertedDishes = dishesRepository.dishes.value!!.map { CategoryDish(it) } as MutableList

            dishesCategoryList.value!!.addAll(convertedDishes)
            dishesCategoryList.value!!.sortBy { it.order }
            dishesCategoryList.value = dishesCategoryList.value
        }

        dishesCategoryList.addSource(dishesRepository.categories) { onChangeDishCategory() }
        dishesCategoryList.addSource(dishesRepository.dishes) { onChangeDishCategory() }
    }

    fun addDish(dishName: String, categoryName: String?, cookDuration: String,  products: Map<String, String>) {


        var duration = 0
        try { duration = cookDuration.toInt() } catch (err: Error) { }

        val filtredProducts = products.map { (productName, count) ->
            val product = productRepository.products.value!!.find { it.productName ==  productName}
            if (product == null) {
//                TODO: ADD NEW PRODUCT
//                val productId = productRepository.addProduct(productName, null, 1, null)
                1 to 2.2
            } else {
                product.productId to count.toDouble()
            }
        }.toMap().toMutableMap()

        if (categoryName == "") {
            val order = dishesCategoryList.value!![dishesCategoryList.value!!.lastIndex].order + 1
            dishesRepository.addDish(dishName, null, duration, filtredProducts, order)
            return
        }

        val category = dishesRepository.categories.value!!.find { it.categoryName == categoryName }

        if (category == null) {
//            Add category
//            Add dish
        } else {
            val elementsForIncrement = mutableListOf<CategoryDish>()
            var currOrder = category.order + 1
            dishesCategoryList.value!!.forEach {
                if (it.order == currOrder) {
                    currOrder = it.order + 1
                    elementsForIncrement.add(it)
                }
            }
//            increment order
//            add product

        }


    }

}
