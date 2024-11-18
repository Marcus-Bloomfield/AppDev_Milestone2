package com.example.iworkout.data.repository

import com.example.iworkout.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addWorkout(workout: Workout, onComplete: (Boolean) -> Unit) {
        db.collection("workouts")
            .add(workout)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getWorkoutsForDay(userId: String, dayId: String, onResult: (List<Workout>) -> Unit) {
        db.collection("workouts")
            .whereEqualTo("userId", userId)
            .whereEqualTo("dayId", dayId)
            .get()
            .addOnSuccessListener { documents ->
                val workouts = documents.map { it.toObject(Workout::class.java) }
                onResult(workouts)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }
}