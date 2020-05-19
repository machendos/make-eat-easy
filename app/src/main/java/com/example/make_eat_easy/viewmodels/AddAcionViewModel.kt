package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.models.DishProduct
import com.example.make_eat_easy.repository.DishesRepository
import com.example.make_eat_easy.repository.ProductsRepository

class AddAcionViewModel: ViewModel() {

    val productRepository = ProductsRepository
    val dishRepository = DishesRepository
    val productsDishesList = MediatorLiveData<MutableList<DishProduct>>()

    fun onChangeProductDish() {
        productsDishesList.value = productRepository.products.value!!.map { DishProduct(it) } as MutableList
        val convertedDishes = dishRepository
            .dishes
            .value!!
            .map { DishProduct(it) } as MutableList

        productsDishesList.value!!.addAll(convertedDishes)
        productsDishesList.value = productsDishesList.value

    }

    init {

        productsDishesList.value = mutableListOf()

        productsDishesList.addSource(productRepository.products) {onChangeProductDish()}
        productsDishesList.addSource(dishRepository.dishes) {onChangeProductDish()}


    }

}
