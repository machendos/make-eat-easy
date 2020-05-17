package com.example.make_eat_easy.models

class Dish(
    dishId: Int = 0,
    dishName: String = "",
    categoryId: Int? = 0,
    cookDuration: Int = 0,
    products: MutableMap<Int, Double> = mutableMapOf<Int, Double>(),
    order: Int = 0
) {
    var dishId = dishId
    var dishName = dishName
    var categoryId = categoryId
    val cookDuration = cookDuration
    var products = products
    var order = order
}


class CategoryDish() {
    var isDish: Boolean = true

    var dishId: Int = 0
    var dishName: String = ""
    var cookDuration: Int = 0
    var products: MutableMap<Int, Double> = mutableMapOf<Int, Double>()

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

class NecessaryProduct(
    val productName: String,
    val count: Double
)