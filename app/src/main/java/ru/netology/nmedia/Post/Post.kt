package ru.netology.nmedia.Post

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var shareCount: Int = 0,
    var likes: Int = 0,
    var likedByMe: Boolean = false
)