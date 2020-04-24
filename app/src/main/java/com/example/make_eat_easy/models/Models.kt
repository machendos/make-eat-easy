package com.example.make_eat_easy.models

class Product(
    productId: String = "",
    productName: String = "",
    measureId: String = "",
    categoryId: String = ""
) {
    val productId = productId
    var productName = productName
    var measureId = measureId
    var categoryId =categoryId
}

class Measure(
    val measureId: Int,
    val measureName: String,
    val parentMeasureId: Int,
    val parentMeasureFactor: Float
)

class Category(
    categoryId: String = "",
    categoryName: String = "",
    order: Int = 0

) {

    val categoryId = categoryId

    val categoryName = categoryName

    val order = order

}
