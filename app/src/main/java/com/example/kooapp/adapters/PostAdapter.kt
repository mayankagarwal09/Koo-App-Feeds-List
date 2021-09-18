package com.example.kooapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kooapp.databinding.RowItemPostBinding
import com.example.kooapp.models.Post

class PostAdapter : PagingDataAdapter<Post, PostAdapter.PostViewHolder>(PostDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


    class PostViewHolder private constructor(private val binding: RowItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post?) {
            binding.post = post
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowItemPostBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }

    }
}

class PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}