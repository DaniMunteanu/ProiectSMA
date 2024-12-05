package com.example.proiectsma.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proiectsma.view_models.AuthViewModel

@Composable
fun ContentScreen(modifier : Modifier = Modifier, selectedIndex : Int, navController: NavController, authViewModel: AuthViewModel) {
    when(selectedIndex) {
        0 -> PostsScreen(modifier)
        1 -> ChannelsScreen(navController,authViewModel)
        2 -> ProfileScreen(modifier)
    }
}