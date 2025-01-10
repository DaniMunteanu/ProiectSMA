package com.example.proiectsma.view_models

import androidx.lifecycle.ViewModel
import com.example.proiectsma.model.Message
import com.example.proiectsma.model.Post
import com.example.proiectsma.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.internal.util.HalfSerializer.onComplete
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ProfileViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles = _profiles.asStateFlow()

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    init {
        getProfiles()
    }

    fun getProfileById(userId : String, onComplete: (Profile?) -> Unit) {
        firebaseDatabase.reference.child("profiles").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedProfile = snapshot.getValue(Profile::class.java)
                onComplete(fetchedProfile)
            }override fun onCancelled(error: DatabaseError) {
                println("Error: ${error.message}")
            }
        })
    }

    fun createProfile(userId : String ,userName : String) {
        val profile =  Profile(
            userId,
            userName,
            null,
            "No description yet",
            System.currentTimeMillis()
        )
        firebaseDatabase.getReference("profiles").child(userId).setValue(profile)
    }

    fun getProfiles() {
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