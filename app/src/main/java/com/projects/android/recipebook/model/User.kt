package com.projects.android.recipebook.model

import com.google.firebase.database.ServerValue

data class User(
    val email: String = "",
    val createdAt: Any = ServerValue.TIMESTAMP,
    val lastLogin: Any = ServerValue.TIMESTAMP
)
