package com.example.make_eat_easy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StartActivity: AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        if (FirebaseAuth.getInstance().currentUser == null) {

            Toast.makeText(this, "Please authorise", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "${FirebaseAuth.getInstance().uid}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FirebaseInteract::class.java))
        }

        Toast.makeText(this, auth.languageCode, Toast.LENGTH_LONG).show()

        findViewById<Button>(R.id.register_button).setOnClickListener { signup(
            findViewById<EditText>(R.id.email_register).text.toString(),
            findViewById<EditText>(R.id.pass_register).text.toString()
        ) }

        findViewById<Button>(R.id.login_button).setOnClickListener { signin(
            findViewById<EditText>(R.id.email_login).text.toString(),
            findViewById<EditText>(R.id.pass_login).text.toString()
        ) }

        findViewById<Button>(R.id.pass_reset_button).setOnClickListener { resetPass(
            findViewById<EditText>(R.id.email_pass_reset).text.toString()
        ) }


    }

    fun signup(email: String, pass: String) {
        Log.d("REGISTER", "$email $pass")
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Create new user", Toast.LENGTH_LONG).show()
                signin(email, pass)
                Firebase.firestore.collection("userData").document(
                    FirebaseAuth.getInstance().currentUser?.email!!
                ).collection("category").add(mapOf<String, String>())
            } else {
                Toast.makeText(this, "error: Create new user:: ${it.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "FAIL: Create new user:: ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun signin(email: String, pass: String) {
        Log.d("LOGIN", "$email $pass")
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) Toast.makeText(this, "Authorised", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "error: Authorised:: ${it.exception?.message}", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "FAIL: Authorised ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun resetPass(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("asd", "Email sent.")
                } else Toast.makeText(this, "FAIL: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
    }

}