package com.example.make_eat_easy.models

class Product(
    var productId: Int = 0,
    var productName: String = "",
    var measureId: Int = 0,
    var categoryId: Int? = 0,
    var order: Int = 0
)