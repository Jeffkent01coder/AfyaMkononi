package com.example.afyamkononi.machineLearning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.PreviousItemBinding
import com.example.afyamkononi.machineLearning.model.ScanData

class ScanAdapter(private val list: MutableList<ScanData>) :
    RecyclerView.Adapter<ScanAdapter.ScanViewModel>() {

    inner class ScanViewModel(val previousItemBinding: PreviousItemBinding) :
        RecyclerView.ViewHolder(previousItemBinding.root) {
        fun setData(scan: ScanData) {
            previousItemBinding.apply {
                scanContent.text = scan.scanContent
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewModel {
        return ScanViewModel(
            PreviousItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ScanViewModel, position: Int) {
        val scan = list[position]
        holder.setData(scan)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}