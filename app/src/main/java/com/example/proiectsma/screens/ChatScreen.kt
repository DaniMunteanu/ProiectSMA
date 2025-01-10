package com.example.proiectsma.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            IconButton(onClick = { navController.navigate("main_screen") },
                modifier = Modifier
                    .heightIn(45.dp),
            ) { Icon(imageVector = Icons.Filled.Close, contentDescription = "") }
            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessage(channelId, message)
                }
            )
        }
    }
}