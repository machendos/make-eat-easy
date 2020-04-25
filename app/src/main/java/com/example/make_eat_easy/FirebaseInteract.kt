package com.example.make_eat_easy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.adapters.CategoryAdapter
import com.example.make_eat_easy.firebase.Post
import com.example.make_eat_easy.models.Category
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseInteract : AppCompatActivity() {

    lateinit var adapter: CategoryAdapter

    val categoryCollection = FirebaseFirestore
        .getInstance()
        .collection("userData")
        .document(FirebaseAuth.getInstance().currentUser?.email!!)
        .collection("category")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_interact)

        findViewById<Button>(R.id.set_category_button).setOnClickListener { addCategory() }

        showCategories()

    }

    private fun addCategory() {

        val categoryName = findViewById<EditText>(R.id.category_name_edit).text.toString()

       Post().postCategory(categoryName)
           .addOnSuccessListener { Toast.makeText(this, "ADD!", Toast.LENGTH_LONG).show() }
           .addOnFailureListener { Toast.makeText(this, "FAIL to add: ${it.message}", Toast.LENGTH_LONG).show() }

    }

    fun showCategories() {

        val options = FirestoreRecyclerOptions.Builder<Category>().setQuery(categoryCollection, Category::class.java).build()

        adapter = CategoryAdapter(options)

        val rc = findViewById<RecyclerView>(R.id.recycler_v)
        rc.setHasFixedSize(true)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}
