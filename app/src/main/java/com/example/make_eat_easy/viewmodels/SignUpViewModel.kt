package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.Authenticator
import com.example.make_eat_easy.util.SingleLiveEvent

class SignUpViewModel : ViewModel() {

    val success = SingleLiveEvent<Void>()
    val error = SingleLiveEvent<String>()

        fun signUp() {
            Authenticator().signUp(email, password)
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