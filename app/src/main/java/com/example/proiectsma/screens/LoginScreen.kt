package com.example.proiectsma.screens

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.biometric.BiometricPromptManager
import com.example.proiectsma.view_models.AuthState
import com.example.proiectsma.view_models.AuthViewModel
import com.example.proiectsma.components.ClickableLoginTextComponent
import com.example.proiectsma.components.DividerTextComponent
import com.example.proiectsma.components.HeadingTextComponent
import com.example.proiectsma.components.NormalTextComponent
import com.example.proiectsma.components.UnderlinedTextComponent


@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel, promptManager: BiometricPromptManager){

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("main_screen")
            is AuthState.Error-> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = "Hey there,")
            HeadingTextComponent(value = "Welcome Back!")

            Spacer(modifier = Modifier.height(20.dp))
            //RegularTextField(labelValue = "Email", icon = Icons.Rounded.Mail)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text="Email") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Rounded.Mail, contentDescription = "")
                }
            )
            //PasswordTextField(labelValue = "Password", icon = Icons.Rounded.Lock)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text="Password") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Rounded.Lock, contentDescription = "")
                },
                trailingIcon = {
                    val iconImage = if(passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    var description = if(passwordVisible) {
                        "Hide password"
                    } else {
                        "Show password"
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            //UnderlinedTextComponent(value = "Forgot your password?")

            //ButtonComponent(value = "Log in", onButtonSelected = { navController.navigate("home_screen") })
            Button(onClick = { authViewModel.login(email,password) },
                enabled = authState.value != AuthState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(Color.Blue, Color.Red)),
                        shape = RoundedCornerShape(50.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Log in",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                navController.navigate("signup_screen")
            })

        }

        val biometricResult by promptManager.promptResults.collectAsState(initial = null)
        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                println("Activity result: $it")
            }
        )
        LaunchedEffect(biometricResult) {
            if(biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
                if(Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                promptManager.showBiometricPrompt(
                    title = "Sample prompt",
                    description = "Sample prompt description"
                )
            }) {
                Text(text = "Authenticate")
            }
            biometricResult?.let { result ->
                Text(
                    text = when(result) {
                        is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                            result.error
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                            "Authentication failed"
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                            "Authentication not set"
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationSuccess-> {
                            "Authentication success"
                        }
                        BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                            "Feature unavailable"
                        }
                        BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                            "Hardware unavailable"
                        }
                    }
                )
            }
        }
    }
}
