package com.example.make_eat_easy.firebase

import androidx.lifecycle.MutableLiveData
import com.example.make_eat_easy.models.Category
import com.example.make_eat_easy.models.Dish
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class DishesRepository {

    var dishes: MutableLiveData<MutableList<Dish>> = MutableLiveData(mutableListOf())
    var categories: MutableLiveData<MutableList<Category>> = MutableLiveData(mutableListOf())

    private val db = FirebaseFirestore.getInstance()

    private val dishCollection = db
        .collection("userData")
        .document(Authenticator().getEmail())
        .collection("dish")

    private val categoryCollection = db
        .collection("userData")
        .document(Authenticator().getEmail())
        .collection("dishCategory")

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

        dishCollection.addSnapshotListener { snapshot, _ ->

            dishes.value = snapshot!!.documents.map {  document ->

                val dishId = (document.get("dishId") as Long).toInt()
                val dishName = document.get("dishName") as String
                val categoryId = (document.get("categoryId") as Long?)?.toInt()
                val order = (document.get("order") as Long).toInt()
                val cookDuration = (document.get("cookDuration") as Long).toInt()
                val products = (document.get("products") as HashMap<Int, Double>).toMutableMap()

                Dish(dishId, dishName, categoryId, cookDuration, products, order)

            } as MutableList<Dish>

        }

    }

    fun addDish(
        dishName: String,
        categoryId: Int?,
        cookDuration: Int,
        products: MutableMap<Int, Double>,
        order: Int
    ) {

        var maxId = 0
        dishes.value!!.forEach { if (it.dishId >= maxId) maxId = it.dishId + 1 }

        dishCollection.document(maxId.toString())
            .set(Dish(maxId, dishName, categoryId, cookDuration, products, order))

    }

    fun addCategory(categoryName: String): AddedCategory {

        val newId = categories.value!!.map { it.categoryId }.sortedDescending()[0] + 1
        val newOrder = categories.value!!.map { it.order }.sorted()[0] - 100
        categoryCollection.document(newId.toString())
            .set(Category(newId, categoryName, newOrder))

        return AddedCategory(newId, newOrder)
    }

}