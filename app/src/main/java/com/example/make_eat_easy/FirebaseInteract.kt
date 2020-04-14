package com.example.make_eat_easy

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseInteract : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_interact)
        findViewById<Button>(R.id.set_product_button).setOnClickListener { addProduct() }
    }

    fun addProduct() {
        val product = findViewById<EditText>(R.id.product_edit).text.toString()
        val measure = findViewById<EditText>(R.id.measure_edit).text.toString()
//        val ref = Firebase.database.getReference("product")
//        Toast.makeText(this, FirebaseAuth.getInstance().currentUser.toString(), Toast.LENGTH_LONG).show()
//        ref.setValue(object { val product = product; val measure = measure}).addOnSuccessListener {
//            Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener {
//            Toast.makeText(this, "FAIL, ${it.message}", Toast.LENGTH_LONG).show()
//        }

        val db = Firebase.firestore
        db.collection("product").add(hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )).addOnSuccessListener { documentReference ->
            Log.d("ad", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w("ad", "Error adding document", e)
            }


    }
}
