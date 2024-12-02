package com.example.proiectsma

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.screens.HomeScreen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.SignUpScreen

@Composable
fun AppNavigation( authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen", builder = {
        composable("signup_screen") {
            SignUpScreen( navController, authViewModel)
        }
        composable("login_screen") {
            LoginScreen( navController, authViewModel)
        }
        composable("home_screen") {
            HomeScreen( navController, authViewModel)
        }
    })
}