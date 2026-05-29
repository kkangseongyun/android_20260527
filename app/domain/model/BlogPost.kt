package com.example.androidlab.domain.model

data class BlogPost(
    val id: Long = 0,
    val title: String,
    val content: String,
    val author: String,
    val imageUri: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)