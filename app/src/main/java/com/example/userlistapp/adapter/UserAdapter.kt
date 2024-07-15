package com.example.userlistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userlistapp.databinding.ItemUserBinding
import com.example.userlistapp.data.User
import com.squareup.picasso.Picasso

class UserAdapter(private var users: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun updateUsers(newUsers: List<User>) {
        this.users = newUsers
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userName.text = "${user.first_name} ${user.last_name}"
            binding.userEmail.text = user.email
            Picasso.get().load(user.avatar).into(binding.userAvatar)
        }
    }
}
