package com.example.afyamkononi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.DoctorItemBinding
import com.example.afyamkononi.model.DoctorData
//
//class DoctorAdapter(
//    private val list: ArrayAdapter<DoctorData>,
//    val clickListener: OnDoctorClickListener
//) :
//    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {
//
//    inner class DoctorViewHolder(val doctorItemBinding: DoctorItemBinding) :
//        RecyclerView.ViewHolder(doctorItemBinding.root) {
//        fun setData(doctor: DoctorData, action: OnDoctorClickListener) {
//            doctorItemBinding.apply {
//                doctorImage.setImageResource(doctor.image)
//                doctorName.text = doctor.doctorName
//                doctorProfession.text = doctor.doctorProfession
//            }
//            doctorItemBinding.root.setOnClickListener {
//                action.onDoctorClick(doctor, adapterPosition)
//            }
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
//        return DoctorViewHolder(
//            DoctorItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
//        val doctor = list[position]
//        holder.setData(doctor, clickListener)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    interface OnDoctorClickListener {
//        fun onDoctorClick(doctor: DoctorData, position: Int)
//
//    }
//}