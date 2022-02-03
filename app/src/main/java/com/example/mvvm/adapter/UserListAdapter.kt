package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.model.User

class UserListAdapter(
    var users: ArrayList<User>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateUsers(user: List<User>) {
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.email)
        fun bind(user: User) {
            name.text = user.name
            email.text = user.email
        }

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}