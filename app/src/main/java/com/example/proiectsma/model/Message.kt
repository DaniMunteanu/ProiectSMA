package com.example.proiectsma.model

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.MessageDigestAlgorithms

data class Message(
    val id : String,
    val senderId : String,
    val message : String,
    val senderName: String,
    val createdAt: Long) {
    constructor() : this("","", "", "", System.currentTimeMillis())
}