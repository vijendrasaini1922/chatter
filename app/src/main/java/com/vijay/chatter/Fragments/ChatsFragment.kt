package com.vijay.chatter.Fragments

import android.annotation.SuppressLint
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.vijay.chatter.Adapters.UsersAdapter
import com.vijay.chatter.Model.Users
import com.vijay.chatter.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var list: ArrayList<Users>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatsBinding.inflate(inflater, container, false)
        list = ArrayList()
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        usersAdapter = UsersAdapter(list)
        val recyclerView = binding.chatRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = usersAdapter

        var i = 0

        database.child("Users").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(postSnapShot in snapshot.children){ // traversing in database
                    val currentUser = postSnapShot.getValue(Users::class.java)
                    Log.e(tag, "currentUser" + currentUser?.uid)
                    Log.e(tag, "auth.currentUser" + auth.currentUser?.uid)
                    if (auth.currentUser?.uid == currentUser?.uid) {  // for not chatting yourself
                        Log.e(tag, "its me")
                        continue
                    }
                    list.add(currentUser!!)
                }
                usersAdapter.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return binding.root
    }


}