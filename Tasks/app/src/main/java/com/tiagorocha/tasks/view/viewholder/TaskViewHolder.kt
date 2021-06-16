package com.tiagorocha.tasks.view.viewholder

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.listener.TaskListener
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.service.repository.Priorityrepository
import java.text.SimpleDateFormat
import java.util.*

class TaskViewHolder(itemView: View ,val listener : TaskListener):RecyclerView.ViewHolder(itemView){

    private val _PriorityRepository = Priorityrepository(itemView.context)
    private val _DateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private var _TextDescription: TextView = itemView.findViewById(R.id.text_description)
    private var _TextPriority: TextView = itemView.findViewById(R.id.text_priority)
    private var _TextDueDate: TextView = itemView.findViewById(R.id.text_due_date)
    private var _ImageTask: ImageView = itemView.findViewById(R.id.image_task)


    fun  bindData(task: TaskModel){
        this._TextDescription.text = task.description
        this._TextPriority.text= _PriorityRepository.getById(task.priorityId).description
        val date = SimpleDateFormat("yyyy-MM-dd").parse(task.date)
        this._TextDueDate.text=_DateFormat.format(date)

        if(task.complete){
            _TextDescription.setTextColor(Color.GRAY)
            _ImageTask.setImageResource(R.drawable.ic_done)
        }
        else{
            _TextDescription.setTextColor(Color.BLACK)
            _ImageTask.setImageResource(R.drawable.ic_todo)
        }
        //evertos
        _TextDescription.setOnClickListener {listener.onListClick(task.id)}

        _ImageTask.setOnClickListener{
            if(task.complete) {
                listener.onUndoClick(task.id)
            }else{
                listener.onCompleteClick(task.id)
            }

        }
        _TextDescription.setOnLongClickListener{
            androidx.appcompat.app.AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de Tarefa")
                .setMessage("remoção da Tarefa ?")
                .setPositiveButton("Sim"){dialog,which ->
                    listener.onDeleteClick(task.id)

                }
                .setNeutralButton("Cancelar", null)
                .show()
            true
        }
    }

}