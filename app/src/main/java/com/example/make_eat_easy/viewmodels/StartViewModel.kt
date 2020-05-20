package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.repository.Authenticator
import com.example.make_eat_easy.repository.DB

class StartViewModel : ViewModel() {

    val alreadyLoggedIn = Authenticator.alreadyLoggedIn()

    init { if (alreadyLoggedIn) DB.init() }

}