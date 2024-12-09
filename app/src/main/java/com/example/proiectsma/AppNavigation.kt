package com.example.proiectsma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proiectsma.screens.ChannelsScreen
import com.example.proiectsma.screens.ChatScreen
import com.example.proiectsma.screens.CreatePostScreen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.MainScreen
import com.example.proiectsma.screens.ProfileScreen
import com.example.proiectsma.screens.SignUpScreen
import com.example.proiectsma.view_models.AuthViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen", builder = {
        composable("signup_screen") {
            SignUpScreen( navController, authViewModel)
        }
        composable("login_screen") {
            LoginScreen( navController, authViewModel)
        }
        composable("main_screen") {
            MainScreen( navController, authViewModel)
        }
        composable("create_post") {
            CreatePostScreen(navController)
        }
        composable("profile/{profileId}", arguments = listOf(
            navArgument("profileId") {
                type = NavType.StringType
            }
        )) {
            val profileId = it.arguments?.getString("profileId") ?: ""
            ProfileScreen(navController,authViewModel,profileId)
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