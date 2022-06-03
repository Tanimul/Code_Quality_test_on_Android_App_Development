package com.example.codequalitytestonandroidappdevelopment.utils

import com.example.codequalitytestonandroidappdevelopment.data.model.User
import com.google.firebase.auth.FirebaseAuth

object Variables {
    var userId = FirebaseAuth.getInstance().uid ?: ""
    var user = User()
}