package com.example.proiectsma.view_models

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proiectsma.model.Channel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")
    private val databaseReference = firebaseDatabase.getReference("channels")

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    fun getChannels() {

        /*
        val connectedRef = Firebase.database.getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    Log.d(TAG, "connected")
                } else {
                    Log.d(TAG, "not connected")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Listener was cancelled")
            }
        })
         */

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Channel>()
                if (snapshot.exists()) {
                    for (channelSnapshot in snapshot.children) {
                        val channel = channelSnapshot.getValue(Channel::class.java)
                        //channel?.let { list.add(it) }
                        list.add(channel!!)
                        Log.d(TAG, channel.name)
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