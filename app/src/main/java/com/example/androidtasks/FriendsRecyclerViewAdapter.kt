package com.example.androidtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendsRecyclerViewAdapter(
    private val friends: List<FriendItem>
) : RecyclerView.Adapter<FriendsRecyclerViewAdapter.FriendsViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class FriendsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name)
        val photo: ImageView = view.findViewById(R.id.iv_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val friendItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_friend_item, parent, false)
        return FriendsViewHolder(friendItemView)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.name.text = "${friends[position].secondName} ${friends[position].firstName}"
        holder.photo.setImageResource(friends[position].photo)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, friends[position])
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun interface OnClickListener {
        fun onClick(position: Int, friend: FriendItem)
    }
}