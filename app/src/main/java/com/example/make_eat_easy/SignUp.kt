package com.example.make_eat_easy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.make_eat_easy.databinding.SignupActivityBinding
import com.example.make_eat_easy.viewmodels.SignUpViewModel
import com.google.android.material.snackbar.Snackbar

class SignUp: AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: SignupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.signup_activity)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = this.viewModel

        viewModel.success.observe(this) {
//            TODO: automatically signin
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

