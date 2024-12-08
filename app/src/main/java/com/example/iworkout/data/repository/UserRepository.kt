package com.example.iworkout.data.repository

import com.example.iworkout.data.model.User
import com.example.iworkout.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addUser(user: User, onComplete: (Boolean) -> Unit) {
        db.collection("users").document(user.userId)
            .set(user)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getUser(username: String, password: String, onResult: (User?) -> Unit) {
        db.collection("users")
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    onResult(documents.documents[0].toObject(User::class.java))
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { onResult(null) }
    }

    fun getCurrentUserId(username: String, password: String, onResult: (String?) -> Unit) {
        getUser(username, password) { user ->
            if (user != null) {
                onResult(user.userId)
            } else {
                onResult(null)
            }
        }
    }
}
