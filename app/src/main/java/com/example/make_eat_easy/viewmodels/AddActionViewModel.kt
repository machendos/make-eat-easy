package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.models.DishProduct
import com.example.make_eat_easy.repository.DishesRepository
import com.example.make_eat_easy.repository.ProductsRepository

class AddActionViewModel : ViewModel() {

    private val productRepository = ProductsRepository
    private val dishRepository = DishesRepository
    val productsDishesList = MediatorLiveData<MutableList<DishProduct>>()


    val COOKING_TYPE = 1
    val EATING_TYPE = 2
    val OTHER_TYPE = 3

    var actionType = COOKING_TYPE

    private fun onChangeProductDish() {

        productsDishesList.value =
            productRepository.products.value!!
                .map { DishProduct(it) } as MutableList

        val convertedDishes = dishRepository
            .dishes
            .value!!
            .map { DishProduct(it) } as MutableList

        productsDishesList.value!!.addAll(convertedDishes)
        productsDishesList.value = productsDishesList.value

    }

    init {

        productsDishesList.value =
            productRepository
                .products
                .value!!
                .map { DishProduct(it) } as MutableList

        dishRepository
            .dishes
            .value!!
            .forEach { productsDishesList.value!!.add(DishProduct(it)) }
        productsDishesList.value = productsDishesList.value

        productsDishesList
            .addSource(productRepository.products) { onChangeProductDish() }
        productsDishesList
            .addSource(dishRepository.dishes) { onChangeProductDish() }

    }
}
