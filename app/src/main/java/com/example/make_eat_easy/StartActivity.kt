package com.example.make_eat_easy

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity: AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        if (FirebaseAuth.getInstance().currentUser == null) {
            Toast.makeText(this, "Please authorise", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Already signin", Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.register_button).setOnClickListener { signup(
            findViewById<EditText>(R.id.email_register).text.toString(),
            findViewById<EditText>(R.id.pass_register).text.toString()
        ) }

        findViewById<Button>(R.id.login_button).setOnClickListener { signup(
            findViewById<EditText>(R.id.email_login).text.toString(),
            findViewById<EditText>(R.id.pass_login).text.toString()
        ) }


    }

    fun signup(email: String, pass: String) {
        Log.d("REGISTER", "$email $pass")
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            Toast.makeText(this, "Create new user", Toast.LENGTH_LONG).show()
            signin(email, pass)
        }.addOnFailureListener {
            Toast.makeText(this, "FAIL: Create new user", Toast.LENGTH_LONG).show()
        }
    }

    fun signin(email: String, pass: String) {
        Log.d("LOGIN", "$email $pass")
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            Toast.makeText(this, "Authorised", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "FAIL: Authorised", Toast.LENGTH_LONG).show()
        }
    }


}
