package com.example.proiectsma.view_models


import androidx.lifecycle.ViewModel
import com.example.proiectsma.model.Message
import com.example.proiectsma.model.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class PostViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance("https://proiectsma-firebase-default-rtdb.europe-west1.firebasedatabase.app")

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    val currentUserId = Firebase.auth.currentUser?.uid ?: ""

    fun createPost(
        authorName : String,
        imgUri : String?,
        needHelp : Boolean,
        title : String,
        description : String,
        country : String,
        city : String) {
        val post =  Post(
            firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            currentUserId,
            authorName,
            imgUri,
            needHelp,
            title,
            description,
            country,
            city,
            System.currentTimeMillis()
        )
        firebaseDatabase.getReference("posts").push().setValue(post)
    }

    fun getPosts() {
        firebaseDatabase.reference.child("posts").orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list = mutableListOf<Post>()
                    snapshot.children.forEach { data ->
                        val post = data.getValue(Post::class.java)
                        post?.let {
                            list.add(it)
                        }
                    }
                    _posts.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error: ${error.message}")
                }
            })
    }
}