package com.example.make_eat_easy.firebase

import com.example.make_eat_easy.models.Measure
import com.google.firebase.firestore.FirebaseFirestore

class DB {

    private val db = FirebaseFirestore.getInstance()

    fun create(email: String) {

        val measureCategory = db
            .collection("userData")
            .document(Authenticator().getEmail())
            .collection("measure")

        measureCategory.add(Measure(1, "kilograms"))
        measureCategory.add(Measure(2, "grams", 1, 1000.0))
        measureCategory.add(Measure(3, "pieces"))
    }
}