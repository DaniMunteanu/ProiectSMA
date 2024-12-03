package com.example.proiectsma.model

data class Channel(
    var id : String,
    var name : String,
    var createdAt : Long) {

    constructor() : this("","",0L)
}