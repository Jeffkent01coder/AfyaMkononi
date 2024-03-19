package com.example.afyamkononi.shared.chatMongo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.DoctorChatItemBinding
import com.example.afyamkononi.shared.model.DoctorData

class DoctorsChatAdapter(
    private val list: MutableList<DoctorData>,
    val clickListener: OnDoctorClickListener
) : RecyclerView.Adapter<DoctorsChatAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(val doctorChatItemBinding: DoctorChatItemBinding) :
        RecyclerView.ViewHolder(doctorChatItemBinding.root) {
        fun setData(doctor: DoctorData, action: OnDoctorClickListener) {
            doctorChatItemBinding.apply {
                doctorName.text = doctor.doctorName
                doctorProfession.text = doctor.doctorProfession
            }
            doctorChatItemBinding.root.setOnClickListener {
                action.onDoctorClick(doctor, adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            DoctorChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = list[position]
        holder.setData(doctor, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnDoctorClickListener {
        fun onDoctorClick(doctor: DoctorData, position: Int)
    }
}