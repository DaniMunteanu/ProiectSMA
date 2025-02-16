package com.example.proiectsma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.proiectsma.biometric.BiometricPromptManager
import com.example.proiectsma.screens.MainScreen
import com.example.proiectsma.view_models.AuthViewModel
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        FirebaseApp.initializeApp(this)

        setContent{
            //MainScreen()

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

            AppNavigation(authViewModel = authViewModel, promptManager)

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