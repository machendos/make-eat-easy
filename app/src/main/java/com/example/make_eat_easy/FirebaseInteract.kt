package com.example.make_eat_easy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.make_eat_easy.models.Category
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class CategoryAdapter(options: FirestoreRecyclerOptions<Category>):
    FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CategoryHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int, model: Category) {
        holder.categoryId.text = model.categoryId
        holder.categoryName.text = model.categoryName
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryId: TextView = itemView.findViewById(R.id.id_text)
        val categoryName: TextView = itemView.findViewById(R.id.category_text)
    }

}

class FirebaseInteract : AppCompatActivity() {

    lateinit var adapter: CategoryAdapter

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_interact)

        findViewById<Button>(R.id.set_category_button).setOnClickListener { addCategory() }

        showCategories()

    }

    private fun addCategory() {

        val categoryName = findViewById<EditText>(R.id.category_name_edit).text.toString()

        val db = Firebase.firestore

        val categoryDocument = db
            .collection("userData")
            .document(FirebaseAuth.getInstance().currentUser?.email!!)
            .collection("category")

        val index = Random.nextInt(0, 1000)

        categoryDocument.add(Category(index.toString(), categoryName))
        .addOnSuccessListener {
            Toast.makeText(this, "ADD!", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "FAIL to add: ${it.message}", Toast.LENGTH_LONG).show()
        }

    }

    fun showCategories() {

        val query = FirebaseFirestore
            .getInstance()
            .collection("userData")
            .document(FirebaseAuth.getInstance().currentUser?.email!!)
            .collection("category")


        val options = FirestoreRecyclerOptions.Builder<Category>().setQuery(query, Category::class.java).build()

        adapter = CategoryAdapter(options)

        val rc = findViewById<RecyclerView>(R.id.recycler_v)
        rc.setHasFixedSize(true)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
    }
}
