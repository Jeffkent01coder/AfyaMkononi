package com.example.afyamkononi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.DoctorItemBinding
import com.example.afyamkononi.model.DoctorData

class DoctorsAdapter(
    private val list: ArrayList<DoctorData>,
    val clickListener: OnDoctorClickListener
) :
    RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(val doctorItemBinding: DoctorItemBinding) :
        RecyclerView.ViewHolder(doctorItemBinding.root) {
        fun setData(doctor: DoctorData, action: OnDoctorClickListener) {
            doctorItemBinding.apply {
                doctorName.text = doctor.doctorName
                doctor.image.let { doctorImage.setImageResource(it) }
                doctorProfession.text = doctor.doctorProfession

            }
            doctorItemBinding.root.setOnClickListener {
                action.onDoctorClick(doctor, adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            DoctorItemBinding.inflate(
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