package com.example.proiectsma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.app.PCRepairApp
import com.example.proiectsma.navigation.Screen
import com.example.proiectsma.screens.HomeScreen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.SignUpScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            //PCRepairApp()
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login_screen", builder = {
                composable("sign_up_screen") {
                    SignUpScreen(navController)
                }
                composable("login_screen") {
                    LoginScreen(navController)
                }
                composable("home_screen") {
                    HomeScreen(navController)
                }
            })
        }
    }
        /*
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         */
}

/*
@Preview
@Composable
fun DefaultPreview(){
    PCRepairApp()
}

 */