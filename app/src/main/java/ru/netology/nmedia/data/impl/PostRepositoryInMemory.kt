package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post.Post
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemory : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Content $index",
                published = "21.05.2022",
                likes = 999,
                shareCount = 999_999
            )
        }

    )

    override fun like(postId: Long) {
        data.value = posts.map {
            val likedOrNotCount =
                if (!it.likedByMe) it.likes + 1 else it.likes - 1
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = likedOrNotCount
            )
        }

    }

    override fun share(postId: Long) {
        data.value = posts.map {
            val countShare = it.shareCount + 1
            if (it.id != postId) it
            else it.copy(shareCount = countShare)
        }

    }


}