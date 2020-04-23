package com.example.make_eat_easy.firebase

import com.example.make_eat_easy.models.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class Post {

    val authenticator = Authenticator()

    val userDocument = FirebaseFirestore
        .getInstance()
        .collection("userData")
        .document(authenticator.getEmail())

    fun postCategory(categoryName: String): Task<DocumentReference> {
        val categoryCollection = userDocument.collection("category")

        val index = Random.nextInt(0, 1000)

        return categoryCollection.add(Category(index.toString(), categoryName, Random.nextInt(0, 1000)))

    }
}