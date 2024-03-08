package com.example.afyamkononi.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afyamkononi.adapters.TodoAdapter
import com.example.afyamkononi.databinding.ActivityTodoBinding
import com.example.afyamkononi.model.TodoData
import com.example.afyamkononi.todoscreens.AddTodo
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Todo : AppCompatActivity(), AddTodo.DialogNextBtnClickListener,
    TodoAdapter.TodoAdapterClickInterface {
    private lateinit var binding: ActivityTodoBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    private var popUpFragment: AddTodo? = null
    private lateinit var adapter: TodoAdapter
    private lateinit var mList: MutableList<TodoData>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTodoBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        getDataFromFirebase()
        registerEvents()

    }

    private fun registerEvents() {
        binding.addTaskBtn.setOnClickListener {
            val intent = Intent(this, AddTodo::class.java)
            startActivity(intent)
        }
    }


    private fun init() {
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("TodoTasks")
            .child(auth.currentUser?.uid.toString())

        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mList = mutableListOf()
        adapter = TodoAdapter(mList)
        adapter.setListener(this)
        binding.mainRecyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for (taskSnapshot in snapshot.children) {
                    val todoTask = taskSnapshot.key?.let {
                        TodoData(it, taskSnapshot.value.toString())
                    }
                    if (todoTask != null) {
                        mList.add(todoTask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Todo, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDeleteTaskBtnClicked(todoData: TodoData) {
        databaseRef.child(todoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this@Todo, "Todo Task Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this@Todo, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditTaskBtnClicked(todoData: TodoData) {
        val intent = Intent(this, AddTodo::class.java).apply {
            putExtra("taskId", todoData.taskId)
            putExtra("task", todoData.task)
        }
        startActivity(intent)
    }


    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Todo Task saved Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            todoEt.text = null
        }
    }


    override fun onUpdateTask(todoData: TodoData, todoEt: TextInputEditText) {
        val map = HashMap<String, Any>()
        map[todoData.taskId] = todoData.task
        databaseRef.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Todo Task Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            todoEt.text = null
        }
    }

}