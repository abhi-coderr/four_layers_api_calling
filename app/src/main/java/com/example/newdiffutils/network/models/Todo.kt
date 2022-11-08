package com.example.newdiffutils.network.models

data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)