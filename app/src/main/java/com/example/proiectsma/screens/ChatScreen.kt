package com.example.proiectsma.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.proiectsma.components.ChatMessages
import com.example.proiectsma.view_models.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, channelId: String) {

    val viewModel = ChatViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.listenForMessages(channelId)
    }

    val messages = viewModel.messages.collectAsState()

    ChatMessages(
        messages = messages.value,
        onSendMessage = { message ->
            viewModel.sendMessage(channelId, message)
        }
    )
}