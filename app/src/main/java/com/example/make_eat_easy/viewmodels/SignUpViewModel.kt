package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.repository.Authenticator
import com.example.make_eat_easy.repository.DB
import com.example.make_eat_easy.util.SingleLiveEvent

class SignUpViewModel : ViewModel() {

    private val authenticator =
        Authenticator()
    val success = SingleLiveEvent<Void>()
    val error = SingleLiveEvent<String>()

        fun signUp() {
            authenticator.signUp(email, password)
            .addOnSuccessListener {
                authenticator.signIn(email, password)
                DB.create(email)
                success.call()
            }
            .addOnFailureListener {
                error.value = it.message.toString()
            }
    }

    var email = ""
    var password = ""

}