package ru.netology.nmedia.Post

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val shareCount: Int = 0,
    val likes: Int = 0,
    val likedByMe: Boolean = false
)