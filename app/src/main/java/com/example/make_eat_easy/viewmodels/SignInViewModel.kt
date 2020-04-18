package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.Authenticator
import com.example.make_eat_easy.util.SingleLiveEvent

class SignInViewModel : ViewModel() {

    val genericEventSample = SingleLiveEvent<Void>()
    val error = SingleLiveEvent<String>()

    fun fireGenericEvent() = genericEventSample.call()

        fun signIn() {
            Authenticator().signIn(email, password)
            .addOnSuccessListener {
                genericEventSample.call()
            }
            .addOnFailureListener {
                error.value = it.message.toString()
            }
    }

    var email = ""
    var password = ""

}