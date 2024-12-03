package com.example.proiectsma.model

data class Channel(
    val id : String,
    val name : String,
    val createdAt : Long) {

    constructor() : this("","",System.currentTimeMillis())
}