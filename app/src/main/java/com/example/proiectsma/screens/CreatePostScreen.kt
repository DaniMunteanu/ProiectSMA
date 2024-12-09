package com.example.proiectsma.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proiectsma.view_models.AuthViewModel
import com.example.proiectsma.view_models.PostViewModel
import com.example.proiectsma.view_models.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreatePostScreen(navController: NavController) {
    var needHelp by remember {
        mutableStateOf(true)
    }
    var titleValue by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var country by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var currentUserName by remember {
        mutableStateOf("")
    }

    val profileViewModel = ProfileViewModel()
    val postViewModel = PostViewModel()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    LaunchedEffect(key1 = true) {
        profileViewModel.getProfileById(auth.currentUser!!.uid) { currentProfile ->
            if (currentProfile != null) {
                Log.d(TAG, "A gasit profilul")
                currentUserName = currentProfile.userName
            } else {
                println("Profile not found or error occurred")
                Log.d(TAG, "NU a gasit profilul")
            }
        }
    }

    Scaffold(
        modifier = Modifier.padding(top = 28.dp),
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Blue)
                    .clickable {
                        postViewModel.createPost(currentUserName,"",needHelp,titleValue,description,country,city)
                        navController.navigate("main_screen")
                    }
            ) {
                Text(
                    text = "Post",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            Text(
                text = "Create an Announcement...",
                color = Color.Blue,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = "Select an option:",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            )
            Row {
                RadioButton(
                    selected = needHelp == true,
                    onClick = { needHelp = true }
                )
                Text("I need help", Modifier.clickable { needHelp = true })

                RadioButton(
                    selected = needHelp == false,
                    onClick = { needHelp = false }
                )
                Text("I want to offer help", Modifier.clickable { needHelp = false })
            }

            OutlinedTextField(
                value = titleValue,
                onValueChange = { titleValue = it },
                label = { Text(text="Title") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text(text="Country") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text(text="City") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text="Description") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color.Red,
                    cursorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }


}