package com.example.make_eat_easy.repository

import com.google.firebase.auth.FirebaseAuth

interface AuthentificatorInterface {

//    fun signIn(email: String, password: String) =
//        auth.signInWithEmailAndPassword(email, password)
//
//    fun signUp(email: String, password: String) =
//        auth.createUserWithEmailAndPassword(email, password)

//    fun resetPassword(email: String) { }

    fun alreadyLoggedIn(): Boolean

//    fun getEmail() = FirebaseAuth.getInstance().currentUser?.email!!
}

object Authenticator: AuthentificatorInterface {

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun signUp(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun resetPassword(email: String) { }

    override fun alreadyLoggedIn() = user != null

    fun getEmail() = FirebaseAuth.getInstance().currentUser?.email!!
}