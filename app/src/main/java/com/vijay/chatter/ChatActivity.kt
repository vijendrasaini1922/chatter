package com.vijay.chatter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.vijay.chatter.Adapters.ChatAdapter
import com.vijay.chatter.Model.MessageModel
import com.vijay.chatter.databinding.ActivityChatBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val senderId: String? = auth.uid
        val receiverId: String? = intent.getStringExtra("uid")
        val userName: String? = intent.getStringExtra("username")
        val profilePic: String? = intent.getStringExtra("profilepic")

        binding.back.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        binding.userName.text = userName.toString()
        // binding profile pic--------------

        val messageList: ArrayList<MessageModel> = ArrayList()
        val chatAdapter: ChatAdapter = ChatAdapter(messageList)
        val recyclerView = binding.chatActivityRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = chatAdapter

        var senderRoom = senderId + receiverId
        var receiverRoom = receiverId + senderId

        binding.sendImage.setOnClickListener(View.OnClickListener {
            val message = binding.messageBox.text.toString().trim()
            var messageModel: MessageModel = MessageModel()

            val time = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("H:mm:ss.SSS")
            val currentTime = time.format(formatter)


            if (senderId != null) {
                messageModel = MessageModel(senderId, message, currentTime)
            }

            database.child("Chats").child(senderRoom).child("Messages").push()
                .setValue(messageModel).addOnSuccessListener {
                    database.child("Chats").child(receiverRoom).child("Messages").push()
                        .setValue(messageModel)
                    binding.messageBox.setText("");
                }
        })

        // filling data in database
        database.child("Chats").child(senderRoom).child("Messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapShot in snapshot.children) {
                        val textMessage = postSnapShot.getValue(MessageModel::class.java)
                        messageList.add(textMessage!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}