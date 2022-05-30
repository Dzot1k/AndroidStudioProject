package ru.netology.nmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post


        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likeIcon.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareIcon.setOnClickListener { listener.onShareClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                content.text = post.content
                date.text = post.published
                likesCount.text =
                    getTrueCount(likesCount.context, post.likes)
                shareCount.text =
                    getTrueCount(shareCount.context, post.shareCount)
                likeIcon.setImageResource(getLikeIconResId(post.likedByMe))
                options.setOnClickListener { popupMenu.show() }

            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

        private fun getTrueCount(context: Context, count: Int): String {
            if (count in 1000..10_000) {
                val thousands = count / 1000
                val afterPoint = (count % 1000) / 100
                val text = String.format("%d,%d", thousands, afterPoint)

                return if (afterPoint != 0) context.getString(
                    R.string.thousands,
                    text
                ) else context.getString(R.string.thousands, thousands.toString())
            }

            if (count in 10_001..999_999) {
                val thousands = count / 1000
                return context.getString(R.string.thousands, thousands.toString())
            }

            if (count >= 1_000_000) {
                val millions = count / 1_000_000
                val afterPoint = (count % 1_000_000) / 100_000
                val text = String.format("%d,%d", millions, afterPoint)
                return if (afterPoint != 0) context.getString(
                    R.string.million,
                    text
                ) else context.getString(R.string.million, millions.toString())
            }
            return count.toString()
        }


    }


    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}