package com.example.proiectsma.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proiectsma.components.ButtonComponent
import com.example.proiectsma.components.HeadingTextComponent
import com.example.proiectsma.view_models.AuthState
import com.example.proiectsma.view_models.AuthViewModel
import com.example.proiectsma.view_models.ChannelViewModel
import com.example.proiectsma.view_models.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(navController: NavController, authViewModel : AuthViewModel, profileId : String) {
    val isCurrentUser = (profileId == Firebase.auth.currentUser?.uid)
    val currentUser = Firebase.auth.currentUser!!.uid
    val authState = authViewModel.authState.observeAsState()

    val profileViewModel = ProfileViewModel()
    val channelViewModel = ChannelViewModel()
    //var currentUserName = mutableStateOf("")

    var currentUserName by remember {
        mutableStateOf("")
    }

    var currentDescription by remember {
        mutableStateOf("")
    }
    //var currentDescription = ""

    LaunchedEffect(authState.value) {
        if (profileId != null) {
            profileViewModel.getProfileById(profileId) { currentProfile ->
                if (currentProfile != null) {
                    Log.d(TAG, "A gasit profilul")
                    currentUserName = currentProfile.userName
                    currentDescription = currentProfile.description
                } else {
                    println("Profile not found or error occurred")
                    Log.d(TAG, "NU a gasit profilul")
                }
            }
        }
        when(authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login_screen")
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 28.dp)
            .fillMaxWidth(),
    ) {
        IconButton(onClick = { navController.navigate("main_screen") },
            modifier = Modifier
                .heightIn(45.dp),
        ) { Icon(imageVector = Icons.Filled.Close, contentDescription = "") }

        Image(
            painter = ColorPainter(Color.Gray),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        HeadingTextComponent(currentUserName)

        Text(
            text = currentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .heightIn(max = 85.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ),
            color = Color.Black
        )

        var found by remember {
            mutableStateOf(false)
        }



        fun findingChatRoute() {
            var chatRoute = ""
            channelViewModel.findChannel(Firebase.auth.currentUser?.uid,profileId) { currentChannel ->
                if (currentChannel != null) {
                    Log.d(TAG, "A gasit canalul")
                    //navController.navigate("chat/${currentChannel.channelId}")
                    found = found.not()
                    chatRoute = "chat/${currentChannel.channelId}"
                    Log.d(TAG, found.toString())
                    Log.d(TAG, chatRoute)
                    return@findChannel
                }
            }
            Log.d(TAG, "Asta este ${chatRoute}")
            if(found == false) {
                channelViewModel.findChannel(profileId,Firebase.auth.currentUser?.uid) { revCurrentChannel ->
                    if (revCurrentChannel != null) {
                        Log.d(TAG, "A gasit canalul alta ramura")
                        //navController.navigate("chat/${revCurrentChannel.channelId}")
                        found = true
                        chatRoute = "chat/${revCurrentChannel.channelId}"
                    }
                }
            }
            if(found == false) {
                channelViewModel.createChannel(currentUser, profileId) { createdChannelId ->
                    //navController.navigate("chat/${createdChannelId}")
                    chatRoute = "chat/${createdChannelId}"
                }
            }
            Log.d(TAG, chatRoute)
            navController.navigate(chatRoute)
        }

        if(isCurrentUser)
            ButtonComponent(value = "Sign out", onButtonSelected = { authViewModel.signout() } )
        else
            ButtonComponent(value = "Send Message", onButtonSelected = {
                channelViewModel.findChannel(Firebase.auth.currentUser?.uid,profileId) { currentChannel ->
                    if (currentChannel != null) {
                        Log.d(TAG, "A gasit canalul")
                        navController.navigate("chat/${currentChannel.channelId}")
                    }
                }
            }
            )
    }
}