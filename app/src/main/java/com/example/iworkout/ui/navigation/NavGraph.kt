package com.example.iworkout.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iworkout.ui.screens.Login
import com.example.iworkout.ui.screens.SignUp
import com.example.iworkout.ui.screens.Home

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(onSignUpNavigate = { navController.navigate("signup") },
                onLoginSuccess = { navController.navigate("home") })
        }
        composable("signup") {
            SignUp(onLoginNavigate = { navController.navigate("login") })
        }
        composable("home") {
            Home()
        }
    }
}