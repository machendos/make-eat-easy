package com.example.make_eat_easy.repository

import com.example.make_eat_easy.models.Measure
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch

object DB {

    private val email = Authenticator().getEmail()

    private val db = FirebaseFirestore.getInstance()

    private val userDocument = db
        .collection("userData")
        .document(email)

    val productCollection = db.collection("product")
    val measureCollection = db.collection("measure")
    val productCategoryCollection = db.collection("category")
    val dishCollection = db.collection("dish")
    val dishCategoryCollection = db.collection("dishCategory")

    fun create(email: String) {
        val measureCategory = FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(Authenticator().getEmail())
            .collection("measure")

        measureCategory.add(Measure(1, "kilograms"))
        measureCategory.add(Measure(2, "grams", 1, 1000.0))
        measureCategory.add(Measure(3, "pieces"))
    }

    fun runBatch(callback: (batch: WriteBatch) -> Unit) = FirebaseFirestore
        .getInstance().runBatch(callback)

}