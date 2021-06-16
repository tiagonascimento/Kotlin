package com.tiagorocha.tasks.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.listener.TaskListener
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.view.viewholder.TaskViewHolder

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    private var _List: List<TaskModel> = arrayListOf()
    private lateinit var _Listener: TaskListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_task_list, parent, false)
        return TaskViewHolder(item, _Listener)

    }

    override fun getItemCount(): Int {
        return _List.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindData(_List[position])
    }

    fun attachListener(listener: TaskListener) {
        _Listener = listener
    }

    fun updateList(list: List<TaskModel>) {
        _List = list
        notifyDataSetChanged()
    }
}