package com.example.proiectsma.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proiectsma.view_models.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun ContentScreen(modifier : Modifier = Modifier, selectedIndex : Int, navController: NavController, authViewModel: AuthViewModel) {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    when(selectedIndex) {
        0 -> ChannelsScreen(navController, authViewModel)
        1 -> PostsScreen(modifier, navController)
        2 -> ProfileScreen(navController, authViewModel, auth.currentUser!!.uid)
    }
}