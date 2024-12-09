package com.example.proiectsma.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.view_models.AuthState
import com.example.proiectsma.view_models.AuthViewModel
import com.example.proiectsma.components.ClickableLoginTextComponent
import com.example.proiectsma.components.DividerTextComponent
import com.example.proiectsma.components.HeadingTextComponent
import com.example.proiectsma.components.NormalTextComponent
import com.example.proiectsma.components.RegularTextField

@Composable
fun SignUpScreen( navController: NavController, authViewModel: AuthViewModel) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("main_screen")
            is AuthState.Error-> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
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
            HeadingTextComponent(value = "Create an Account")
            Spacer(modifier = Modifier.height(20.dp))
            //RegularTextField(labelValue = "First Name", icon = Icons.Rounded.Person)
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text="First Name") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Rounded.Person, contentDescription = "")
                }
            )
            //RegularTextField(labelValue = "Last Name", icon = Icons.Rounded.Person)
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text="Last Name") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Rounded.Person, contentDescription = "")
                }
            )

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
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            //ButtonComponent(value = "Register", onButtonSelected = { navController.navigate("home_screen") })
            Button(onClick = { authViewModel.signup(email,password, "$firstName $lastName") },
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

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                navController.navigate("login_screen")
            })
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    val mockNavController = rememberNavController()
    val mockAuthViewModel = AuthViewModel()
    SignUpScreen( mockNavController, mockAuthViewModel)
}