package com.example.basicapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.basicapp.R
import com.example.basicapp.data.model.User
import com.example.basicapp.databinding.ItemUserBinding


class UserAdapter : PagingDataAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvUserName.text = user.fullName
                tvUserEmail.text = user.email

                ivUserAvatar.load(user.avatar) {
                    crossfade(true)
                    placeholder(R.drawable.ic_person_placeholder)
                    error(R.drawable.ic_person_placeholder)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}