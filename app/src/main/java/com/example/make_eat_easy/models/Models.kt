package com.example.make_eat_easy.models

class Product(
    val productId: Int,
    val productName: String,
    val measureId: Int,
    val categoryId: Int
)

class Measure(
    val measureId: Int,
    val measureName: String,
    val parentMeasureId: Int,
    val parentMeasureFactor: Float
)

class Category(
    categoryId: String = "",
    categoryName: String = ""
) {


    val categoryId = categoryId

    val categoryName = categoryName


}
