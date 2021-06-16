package com.tiagorocha.tasks.viewmodel

import android.app.Application
import android.text.BoringLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.service.model.PriorityModel
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.service.repository.Priorityrepository
import com.tiagorocha.tasks.service.repository.TaskRepository


class TaskFormViewModel(application: Application): AndroidViewModel(application) {

    private  val _prioriyRepository  = Priorityrepository(application)
    private  val _taskRepository  = TaskRepository(application)
    private val _priorytiList = MutableLiveData<List<PriorityModel>>()
    private val _Validation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = _Validation
    val priorites : LiveData<List<PriorityModel>> = _priorytiList
    private val _Task = MutableLiveData<TaskModel>()
    var task: LiveData<TaskModel> = _Task


    fun listPriorities(){
        _priorytiList.value = _prioriyRepository.list()
    }
    fun save(task:TaskModel){
        if(task.id==0) {
            _taskRepository.create(task, object : IApiListener<Boolean> {
                override fun onSuccess(model: Boolean) {
                    _Validation.value = ValidationListener()
                }
                override fun onFailure(mensage: String) {
                    _Validation.value = ValidationListener(mensage)
                }
            })
        }else{
            _taskRepository.update(task, object : IApiListener<Boolean> {
                override fun onSuccess(model: Boolean) {
                    _Validation.value = ValidationListener()
                }
                override fun onFailure(mensage: String) {
                    _Validation.value = ValidationListener(mensage)
                }
            })

        }
    }

    fun load(id:Int){
        _taskRepository.load(id, object : IApiListener<TaskModel>{
            override fun onSuccess(model: TaskModel) {
               _Task.value = model
            }
            override fun onFailure(mensage: String) {
                _Validation.value = ValidationListener(mensage)
            }
        })
    }
}