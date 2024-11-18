package com.example.iworkout.viewmodel

import androidx.lifecycle.ViewModel
import com.example.iworkout.data.model.Workout
import com.example.iworkout.data.repository.WorkoutRepository

class WorkoutViewModel : ViewModel() {

    private val workoutRepository = WorkoutRepository()

    fun addWorkout(workout: Workout, onComplete: (Boolean) -> Unit) {
        workoutRepository.addWorkout(workout, onComplete)
    }

    fun getWorkoutsForDay(userId: String, dayId: String, onResult: (List<Workout>) -> Unit) {
        workoutRepository.getWorkoutsForDay(userId, dayId, onResult)
    }
}