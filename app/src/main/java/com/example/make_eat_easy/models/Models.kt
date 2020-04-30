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

class CategoryProduct() {

    var isProduct: Boolean = true

    var productId: Int = 0
    var productName: String = ""
    var measureId: Int = 0

    var categoryName: String = ""

    var categoryId: Int = 0

    constructor(category: Category) : this() {
        isProduct = false

        categoryId = category.categoryId
        categoryName = category.categoryName
    }

    constructor(product: Product) : this() {
        var productId = product.productId
        var productName = product.productName
        var measureId = product.measureId
        var categoryId = product.categoryId
    }


}
