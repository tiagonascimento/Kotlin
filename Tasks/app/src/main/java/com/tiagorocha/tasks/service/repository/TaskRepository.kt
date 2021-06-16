package com.tiagorocha.tasks.service.repository

import android.content.Context
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.model.HeaderModel
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.service.repository.remote.Interfaces.IPersonService
import com.tiagorocha.tasks.service.repository.remote.Interfaces.ITaskService
import com.tiagorocha.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.http.Field

class TaskRepository(context: Context) : BaseRepository(context) {
    private val _remote = RetrofitClient.createService(ITaskService::class.java)

    fun all(listener: IApiListener<List<TaskModel>>){
        val call : Call<List<TaskModel>> = _remote.all()
        CallApi(call, listener)
    }
    fun nextWeek(listener: IApiListener<List<TaskModel>>){
        val call : Call<List<TaskModel>> = _remote.nextWeek()
        CallApi(call, listener)
    }
    fun overdue(listener: IApiListener<List<TaskModel>>){
        val call : Call<List<TaskModel>> = _remote.overdue()
        CallApi(call, listener)
    }
    fun create(task: TaskModel, listener: IApiListener<Boolean>) {
        val call: Call<Boolean> =
            _remote.create(task.priorityId, task.description, task.date, task.complete)
        CallApi(call, listener)
    }
    fun update(task: TaskModel, listener: IApiListener<Boolean>) {
        val call: Call<Boolean> =
            _remote.update(task.id,task.priorityId, task.description, task.date, task.complete)
        CallApi(call, listener)
    }
    fun updateState(id: Int,complete:Boolean, listener: IApiListener<Boolean>) {
        val call: Call<Boolean> = if(complete){
           _remote.complete(id)
       }else{
            _remote.undo(id)
       }
        CallApi(call, listener)
    }
    fun delete(id:Int, listener: IApiListener<Boolean>) {
        val call: Call<Boolean> =
            _remote.delete(id)
        CallApi(call, listener)
    }
    fun load(id: Int, listener: IApiListener<TaskModel>){
        val call: Call<TaskModel> = _remote.load(id)
        CallApi(call, listener)

    }
}