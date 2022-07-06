package com.vijay.chatter.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.vijay.chatter.Model.MessageModel
import com.vijay.chatter.R
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(private val message_list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemReceive = 1
    private val itemSend = 2

    private val auth = FirebaseAuth.getInstance()
    private lateinit var ctx: Context
    private lateinit var receiverViewHolder: ReceiverViewHolder
    private lateinit var senderViewHolder: SenderViewHolder

    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val receiverMessage: TextView = view.findViewById(R.id.text_receive)
        val receiverMessageTime: TextView = view.findViewById(R.id.time_receiver)
    }

    class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val senderMessage: TextView = view.findViewById(R.id.text_send)
        val senderMessageTime: TextView = view.findViewById(R.id.time_sender)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        ctx = parent.context

        return if (viewType == itemReceive) {
            // Inflate Receiver
            val myView: View =
                LayoutInflater.from(ctx).inflate(R.layout.receive_message, parent, false)
            receiverViewHolder = ReceiverViewHolder(myView)
            receiverViewHolder
        } else {
            // Inflate Sender
            val myView: View =
                LayoutInflater.from(ctx).inflate(R.layout.send_message, parent, false)
            senderViewHolder = SenderViewHolder(myView)
            senderViewHolder
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = message_list[position]
        if (holder.javaClass == SenderViewHolder::class.java) {
            val newHolder = holder as SenderViewHolder
            newHolder.senderMessage.text = currentMessage.message.toString().trim()
            newHolder.senderMessageTime.text = currentMessage.time.toString()
        }else{
            val newHolder = holder as ReceiverViewHolder
            newHolder.receiverMessage.text = currentMessage.message.toString().trim()
            newHolder.receiverMessageTime.text = currentMessage.time.toString()
        }

        // Fore deleting the message

//        holder.itemView.setOnClickListener(View.OnClickListener {
//            AlertDialog.Builder(ctx).setTitle("Delete")
//                .setMessage("Are you sure you want to delete this message")
//                .setPositiveButton("Yes", DialogInterface.OnClickListener(
//
//                )).setNegativeButton("No", DialogInterface.OnClickListener(
//
//                )).show()
//        })
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = message_list[position]
        return if (currentMessage.uid == auth.currentUser?.uid) {
            itemSend
        } else {
            itemReceive
        }

    }

    override fun getItemCount(): Int {
        return message_list.size
    }


}