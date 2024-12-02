package com.example.proiectsma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proiectsma.AuthState
import com.example.proiectsma.AuthViewModel
import com.example.proiectsma.DataSource
import com.example.proiectsma.components.AnnouncementList

@Composable
fun HomeScreen( navController: NavController, authViewModel: AuthViewModel){

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login_screen")
            else -> Unit
        }
    }
    
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Column {
            Text(text = "Home Page")
            TextButton(onClick = {
                authViewModel.signout()
            }) {
                Text(text = "Sign out")
            }
        }
        //AnnouncementList(descriptionList = DataSource().loadDescriptions())
    }


}