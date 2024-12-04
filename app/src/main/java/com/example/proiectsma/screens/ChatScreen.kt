package com.example.proiectsma.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proiectsma.components.ChatMessages
import com.example.proiectsma.view_models.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, channelId: String) {
    val viewModel = ChatViewModel()
    val messages = viewModel.messages.collectAsState()

    Scaffold{
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }

            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessage(channelId, message)
                }
            )
        }
    }
}