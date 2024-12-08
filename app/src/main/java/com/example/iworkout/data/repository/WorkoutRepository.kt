package com.example.iworkout.data.repository

import android.util.Log
import com.example.iworkout.data.model.Workout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutRepository {

    private val db = FirebaseFirestore.getInstance()
    fun addWorkout(workout: Workout, onComplete: (Boolean) -> Unit) {
        // Add workout document to Firestore, Firestore will auto-generate a unique ID
        var temp = db.collection("workouts")

        temp.add(workout)
            .addOnSuccessListener {
                Log.d("Firestore", "Workout added successfully")
                onComplete(true)  // Callback indicating success
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error adding workout", exception)
                onComplete(false)  // Callback indicating failure
            }


        onComplete(true)
    }

    // Fetch workouts for a specific user and day
    fun getWorkoutsForDay(userId: Int, dayId: Int, onResult: (List<Workout>) -> Unit) {
        db.collection("workouts")
            .whereEqualTo("userId", userId)  // User ID as Int
            .whereEqualTo("dayId", dayId)
            .get()
            .addOnSuccessListener { documents ->
                val workouts = documents.map { it.toObject(Workout::class.java) }
                onResult(workouts)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }
}
