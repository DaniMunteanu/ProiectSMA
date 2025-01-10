package com.example.proiectsma.view_models

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.proiectsma.model.Channel
import com.example.proiectsma.model.Profile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.internal.util.HalfSerializer.onComplete
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ChannelViewModel() : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.getReference("channels")

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    fun createChannel(user1Id : String, user2Id : String, onComplete: (String) -> Unit) {
        val channel =  Channel(
            firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            user1Id,
            user2Id,
            System.currentTimeMillis()
        )
        firebaseDatabase.getReference("channels").child(user1Id).setValue(channel)
        onComplete(channel.channelId)
    }

    fun findChannel(currentUserId : String?, targetUserId : String?, onComplete: (Channel?) -> Unit) {
        if (currentUserId != null) {
            firebaseDatabase.reference.child("channels").child(currentUserId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val fetchedChannel = snapshot.getValue(Channel::class.java)
                        if (fetchedChannel != null && fetchedChannel.user2Id == targetUserId) {
                            onComplete(fetchedChannel)
                        }
                    }
                }override fun onCancelled(error: DatabaseError) {
                    println("Error: ${error.message}")
                }
            })
        }
    }

    fun getChannels() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Channel>()
                if (snapshot.exists()) {
                    for (channelSnapshot in snapshot.children) {
                        val channel = channelSnapshot.getValue(Channel::class.java)
                        //channel?.let { list.add(it) }
                        list.add(channel!!)
                    }
                    _channels.value = list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error: ${error.message}")
            }
        })
    }
}