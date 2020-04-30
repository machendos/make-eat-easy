package com.example.make_eat_easy.models

class Product(
    productId: Int = 0,
    productName: String = "",
    measureId: Int = 0,
    categoryId: Int = 0
) {
    val productId = productId
    var productName = productName
    var measureId = measureId
    var categoryId =categoryId
}

class Measure(
    measureId: Int = 0,
    measureName: String = "",
    parentMeasureId: Int? = 0,
    parentMeasureFactor: Double = 0.0
) {
    var measureId = measureId
    var measureName = measureName
    var parentMeasureId = parentMeasureId
    var parentMeasureFactor = parentMeasureFactor
}

class Category(
    categoryId: Int = 0,
    categoryName: String = "",
//    order: Int = 0

) {

    var categoryId = categoryId

    var categoryName = categoryName

//    val order = order

}
