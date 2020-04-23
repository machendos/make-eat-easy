package com.example.make_eat_easy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.make_eat_easy.databinding.StartActivityBinding
import com.example.make_eat_easy.viewmodels.StartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface StartNavigation {
    fun signIn() {}
    fun signUp() {}
}

class StartActivity: AppCompatActivity() {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: StartActivityBinding



    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        object StartNavigation {
            fun signIn() = startActivity(Intent(this, SignIn::class.java))
            fun signUp() = startActivity(Intent(this, SignUp::class.java))
//        }

        binding = DataBindingUtil.setContentView(this, R.layout.start_activity)
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]

        binding.goToSigninButton
            .setOnClickListener { startActivity(Intent(this, SignIn::class.java)) }
        binding.goToSignupButton
            .setOnClickListener { startActivity(Intent(this, SignUp::class.java)) }




//        !!
        if (viewModel.alreadyLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


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

        Authenticator().signIn(email, pass).addOnSuccessListener {  }

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
