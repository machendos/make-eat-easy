package com.example.make_eat_easy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SignIn: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        supportActionBar?.setDisplayShowTitleEnabled(false)

    }
}