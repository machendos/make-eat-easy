package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.firebase.Authenticator

class StartViewModel : ViewModel() {

    private val authenticator =
        Authenticator()

    val alreadyLoggedIn = authenticator.alreadyLoggedIn()

}