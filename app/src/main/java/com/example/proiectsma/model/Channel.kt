package com.example.proiectsma.model

data class Channel(
    val channelId : String,
    val user1Id: String,
    val user2Id: String,
    val createdAt : Long) {

    constructor() : this("","","",System.currentTimeMillis())
}