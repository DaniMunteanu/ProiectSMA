package com.example.proiectsma.view_models

import androidx.lifecycle.ViewModel
import com.example.proiectsma.model.Post
import com.example.proiectsma.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ProfileViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles = _profiles.asStateFlow()

    val currentUserId = Firebase.auth.currentUser?.uid ?: ""

    fun createProfile(userName : String) {
        val profile =  Profile(
            currentUserId,
            userName,
            null,
            "",
            System.currentTimeMillis()
        )
        firebaseDatabase.reference.child("profiles").child(currentUserId).push().setValue(profile)
    }

    fun getPosts() {
        firebaseDatabase.reference.child("profiles").orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = mutableListOf<Profile>()
                    snapshot.children.forEach { data ->
                        val profile = data.getValue(Profile::class.java)
                        profile?.let {
                            list.add(it)
                        }
                    }
                    _profiles.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error: ${error.message}")
                }
            })
    }
}