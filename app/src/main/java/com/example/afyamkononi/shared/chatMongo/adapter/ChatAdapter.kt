package com.example.afyamkononi.shared.chatMongo.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.shared.chatMongo.model.Message
import com.example.afyamkononi.databinding.MessageItemBinding

class MessageAdapter(private var messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.messageTextView.text = message.message

            if (message.sender == "John") {
                // If sender is "John", set gravity to end (right)
                binding.senderTextView.gravity = Gravity.END
                binding.receiverTextView.gravity = Gravity.END
            } else {
                // If sender is not "John", set gravity to start (left)
                binding.senderTextView.gravity = Gravity.START
                binding.receiverTextView.gravity = Gravity.START
            }

            // Set sender and receiver TextViews
            binding.senderTextView.text = message.sender
            binding.receiverTextView.text = message.receiver
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    fun updateData(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}
