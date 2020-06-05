package com.example.make_eat_easy.viewmodels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.models.Category
import com.example.make_eat_easy.models.CategoryProduct
import com.example.make_eat_easy.models.Product
import com.example.make_eat_easy.repository.ProductRepositoryInterface
import com.example.make_eat_easy.repository.ProductsRepository

class ProductViewModel(productRepositoryEntered: ProductRepositoryInterface) : ViewModel() {

    constructor(): this(ProductsRepository) {
        Log.d("asdasd", "EMPTY CONSTRUCTOR")
    }

    var productRepository = productRepositoryEntered

    val productsCategoryList = MediatorLiveData<MutableList<CategoryProduct>>()

    lateinit var cashedProduct: Product
    lateinit var cashedcategories: Category
    lateinit var cashedProductsList: List<Product>

    fun onChangeProductCategory() {

        productRepository = ProductsRepository

        productsCategoryList.value =
            productRepository
                .categories
                .value!!
                .map { category ->
                    CategoryProduct(category)
                } as MutableList

        val convertedProduct = productRepository
            .products
            .value!!
            .map { product -> CategoryProduct(product) } as MutableList

        productsCategoryList.value!!.addAll(convertedProduct)

        productsCategoryList.value!!.sortBy { it.order }
        productsCategoryList.value = productsCategoryList.value

    }

    init {

        productsCategoryList.value = mutableListOf()

        productsCategoryList.addSource(productRepository.categories) {
            onChangeProductCategory()
        }

        productsCategoryList.addSource(productRepository.products) {
            onChangeProductCategory()
        }
    }

    fun deleteProductLocal(productId: Int) {
        val productIndex = productsCategoryList.value!!.indexOfFirst { it.productId == productId }
        if (productIndex > -1) productsCategoryList.value!!.removeAt(productIndex)
    }

    fun deleteCategoryLocal(categoryId: Int) {
        val categoryIndex = productsCategoryList.value!!
            .indexOfFirst { it.categoryId == categoryId && !it.isProduct }
        if (categoryIndex > -1) {
            productsCategoryList.value!!
                .forEach { if (it.categoryId == categoryId) it.categoryId = null }
            productsCategoryList.value!!.removeAt(categoryIndex)
        }
    }

    fun addProduct(productName: String, measureName: String, categoryName: String) {

        val measure =
            productRepository.measures.value!!.find { it.measureName == measureName }
        val measureId = measure?.measureId ?: productRepository.addMeasure(measureName)

        if (categoryName == "") {
            val order =
                productsCategoryList.value!![productsCategoryList.value!!.lastIndex].order + 1
            productRepository.addProduct(productName, null, measureId, order)
            return
        }

        val category =
            productRepository.categories.value!!.find { it.categoryName == categoryName }

        if (category == null) {
            val (categoryId, categoryOrder) = productRepository.addCategory(categoryName)
            productRepository.addProduct(productName, categoryId, measureId, categoryOrder + 1)

        } else {
            val elementsForIncrement = mutableListOf<CategoryProduct>()
            var currOrder = category.order + 1
            productsCategoryList.value!!.forEach {
                if (it.order == currOrder) {
                    currOrder = it.order + 1
                    elementsForIncrement.add(it)
                }
            }
            productRepository.incrementOrder(elementsForIncrement)
            productRepository.addProduct(
                productName,
                category.categoryId,
                measureId,
                category.order + 1
            )
        }
    }

    fun deleteProduct(productId: Int) {
        cashedProduct = productRepository.products.value!!.find { it.productId == productId }!!
        productRepository.deleteProduct(productId)
    }

    fun unremoveProduct() = productRepository.unremoveProduct(cashedProduct)

    fun updateProduct(productId: Int, newProductName: String) {

        val oldProduct = productRepository.products.value!!.find { it.productId == productId }

        if (oldProduct!!.productName != newProductName) {
            cashedProduct = oldProduct
            productRepository.updateProduct(productId, newProductName)

        }

    }

    fun addProductLocal(id: Int = 0, name: String = "product", categoryId: Int? = null) {
        val product = Product(id, name, 0, categoryId)
        productsCategoryList.value!!.add(CategoryProduct(product))
    }

    fun addCategoryLocal(id: Int = 0, name: String = "category") {
        val product = Category(id, name)
        productsCategoryList.value!!.add(CategoryProduct(product))
    }

    fun editProductLocal(id: Int, newName: String = "product") {
        val product = productsCategoryList.value!!.find{ it.productId == id }
        if (product !== null) product.productName = newName

    }

    fun addCategory(categoryName: String) = productRepository.addCategory((categoryName))

}