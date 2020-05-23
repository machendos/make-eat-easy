package com.example.make_eat_easy.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.repository.ActionsRepository
import com.example.make_eat_easy.repository.DishesRepository
import java.util.*

class AddActionViewModel : ViewModel() {

//    private val productRepository = ProductsRepository
    private val dishRepository = DishesRepository
    private val actionRepository = ActionsRepository
    val dishesList = dishRepository.dishes
    val actions = actionRepository.actions


    val COOKING_TYPE = 1
    val EATING_TYPE = 2
    val OTHER_TYPE = 3

    var actionType = COOKING_TYPE

    fun setType(type: Int) { actionType = type }

    lateinit var dateTime: Calendar

    fun setTime(calendar: Calendar) { dateTime = calendar }

    fun addAction(duration: Int, dishes: Map<String, String>) {

        val parsedDishes = dishes.filter { it.key !== "" }.map {(dishName, count) ->

            val parsedCount = count.toDoubleOrNull() ?: 0.0

            val dish = dishRepository.dishes.value!!.find { it.dishName == dishName }

            if (dish == null) {
                val dishId = dishRepository
                    .addDish(dishName, null, 0, mutableMapOf(), null)
                    .toString()
                dishId to parsedCount
            } else {
                dish.dishId.toString() to parsedCount.toDouble()
            }

        }.toMap().toMutableMap()

        Log.d("asdasd", "Now call in repository")
        actionRepository.addAction(actionType, dateTime, duration, parsedDishes)


    }

//    private fun onChangeProductDish() {
//
//        productsDishesList.value =
//            productRepository.products.value!!
//                .map { DishProduct(it) } as MutableList
//
//        val convertedDishes = dishRepository
//            .dishes
//            .value!!
//            .map { DishProduct(it) } as MutableList
//
//        productsDishesList.value!!.addAll(convertedDishes)
//        productsDishesList.value = productsDishesList.value
//
//    }

//    init {
//
//        productsDishesList.value =
//            productRepository
//                .products
//                .value!!
//                .map { DishProduct(it) } as MutableList
//
//        dishRepository
//            .dishes
//            .value!!
//            .forEach { productsDishesList.value!!.add(DishProduct(it)) }
//        productsDishesList.value = productsDishesList.value
//
//        productsDishesList
//            .addSource(productRepository.products) { onChangeProductDish() }
//        productsDishesList
//            .addSource(dishRepository.dishes) { onChangeProductDish() }
//
//    }
}
