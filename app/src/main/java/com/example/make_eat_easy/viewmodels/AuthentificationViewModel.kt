package com.example.make_eat_easy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.make_eat_easy.util.SingleLiveEvent


class SignInViewModelka: ViewModel() {


//    val authenticator = Authenticator()


//    private val _navigateToDetails = SingleLiveEvent<Any>()
//
//    val navigateToDetails : LiveData<Any>
//        get() = _navigateToDetails

    val genericEventSample = SingleLiveEvent<Void>()

    fun fireGenericEvent() = genericEventSample.call()

//    fun signIn() {
//        authenticator.signIn(email, password)
//            .addOnSuccessListener {
//                Log.d("asdasd", "ну тут и так понятно")
//                _navigateToDetails.call()
//            }
//            .addOnFailureListener {
//                Log.d("asdasd", "ну тут и так понятно бля")
//                _navigateToDetails.call()
//            }
//    }

    var email = ""
    var password = ""

}