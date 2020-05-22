package com.example.make_eat_easy.models

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
