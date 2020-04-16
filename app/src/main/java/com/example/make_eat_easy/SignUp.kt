package com.example.make_eat_easy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SignUp: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}