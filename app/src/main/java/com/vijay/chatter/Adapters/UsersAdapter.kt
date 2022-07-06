package com.vijay.chatter.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vijay.chatter.ChatActivity
import com.vijay.chatter.Fragments.ChatsFragment
import com.vijay.chatter.MainActivity
import com.vijay.chatter.Model.Users
import com.vijay.chatter.R
import com.vijay.chatter.databinding.FragmentChatsBinding
import kotlin.contracts.contract


class UsersAdapter(private val users: ArrayList<Users>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private lateinit var ctx:Context
    private lateinit var userViewHolder: UserViewHolder

    public class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.user_name)
        val profilepicture: ImageView = view.findViewById(R.id.profile_picture)
        val lastmessage: TextView = view.findViewById(R.id.last_message)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        ctx = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.show_user, parent, false)
        userViewHolder = UserViewHolder(view)
        return userViewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        userViewHolder.username.text = currentUser.userName
        userViewHolder.profilepicture.setImageURI(currentUser.profilePic?.toUri())


        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, ChatActivity::class.java)
            intent.putExtra("uid", currentUser.uid)
            intent.putExtra("profilepic", currentUser.profilePic)
            intent.putExtra("username", currentUser.userName)
            ctx.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}