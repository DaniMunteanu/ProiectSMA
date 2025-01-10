package com.example.proiectsma.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proiectsma.view_models.AuthState
import com.example.proiectsma.view_models.AuthViewModel
import com.example.proiectsma.view_models.ChannelViewModel
import com.example.proiectsma.view_models.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChannelsScreen(navController: NavController, authViewModel: AuthViewModel){

    val authState = authViewModel.authState.observeAsState()
    val homeViewModel = ChannelViewModel()
    val profileViewModel = ProfileViewModel()
    val channels = homeViewModel.channels.collectAsState()


    //val channels by homeViewModel.channels.collectAsState()

    LaunchedEffect(authState.value) {
        homeViewModel.getChannels()
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login_screen")
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.padding(top = 28.dp),

        /*
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Green)
                    .clickable {
                        // TO DO
                    }
            ) {
                Text(
                    text = "Add Channel",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        },

         */
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                item{
                    Text(text = "Messages",
                        color = Color.Blue,
                        style = TextStyle(fontSize = 40.sp,
                            fontWeight = FontWeight.Black),
                        modifier = Modifier.padding(16.dp))
                }
                items(channels.value) { channel ->
                    val targetUserId = if (channel.user1Id == Firebase.auth.currentUser!!.uid) {
                        channel.user2Id
                    } else {
                        channel.user1Id
                    }
                    var name  by remember {
                        mutableStateOf("")
                    }

                    profileViewModel.getProfileById(targetUserId) { currentProfile ->
                        if (currentProfile != null) {
                            Log.d(TAG, "A gasit profilul")
                            Log.d(TAG, name)
                            name = currentProfile.userName
                        } else {
                            println("Profile not found or error occurred")
                            Log.d(TAG, "NU a gasit profilul")
                        }
                    }
                    Column {
                        Text(text = name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Cyan)
                                .clickable {
                                    navController.navigate("chat/${channel.channelId}")
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }

        //AnnouncementList(descriptionList = DataSource().loadDescriptions())





}