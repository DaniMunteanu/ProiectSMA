package com.example.proiectsma

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proiectsma.screens.ChatScreen
import com.example.proiectsma.screens.HomeScreen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.SignUpScreen
import com.example.proiectsma.view_models.AuthViewModel

@Composable
fun AppNavigation( authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen", builder = {
        composable("signup_screen") {
            SignUpScreen( navController, authViewModel)
        }
        composable("login_screen") {
            LoginScreen( navController, authViewModel)
        }
        composable("home_screen") {
            HomeScreen( navController, authViewModel)
        }
        composable("chat/{channelId}", arguments = listOf(
            navArgument("channelId") {
                type = NavType.StringType
            }
        )) {
            val channelId = it.arguments?.getString("channelId") ?: ""
            ChatScreen(navController,channelId)
        }
    })
}