package com.example.make_eat_easy.models

class Dish(
    var dishId: Int = 0,
    var dishName: String = "",
    var categoryId: Int? = 0,
    var cookDuration: Int = 0,
    var products: MutableMap<String, Double> = mutableMapOf<String, Double>(),
    var order: Int = 0
)