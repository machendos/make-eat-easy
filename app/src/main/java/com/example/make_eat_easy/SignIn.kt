package com.example.make_eat_easy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.make_eat_easy.databinding.SigninActivityBinding
import com.example.make_eat_easy.viewmodels.SignInViewModel
import com.google.android.material.snackbar.Snackbar

class SignIn: AppCompatActivity() {

//    lateinit var binding: SigninBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//
//
//        val vm = ViewModelProvider(this)[SignInViewModel::class.java]
//        binding = DataBindingUtil.setContentView(this, R.layout.signin_activity)
//        binding.signInViewModel = SignInViewModel()
//
//        vm.genericEventSample.observe(this) {
//            Log.d("asdasd", "ну тут и так понятно бляaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            Snackbar
//                .make(binding.root, "Generic One Time Event", Snackbar.LENGTH_LONG)
//                .show()
//        }
//    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: SigninActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.signin_activity)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding.viewModel = this.viewModel



        viewModel.genericEventSample.observe(this) {
            Snackbar
                .make(binding.root, "Generic One Time Event", Snackbar.LENGTH_LONG)
                .show()
        }

        viewModel.error.observe(this) {
            Snackbar
                .make(binding.root, it, Snackbar.LENGTH_LONG)
                .show()
        }


    }
}

