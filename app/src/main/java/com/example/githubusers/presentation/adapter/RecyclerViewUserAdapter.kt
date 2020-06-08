package com.example.githubusers.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.model.UserModelView
import kotlinx.android.synthetic.main.row_user.view.*

class RecyclerViewUserAdapter(private var listUser: List<UserModelView>) :
    RecyclerView.Adapter<RecyclerViewUserAdapter.UserViewHolder>() {

    private var parentContext: Context? = null

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar = view.image_view_user
        var username = view.text_view_user_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        parentContext = parent.context
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        parentContext?.let { Glide.with(it).load(listUser[position].avatar).into(holder.avatar) }
        holder.username.text = listUser[position].username
    }

    fun setData(newData: List<UserModelView>) {
        this.listUser = newData
        notifyDataSetChanged()
    }

    fun clearData(){
        this.listUser = listOf()
        notifyDataSetChanged()
    }
}