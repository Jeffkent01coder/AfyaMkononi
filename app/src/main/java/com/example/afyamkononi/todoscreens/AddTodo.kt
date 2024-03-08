package com.example.afyamkononi.todoscreens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afyamkononi.databinding.ActivityAddTodoBinding
import com.example.afyamkononi.model.TodoData
import com.google.android.material.textfield.TextInputEditText

class AddTodo : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var listener: DialogNextBtnClickListener

    private var todoData: TodoData? = null

//    fun setListener(listener: DialogNextBtnClickListener) {
//        this.listener = listener
//
//    }

    companion object {
        const val TAG = "AddTodo"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent != null && intent.extras != null) {
            val taskId = intent.getStringExtra("taskId")
            val task = intent.getStringExtra("task")
            todoData = TodoData(taskId ?: "", task ?: "")
            binding.todoEt.setText(todoData?.task)
        }

        registerEvents()

    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (todoData == null) {
                    listener.onSaveTask(todoTask, binding.todoEt)
                } else {
                    todoData?.task = todoTask
                    listener.onUpdateTask(todoData!!, binding.todoEt)
                }
            } else {
                Toast.makeText(this, "Please add a Task", Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            finish()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(todoData: TodoData, todoEt: TextInputEditText)
    }
}