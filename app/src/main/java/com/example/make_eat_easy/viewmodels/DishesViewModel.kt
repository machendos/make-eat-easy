package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.models.CategoryDish
import com.example.make_eat_easy.models.Dish
import com.example.make_eat_easy.repository.DishesRepository
import com.example.make_eat_easy.repository.ProductsRepository

class DishesViewModel : ViewModel() {

    val dishesRepository = DishesRepository
    private val productRepository = ProductsRepository

    lateinit var cashedDish: Dish

    val dishesCategoryList = MediatorLiveData<MutableList<CategoryDish>>()

    init {

        fun onChangeDishCategory() {
            dishesCategoryList.value =
                dishesRepository.categories.value!!.map { category ->
                    CategoryDish(category)
                } as MutableList

            val convertedDishes =
                dishesRepository.dishes.value!!.map { CategoryDish(it) } as MutableList

            dishesCategoryList.value!!.addAll(convertedDishes)
            dishesCategoryList.value!!.sortBy { it.order }
            dishesCategoryList.value = dishesCategoryList.value
        }

        dishesCategoryList.addSource(dishesRepository.categories) { onChangeDishCategory() }
        dishesCategoryList.addSource(dishesRepository.dishes) { onChangeDishCategory() }
    }

    fun addDish(
        dishName: String,
        categoryName: String,
        cookDuration: String,
        products: Map<String, String>
    ) {

        var duration = 0
        try {
            duration = cookDuration.toInt()
        } catch (err: Error) {
        }

        val filtredProducts = products.map { (productName, count) ->

            var parsedCount = 0.0
            try {
                parsedCount = count.toDouble()
            } catch (e: Throwable) {
            }

            val product = productRepository.products.value!!.find { it.productName == productName }

            if (product == null) {
                val productId = productRepository
                    .addProduct(productName, null, 1, null).toString()
                productId to parsedCount
            } else {
                product.productId.toString() to count.toDouble()
            }

        }.toMap().toMutableMap()

        if (categoryName == "") {
            val order = dishesCategoryList.value!![dishesCategoryList.value!!.lastIndex].order + 1
            dishesRepository.addDish(dishName, null, duration, filtredProducts, order)
            return
        }

        val category =
            dishesRepository.categories.value!!.find { it.categoryName == categoryName }

        if (category == null) {
            val (categoryId, categoryOrder) = dishesRepository.addCategory(categoryName)
            dishesRepository.addDish(
                dishName,
                categoryId,
                duration,
                filtredProducts,
                categoryOrder + 1
            )
        } else {
            val elementsForIncrement = mutableListOf<CategoryDish>()
            var currOrder = category.order + 1
            dishesCategoryList.value!!.forEach {
                if (it.order == currOrder) {
                    currOrder = it.order + 1
                    elementsForIncrement.add(it)
                }
            }
            dishesRepository.incrementOrder(elementsForIncrement)
            dishesRepository.addDish(
                dishName,
                category.categoryId,
                duration,
                filtredProducts,
                category.order + 1
            )
        }
    }

    fun deleteDish(dishId: Int) {
        cashedDish = dishesRepository.dishes.value!!.find { it.dishId == dishId }!!
        dishesRepository.deleteDish(dishId)
    }

    fun unremoveDish() = dishesRepository.unremoveDish(cashedDish)

    fun addCategory(categoryName: String) = dishesRepository.addCategory(categoryName)
}
