package com.example.iworkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.iworkout.data.model.User
import com.example.iworkout.data.repository.UserRepository

class AuthViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun signUp(user: User, onComplete: (Boolean) -> Unit) {
        try {
            userRepository.addUser(user) { success ->
                onComplete(success)
            }
        } catch (e: Exception) {
            Log.e("SignUp", "Error signing up user", e)
            onComplete(false)
        }
    }

    fun login(username: String, password: String, onResult: (User?) -> Unit) {
        userRepository.getUser(username, password, onResult)
    }
}