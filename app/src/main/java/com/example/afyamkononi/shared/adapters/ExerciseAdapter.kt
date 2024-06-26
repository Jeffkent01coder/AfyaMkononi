package com.example.afyamkononi.shared.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afyamkononi.databinding.ItemExerciseBinding
import com.example.afyamkononi.patients.exercise.model.Exercise

class ExerciseAdapter(private var exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding =
            ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

//    fun setData(data: List<Exercise>) {
//        exercises = data
//        notifyDataSetChanged()
//    }


    class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.apply {
                exerciseName.text = exercise.name
                bodyPart.text = exercise.bodyPart
                tool.text = exercise.equipment
                // Load image using Glide into an ImageView
                Glide.with(itemView)
                    .load(exercise.gifUrl)
                    .into(exerciseImageView)
                targetMuscle.text = exercise.secondaryMuscles?.joinToString(", ")
                instructions.text = exercise.instructions?.joinToString("\n\n")
            }
        }
    }
}


