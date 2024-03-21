package com.example.afyamkononi.doctors.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.DoctorChatBinding
import com.example.afyamkononi.shared.model.UserData

class DoctorChatAdapter(
    private val list: MutableList<UserData>,
    val clickListener: OnChatClickListener
) : RecyclerView.Adapter<DoctorChatAdapter.ChatViewModel>() {

    inner class ChatViewModel(val doctorChatBinding: DoctorChatBinding) :
        RecyclerView.ViewHolder(doctorChatBinding.root) {
        fun setData(chat: UserData, action: OnChatClickListener) {
            doctorChatBinding.apply {
                name.text = chat.name
            }
            doctorChatBinding.root.setOnClickListener {
                action.onChatClick(chat, adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewModel {
        return ChatViewModel(
            DoctorChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewModel, position: Int) {
        val chat = list[position]
        holder.setData(chat, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnChatClickListener {
        fun onChatClick(chat: UserData, position: Int)
    }
}