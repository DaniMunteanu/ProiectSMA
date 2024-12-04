package com.example.proiectsma.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proiectsma.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

class ChatViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(channelId: String, messageText: String) {
        val message =  Message(
            firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            Firebase.auth.currentUser?.uid ?: "",
            messageText,
            Firebase.auth.currentUser?.displayName ?: "",
            System.currentTimeMillis()
        )
        firebaseDatabase.reference.child("messages").child(channelId).push().setValue(message)
    }

    fun listenForMessages(channelId : String) {
        firebaseDatabase.reference.child("messages").child(channelId).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error: ${error.message}")
                }
            })
    }
}