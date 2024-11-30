package com.example.proiectsma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proiectsma.AuthViewModel
import com.example.proiectsma.components.ButtonComponent
import com.example.proiectsma.components.ClickableLoginTextComponent
import com.example.proiectsma.components.DividerTextComponent
import com.example.proiectsma.components.HeadingTextComponent
import com.example.proiectsma.components.NormalTextComponent
import com.example.proiectsma.components.PasswordTextField
import com.example.proiectsma.components.RegularTextField
import com.example.proiectsma.navigation.PCRepairAppRouter
import com.example.proiectsma.navigation.Screen

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
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
            RegularTextField(labelValue = "First Name", icon = Icons.Rounded.Person)
            RegularTextField(labelValue = "Last Name", icon = Icons.Rounded.Person)
            RegularTextField(labelValue = "Email", icon = Icons.Rounded.Mail)
            PasswordTextField(labelValue = "Password", icon = Icons.Rounded.Lock)


            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = "Register", onButtonSelected = { navController.navigate("home_screen") })
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
    SignUpScreen(mockNavController, mockAuthViewModel)
}