package com.example.make_eat_easy.firebase

import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Product
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ProductsRepository {

    var products: MutableLiveData<MutableList<Product>> = MutableLiveData()

    private val db = FirebaseFirestore.getInstance()

    private val productCategory = db
        .collection("userData")
        .document(Authenticator().getEmail())
        .collection("product")



    init {
        products.value = mutableListOf()

        db.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()

            productCategory.addSnapshotListener { snapshot, _ ->

                snapshot!!.documentChanges.forEach { documentChange ->

                    val document = documentChange.document
                    val productId = document.get("productId") as String
                    val productName = document.get("productName") as String
                    val measureId = document.get("measureId") as String
                    val categoryId = document.get("categoryId") as String

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

    fun addProduct(productId: String, productName: String, categoryId: String, measureId: String) {
        productCategory.document(productId).set(Product(productId, productName, measureId, categoryId))
    }


}

