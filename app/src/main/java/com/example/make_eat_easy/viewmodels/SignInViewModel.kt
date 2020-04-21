package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.Authenticator
import com.example.make_eat_easy.util.SingleLiveEvent

class SignInViewModel : ViewModel() {

    val authenticator = Authenticator()
    val success = SingleLiveEvent<Void>()
    val error = SingleLiveEvent<String>()

    fun fireGenericEvent() = success.call()

        fun signIn() {
            authenticator.signIn(email, password)
            .addOnSuccessListener {
                success.call()
            }
            .addOnFailureListener {
                error.value = it.message.toString()
            }
    }

    var email = ""
    var password = ""

}