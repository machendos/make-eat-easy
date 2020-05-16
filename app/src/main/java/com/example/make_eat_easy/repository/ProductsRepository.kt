package com.example.make_eat_easy.repository

import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Category
import com.example.make_eat_easy.models.CategoryProduct
import com.example.make_eat_easy.models.Measure
import com.example.make_eat_easy.models.Product
import com.google.firebase.firestore.DocumentChange

data class AddedCategory(val categoryId: Int, val categoryOrder: Int)

class ProductsRepository {

    var products: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())
    var measures: MutableLiveData<MutableList<Measure>> = MutableLiveData(mutableListOf())
    var categories: MutableLiveData<MutableList<Category>> = MutableLiveData(mutableListOf())

    private val productCollection = DB.productCollection
    private val measureCollection = DB.measureCollection
    private val categoryCollection = DB.productCategoryCollection

    init {

        categoryCollection.addSnapshotListener { snapshot, _ ->

            snapshot!!.documentChanges.forEach { documentChange ->
                val document = documentChange.document
                val categoryId = (document.get("categoryId") as Long).toInt()
                val categoryName = document.get("categoryName") as String
                val order = (document.get("order") as Long).toInt()

                when (documentChange.type) {

                    DocumentChange.Type.ADDED -> {
                        var targetIndex = categories.value!!.indexOfFirst { it.order >= order }
                        targetIndex =
                            if (targetIndex == -1) categories.value!!.size else targetIndex
                        categories.value!!.add(
                            targetIndex,
                            Category(categoryId, categoryName, order)
                        )
                    }

                    DocumentChange.Type.MODIFIED -> {
                        val element = categories.value!!.find { it.categoryId == categoryId }
                        element?.categoryId = categoryId
                        element?.categoryName = categoryName
                        element?.order = order

//                        TODO:!!!!!!
                        categories.value!!.sortBy { it.order }
                    }

                    DocumentChange.Type.REMOVED -> {
                        val index = categories.value!!.indexOfFirst { it.categoryId == categoryId }
                        categories.value!!.removeAt(index)
                    }


                }
                categories.postValue(categories.value)
            }

        }

        measureCollection.addSnapshotListener { snapshot, _ ->

            snapshot!!.documentChanges.forEach { documentChange ->
                val document = documentChange.document
                val measureId = (document.get("measureId") as Long).toInt()
                val measureName = document.get("measureName") as String
                val parentMeasureId = (document.get("parentMeasureId") as Long?)?.toInt()
                val parentMeasureFactor = document.get("parentMeasureFactor") as Double

                when (documentChange.type) {

                    DocumentChange.Type.ADDED ->
                        measures.value!!.add(
                            Measure(
                                measureId,
                                measureName,
                                parentMeasureId,
                                parentMeasureFactor
                            )
                        )

                    DocumentChange.Type.MODIFIED -> {
                        val element = measures.value!!.find { it.measureId == measureId }
                        element?.measureId = measureId
                        element?.measureName = measureName
                        element?.parentMeasureId = parentMeasureId
                        element?.parentMeasureFactor = parentMeasureFactor
                    }

                    DocumentChange.Type.REMOVED -> {
                        val index = measures.value!!.indexOfFirst { it.measureId == measureId }
                        measures.value!!.removeAt(index)
                    }


                }
                measures.postValue(measures.value)
            }
        }

        productCollection.addSnapshotListener { snapshot, _ ->

            snapshot!!.documentChanges.forEach { documentChange ->
                val document = documentChange.document
                val productId = (document.get("productId") as Long).toInt()
                val productName = document.get("productName") as String
                val measureId = (document.get("measureId") as Long).toInt()
                val categoryId = (document.get("categoryId") as Long?)?.toInt()
                val order = (document.get("order") as Long).toInt()

                when (documentChange.type) {

                    DocumentChange.Type.ADDED ->
                        products.value!!.add(
                            Product(
                                productId,
                                productName,
                                measureId,
                                categoryId,
                                order
                            )
                        )

                    DocumentChange.Type.MODIFIED -> {
                        val element = products.value!!.find { it.productId == productId }
                        element?.productName = productName
                        element?.measureId = measureId
                        element?.categoryId = categoryId
                        element?.order = order
                    }

                    DocumentChange.Type.REMOVED -> {
                        val index = products.value!!.indexOfFirst { it.productId == productId }
                        products.value!!.removeAt(index)
                    }

                }
                products.postValue(products.value)
            }

        }


    }

    fun addProduct(productName: String, categoryId: Int?, measureId: Int, order: Int): Int {

        var maxId = 0
        products.value!!
            .forEach { if (it.productId >= maxId) maxId = it.productId + 1 }
        productCollection.document(maxId.toString())
            .set(Product(maxId, productName, measureId, categoryId, order))

        return maxId
    }

    fun deleteProduct(productId: Int) {
        productCollection
            .document(productId.toString())
            .delete()
    }

    fun updateProductOrders(productsCategories: MutableList<CategoryProduct>) {

        DB.runBatch { batch ->
            productsCategories.forEach {

                if (it.isProduct) {
                    batch.update(
                        productCollection.document(it.productId.toString()),
                        "order",
                        it.order
                    )

                    batch.update(
                        productCollection.document(it.productId.toString()),
                        "categoryId",
                        it.categoryId
                    )
                } else {
                    batch.update(
                        categoryCollection.document(it.categoryId.toString()),
                        "order",
                        it.order
                    )
                }


            }


        }
    }

    fun addMeasure(measureName: String): Int {
        val newId = measures.value!!.map { it.measureId }.sortedDescending()[0] + 1
        measureCollection.document(newId.toString()).set(Measure(newId, measureName))
        return newId
    }


    fun addCategory(categoryName: String): AddedCategory {
        val newId = categories.value!!.map { it.categoryId }.sortedDescending()[0] + 1
        val newOrder = categories.value!!.map { it.order }.sorted()[0] - 100

        categoryCollection.document(newId.toString()).set(Category(newId, categoryName, newOrder))
        return AddedCategory(newId, newOrder)
    }

    fun incrementOrder(elements: MutableList<CategoryProduct>) {
        DB.runBatch { batch ->
            elements.forEach {
                if (it.isProduct) {
                    batch.update(
                        productCollection.document(it.productId.toString()),
                        "order",
                        it.order + 1
                    )
                } else {
                    batch.update(
                        categoryCollection.document(it.categoryId.toString()),
                        "order",
                        it.order + 1
                    )
                }
            }


        }
    }

    fun updateProduct(productId: Int, productName: String) {
        productCollection.document(productId.toString()).update("productName", productName)
    }

    fun unremoveProduct(product: Product) {
        productCollection.document(product.productId.toString()).set(product)
    }

//    fun getOrderForNewProduct(categoryId: Int): Int {
//
//    }

}

