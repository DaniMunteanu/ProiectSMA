package com.example.proiectsma.model

data class Post(
    val id : String,
    val authorId : String,
    val authorName : String,
    val imgUri : String?,
    val title : String,
    val description : String,
    val country : String,
    val city : String,
    val createdAt: Long){
    constructor() : this("","", "",null, "", "", "", "", System.currentTimeMillis())
}
