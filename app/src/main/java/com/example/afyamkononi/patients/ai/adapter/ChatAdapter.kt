package com.example.afyamkononi.patients.ai.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.ItemResponseMessageBinding
import com.example.afyamkononi.databinding.ItemSenderMessageBinding
import com.example.afyamkononi.patients.ai.model.Message
import com.example.afyamkononi.patients.ai.model.MessageType

class ChatAdapter(private val messages: MutableList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder for sender's message
    inner class SenderViewHolder(private val binding: ItemSenderMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.senderMessage.text = message.text
        }
    }

    // ViewHolder for GPT response
    inner class ResponseViewHolder(private val binding: ItemResponseMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.responseMessage.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MessageType.SENDER.ordinal -> {
                val binding = ItemSenderMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SenderViewHolder(binding)
            }
            MessageType.RESPONSE.ordinal -> {
                val binding = ItemResponseMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ResponseViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SenderViewHolder -> holder.bind(message)
            is ResponseViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return messages[position].type.ordinal
    }
}