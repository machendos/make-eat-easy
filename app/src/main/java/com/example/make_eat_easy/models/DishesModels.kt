package com.example.make_eat_easy.models

class CategoryDish() {
    var isDish: Boolean = true

    var dishId: Int = 0
    var dishName: String = ""
    var cookDuration: Int = 0
    var products: MutableMap<String, Double> = mutableMapOf()

    var categoryName: String = ""

    var categoryId: Int? = 0

    var order: Int = 0

    constructor(category: Category): this() {
        isDish = false

        categoryId = category.categoryId
        categoryName = category.categoryName
        order = category.order
    }

    constructor(dish: Dish): this() {
        dishId = dish.dishId
        dishName = dish.dishName
        cookDuration = dish.cookDuration
        products = dish.products

        categoryId = dish.categoryId

        order = dish.order
    }
}
