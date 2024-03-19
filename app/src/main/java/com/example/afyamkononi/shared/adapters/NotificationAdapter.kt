package com.example.afyamkononi.shared.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.NotificationItemBinding
import com.example.afyamkononi.shared.model.NotificationData

class NotificationAdapter(
    private val list: MutableList<NotificationData>,
    val clickListener: OnNotificationClickListener
) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val notificationItemBinding: NotificationItemBinding) :
        RecyclerView.ViewHolder(notificationItemBinding.root) {
        fun setData(
            notification: NotificationData,
            action: OnNotificationClickListener
        ) {
            notificationItemBinding.apply {
                notificationTitle.text = notification.notificationTitle
                notificationContent.text = notification.notificationContent
            }
            notificationItemBinding.root.setOnClickListener {
                action.onNotificationClick(notification, adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = list[position]
        holder.setData(notification, clickListener)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnNotificationClickListener {
        fun onNotificationClick(notification: NotificationData, position: Int)
    }
}