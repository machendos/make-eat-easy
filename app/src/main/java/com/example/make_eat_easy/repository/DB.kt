package com.example.make_eat_easy.repository

import com.example.make_eat_easy.models.Category
import com.example.make_eat_easy.models.Measure
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch

object DB {

    private val email = Authenticator.getEmail()

    private val db = FirebaseFirestore.getInstance()

    private val userDocument = db
        .collection("userData")
        .document(email)

    val productCollection = userDocument.collection("product")
    val measureCollection = userDocument.collection("measure")
    val productCategoryCollection = userDocument.collection("category")
    val dishCollection = userDocument.collection("dish")
    val dishCategoryCollection = userDocument.collection("dishCategory")
    val actionCollection = userDocument.collection("action")

    fun create(email: String) {
        val measureCategory = FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(Authenticator.getEmail())
            .collection("measure")

        measureCategory.add(Measure(1, "kilograms"))
        measureCategory.add(Measure(2, "grams", 1, 1000.0))
        measureCategory.add(Measure(3, "pieces"))

        productCategoryCollection.document("100000")
            .set(Category(100000, "Without category", 100000))

        dishCategoryCollection.document("100000")
            .set(Category(100000, "Without category", 100000))

        this.init()
    }

    fun init() {
        DishesRepository
        ProductsRepository
    }

    fun runBatch(callback: (batch: WriteBatch) -> Unit) = FirebaseFirestore
        .getInstance().runBatch(callback)

}