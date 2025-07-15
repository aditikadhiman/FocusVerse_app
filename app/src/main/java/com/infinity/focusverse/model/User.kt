package com.infinity.focusverse.model

import com.google.firebase.Timestamp

data class User(
    val uid: String ="",
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
//    val streak: Int = 0,
//    val completionRate: Int =0,
    val createdAt: Timestamp = Timestamp.now()
)