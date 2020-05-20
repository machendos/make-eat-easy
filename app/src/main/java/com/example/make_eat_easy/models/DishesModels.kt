package com.example.make_eat_easy.models

class Dish(
    var dishId: Int = 0,
    var dishName: String = "",
    var categoryId: Int? = 0,
    var cookDuration: Int = 0,
    var products: MutableMap<String, Double> = mutableMapOf<String, Double>(),
    var order: Int = 0
)

class CategoryDish() {
    var isDish: Boolean = true

    var dishId: Int = 0
    var dishName: String = ""
    var cookDuration: Int = 0
    var products: MutableMap<String, Double> = mutableMapOf<String, Double>()

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

class DishProduct() {

    var isDish: Boolean = true

    var dishId: Int = 0
    var dishName: String = ""
    var cookDuration: Int = 0
    var products: MutableMap<String, Double> = mutableMapOf<String, Double>()

    var productId: Int = 0
    var productName: String = ""

    constructor(product: Product) : this() {
        isDish = false

        productId = product.productId
        productName = product.productName
    }

    constructor(dish: Dish) : this() {
        dishId = dish.dishId
        dishName = dish.dishName
        cookDuration = dish.cookDuration
        products = dish.products

    }
}