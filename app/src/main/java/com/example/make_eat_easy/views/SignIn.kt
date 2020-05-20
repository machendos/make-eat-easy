package com.example.make_eat_easy.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.make_eat_easy.R
import com.example.make_eat_easy.databinding.SigninActivityBinding
import com.example.make_eat_easy.viewmodels.SignInViewModel
import com.google.android.material.snackbar.Snackbar

class SignIn: AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: SigninActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.signin_activity)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.success.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.error.observe(this) {
            Snackbar
                .make(binding.root, it, Snackbar.LENGTH_LONG)
                .show()
        }

    }
}

