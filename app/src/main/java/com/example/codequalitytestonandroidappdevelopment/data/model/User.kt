package com.example.codequalitytestonandroidappdevelopment.data.model

import java.io.Serializable

data class User(
    val _id: String = "",
    var name: String = "",
    val email: String = "",
    val addedAt: Long = System.currentTimeMillis(),
) : Serializable