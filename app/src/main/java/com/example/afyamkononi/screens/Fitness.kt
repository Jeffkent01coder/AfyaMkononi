package com.example.afyamkononi.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.adapters.ExerciseAdapter
import com.example.afyamkononi.apiservice.RetrofitClient
import com.example.afyamkononi.databinding.ActivityFitnessBinding
import com.example.afyamkononi.models.ExerciseModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fitness : AppCompatActivity() {
    private lateinit var binding : ActivityFitnessBinding
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseAdapter = ExerciseAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Fitness)
            adapter = exerciseAdapter
        }

        //37464653damsh941e7c9227e0bf9p1b0871jsnbe2ddf38abba
        val apiService = RetrofitClient.create()
        apiService.getExercises(10, "")
            .enqueue(object : Callback<List<ExerciseModelItem>> {
                override fun onResponse(
                    call: Call<List<ExerciseModelItem>>,
                    response: Response<List<ExerciseModelItem>>
                ) {
                    if (response.isSuccessful) {
                        Log.i("Res", "Error is: "+ response.body().toString())
                        val exercises = response.body()
                        exercises?.let {
                            exerciseAdapter.setData(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ExerciseModelItem>>, t: Throwable) {
                    Log.d("Res", "Error is: "+ t.toString())
                    Toast.makeText(this@Fitness, "Not working", Toast.LENGTH_LONG).show()
                }
            })
    }
}
