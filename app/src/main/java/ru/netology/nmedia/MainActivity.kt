package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.Post.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Vladimir",
            content = "Events",
            published = "14.05.2022",
            likes = 999,
            shareCount = 999_999
        )

        binding.render(post)
        binding.likeIcon?.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.likeIcon.setImageResource(getLikeIconResId(post.likedByMe))
            post.likes = post.likes + getLikeCount(post.likedByMe)
            binding.likesCount.text = getTrueCount(post.likes)

        }

        binding.shareIcon?.setOnClickListener {
            post.shareCount = post.shareCount + 1
            binding.shareCount.text = getTrueCount(post.shareCount)
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        authorName.text = post.author
        content.text = post.content
        date.text = post.published
        likesCount.text = getTrueCount(post.likes)
        shareCount.text = getTrueCount(post.shareCount)

        likeIcon?.setImageResource(getLikeIconResId(post.likedByMe))

    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

    private fun getLikeCount(liked: Boolean) =
        if (liked) 1 else -1


    @SuppressLint("StringFormatInvalid")
    private fun getTrueCount(count: Int): String {
        if (count in 1000..10_000) {
            val thousands = count / 1000
            val afterPoint = (count % 1000) / 100
            val text = String.format("%d,%d", thousands, afterPoint)

            return if (afterPoint != 0) getString(
                R.string.thousands,
                text
            ) else getString(R.string.thousands, thousands.toString())
        }

        if (count in 10_001..999_999) {
            val thousands = count / 1000
            return getString(R.string.thousands, thousands.toString())
        }

        if (count >= 1_000_000) {
            val millions = count / 1_000_000
            val afterPoint = (count % 1_000_000) / 100_000
            val text = String.format("%d,%d", millions, afterPoint)
            return if (afterPoint != 0) getString(
                R.string.million,
                text
            ) else getString(R.string.million, millions.toString())
        }
        return count.toString()
    }

}