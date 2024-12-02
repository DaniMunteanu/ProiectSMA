package com.example.proiectsma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.navigation.Screen
import com.example.proiectsma.screens.HomeScreen
import com.example.proiectsma.screens.LoginScreen
import com.example.proiectsma.screens.SignUpScreen
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        FirebaseApp.initializeApp(this)

        setContent{

            /*
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login_screen", builder = {
                composable("sign_up_screen") {
                    SignUpScreen( navController, authViewModel)
                }
                composable("login_screen") {
                    LoginScreen( navController, authViewModel)
                }
                composable("home_screen") {
                    HomeScreen( navController, authViewModel)
                }
            })
             */

            AppNavigation(authViewModel = authViewModel)

            /*
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding), authViewModel = authViewModel)

                }

             */
        }

        /*
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         */

    }



}

/*
@Preview
@Composable
fun DefaultPreview(){
    PCRepairApp()
}

 */