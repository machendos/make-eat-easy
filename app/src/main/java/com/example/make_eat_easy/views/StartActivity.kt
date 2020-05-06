package com.example.make_eat_easy.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.StartActivityBinding
import com.example.make_eat_easy.viewmodels.StartViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


private val db = FirebaseFirestore.getInstance()


class StartActivity: AppCompatActivity() {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: StartActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.start_activity
        )
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]

        if (viewModel.alreadyLoggedIn) {

            db.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()

//            TODO: change to the main activity
            startActivity(Intent(this, Products::class.java))
            finish()
        }

        binding.goToSigninButton
            .setOnClickListener { startActivity(Intent(this, SignIn::class.java)) }
        binding.goToSignupButton
            .setOnClickListener { startActivity(Intent(this, SignUp::class.java)) }

    }
}