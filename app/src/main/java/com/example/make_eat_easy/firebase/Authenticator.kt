package com.example.make_eat_easy.firebase

import com.google.firebase.auth.FirebaseAuth

class Authenticator {

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)


    fun signUp(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun resetPassword(email: String) { }

    fun alreadyLoggedIn() = user != null

    fun getEmail() = FirebaseAuth.getInstance().currentUser?.email!!
}