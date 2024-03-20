package com.example.afyamkononi.shared.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.R
import com.example.afyamkononi.databinding.EventItemBinding
import com.example.afyamkononi.shared.model.EventData
import kotlin.random.Random

class EventsAdapter(
    private val list: MutableList<EventData>,
    val clickListener: OnEventClickListener
) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    inner class EventsViewHolder(val eventItemBinding: EventItemBinding) :
        RecyclerView.ViewHolder(eventItemBinding.root) {
        fun setData(event: EventData, action: OnEventClickListener) {
            eventItemBinding.apply {
                personMeet.text = event.eventName
                appointmentTitle.text = event.eventReason
                eventLocation.text = event.eventLocation
                eventTime.text = event.eventTime
                eventDate.text = event.eventDate

            }
            eventItemBinding.root.setOnClickListener {
                action.onEventClick(event, adapterPosition)
            }

        }
        val note_layout = itemView.findViewById<CardView>(R.id.crdContent)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = list[position]
        holder.setData(event, clickListener)
        holder.note_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnEventClickListener {
        fun onEventClick(event: EventData, position: Int)
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.noteColor1)
        list.add(R.color.noteColor2)
        list.add(R.color.noteColor3)
        list.add(R.color.noteColor4)
        list.add(R.color.noteColor5)
        list.add(R.color.noteColor6)
        list.add(R.color.noteColor7)
        list.add(R.color.noteColor8)
        list.add(R.color.noteColor9)
        list.add(R.color.noteColor10)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }
}