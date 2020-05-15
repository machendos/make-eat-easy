package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.firebase.DishesRepository
import com.example.make_eat_easy.firebase.ProductsRepository
import com.example.make_eat_easy.models.CategoryDish

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

}
