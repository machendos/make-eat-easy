package com.example.make_eat_easy.firebase

import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Measure
import com.example.make_eat_easy.models.Product
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class ProductsRepository {

    var products: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())
    var measures: MutableLiveData<MutableList<Measure>> = MutableLiveData(mutableListOf())

    private val db = FirebaseFirestore.getInstance()

    private val productCategory = db
        .collection("userData")
        .document(Authenticator().getEmail())
        .collection("product")

    private val measureCategory = db
        .collection("userData")
        .document(Authenticator().getEmail())
        .collection("measure")

    init {

        measureCategory.addSnapshotListener { snapshot, _ ->

            snapshot!!.documentChanges.forEach { documentChange ->
                val document = documentChange.document
                val measureId = (document.get("measureId") as Long).toInt()
                val measureName = document.get("measureName") as String
                val parentMeasureId = (document.get("parentMeasureId") as Long?)?.toInt()
                val parentMeasureFactor = document.get("parentMeasureFactor") as Double

                when (documentChange.type) {

                    DocumentChange.Type.ADDED ->
                        measures.value!!.add(Measure(measureId, measureName, parentMeasureId, parentMeasureFactor))

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

        productCategory.addSnapshotListener { snapshot, _ ->

            snapshot!!.documentChanges.forEach { documentChange ->
                val document = documentChange.document
                val productId = (document.get("productId") as Long).toInt()
                val productName = document.get("productName") as String
                val measureId = (document.get("measureId") as Long).toInt()
                val categoryId = (document.get("categoryId") as Long).toInt()

                when (documentChange.type) {

                    DocumentChange.Type.ADDED ->
                        products.value!!.add(Product(productId, productName, measureId, categoryId))

                    DocumentChange.Type.MODIFIED -> {
                        val element = products.value!!.find { it.productId == productId }
                        element?.productName = productName
                        element?.measureId = measureId
                        element?.categoryId = categoryId
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

    fun addProduct(productId: Int, productName: String, categoryId: Int, measureId: Int) {
        productCategory.document(productId.toString())
            .set(Product(productId, productName, measureId, categoryId))
    }

}

