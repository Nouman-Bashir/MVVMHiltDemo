package com.nouman.bashir.mvvm.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nouman.bashir.mvvm.example.databinding.ItemUserBinding
import com.nouman.bashir.mvvm.example.models.User

class UserAdapter : ListAdapter<User,UserAdapter.UserViewHolder>(DIFF) {
    companion object{
        val DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User)=oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User)=oldItem.id==newItem.id
        }
    }
    inner class UserViewHolder(view:ItemUserBinding) : RecyclerView.ViewHolder(view.root){
        private val name = view.userName
        private val email = view.userEmail
        fun bind(user: User){
            name.text = user.name
            email.text = user.email
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}