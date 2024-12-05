package com.example.proiectsma.model

data class Profile(
    val id : String,
    val userName : String,
    val imgUri : String?,
    val description : String,
    val createdAt: Long){
    constructor() : this("","", null, "",  System.currentTimeMillis())
}
