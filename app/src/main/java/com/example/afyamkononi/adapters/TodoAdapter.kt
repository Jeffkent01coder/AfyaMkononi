package com.example.afyamkononi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afyamkononi.databinding.EachTodoItemBinding
import com.example.afyamkononi.model.TodoData


class TodoAdapter(private val list: MutableList<TodoData>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var listener: TodoAdapterClickInterface? = null
    fun setListener(listener: TodoAdapterClickInterface) {
        this.listener = listener
    }

    inner class TodoViewHolder(val binding: EachTodoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task
                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteTaskBtnClicked(this)
                }
                binding.editTask.setOnClickListener {
                    listener?.onEditTaskBtnClicked(this)

                }
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface TodoAdapterClickInterface {
        fun onDeleteTaskBtnClicked(todoData: TodoData)
        fun onEditTaskBtnClicked(todoData: TodoData)
    }
}