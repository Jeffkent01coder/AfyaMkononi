package com.example.afyamkononi.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityTodoBinding
import com.example.afyamkononi.todoscreens.AddTodo

class Todo : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTodoBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addTaskBtn.setOnClickListener {
            startActivity(Intent(this, AddTodo::class.java))
        }
    }
}