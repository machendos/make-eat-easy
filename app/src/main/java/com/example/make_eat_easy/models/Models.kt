package com.example.make_eat_easy.models

class Product(
    productId: Int = 0,
    productName: String = "",
    measureId: Int = 0,
    categoryId: Int? = 0,
    order: Int = 0
) {
    val productId = productId
    var productName = productName
    var measureId = measureId
    var categoryId = categoryId
    var order = order
    override fun toString(): String {
        return "Product(productId=$productId, productName='$productName', measureId=$measureId, categoryId=$categoryId, order=$order)"
    }
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
    override fun toString(): String {
        return "Measure(measureId=$measureId, measureName='$measureName', parentMeasureId=$parentMeasureId, parentMeasureFactor=$parentMeasureFactor)"
    }
}

class Category(
    categoryId: Int = 0,
    categoryName: String = "",
    order: Int = 0

) {

    var categoryId = categoryId

    var categoryName = categoryName

    var order = order
    override fun toString(): String {
        return "Category(categoryId=$categoryId, categoryName='$categoryName', order=$order)"
    }

}

class CategoryProduct() {

    var isProduct: Boolean = true

    var productId: Int = 0
    var productName: String = ""
    var measureId: Int = 0

    var categoryName: String = ""

    var categoryId: Int? = 0

    var order: Int = 0

    constructor(category: Category) : this() {
        isProduct = false

        categoryId = category.categoryId
        categoryName = category.categoryName
        order = category.order
    }

    constructor(product: Product) : this() {
        productId = product.productId
        productName = product.productName
        measureId = product.measureId
        categoryId = product.categoryId
        order = product.order
    }

    override fun toString(): String {
        return "CategoryProduct(isProduct=$isProduct, productId=$productId, productName='$productName', measureId=$measureId, categoryName='$categoryName', categoryId=$categoryId, order=$order)"
    }


}
