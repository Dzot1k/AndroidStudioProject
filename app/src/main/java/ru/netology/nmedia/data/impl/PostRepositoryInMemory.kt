package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post.Post
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemory : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0L,
            author = "Vladimir",
            content = "Events",
            published = "14.05.2022",
            likes = 999,
            shareCount = 999_999
        )

    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val countLikes =
            if (!currentPost.likedByMe) currentPost.likes + 1 else currentPost.likes - 1
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = countLikes
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val countShare = currentPost.shareCount + 1
        val sharedPost = currentPost.copy(
            shareCount = countShare
        )
        data.value = sharedPost
    }


}