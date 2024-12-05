package com.example.proiectsma.screens

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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PostsScreen(modifier : Modifier, navController : NavController) {
    Scaffold(
        modifier = Modifier.padding(top = 28.dp),
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                item{
                    Text(text = "Announcements",
                        color = Color.Blue,
                        style = TextStyle(fontSize = 40.sp,
                            fontWeight = FontWeight.Black),
                        modifier = Modifier.padding(16.dp))
                }
                /*
                items(channels.value) { channel ->
                    Column {
                        Text(text = channel.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Cyan)
                                .clickable {
                                    navController.navigate("chat/${channel.id}")
                                }
                                .padding(16.dp)
                        )
                    }
                }

                 */
            }
        }
    }
}