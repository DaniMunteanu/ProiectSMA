package com.example.proiectsma.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.proiectsma.navigation.PCRepairAppRouter
import com.example.proiectsma.navigation.Screen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.SignUpScreen

@Composable
fun PCRepairApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = PCRepairAppRouter.currentScreen) { currentState->
            when(currentState.value){
                is Screen.SignUpScreen -> {
                    //SignUpScreen()
                }
                is Screen.LoginScreen -> {
                    //LoginScreen()
                }
            }

        }

    }
}