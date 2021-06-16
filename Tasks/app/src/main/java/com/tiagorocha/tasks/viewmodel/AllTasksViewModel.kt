package com.tiagorocha.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.listener.TaskListener
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.service.repository.TaskRepository

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val _Taskrepository = TaskRepository(application)
    private val _list = MutableLiveData<List<TaskModel>>()
    var tasks: LiveData<List<TaskModel>> = _list
    private var _TaskFilder = 0
    private val _Validation = MutableLiveData<ValidationListener>()
    var validation: LiveData<ValidationListener> = _Validation

    fun list(taskFilder: Int) {
        _TaskFilder = taskFilder

        var listener = object : IApiListener<List<TaskModel>> {
            override fun onSuccess(model: List<TaskModel>) {
                _list.value = model
            }

            override fun onFailure(mensage: String) {
                _list.value = arrayListOf()
                _Validation.value = ValidationListener(mensage)
            }
        }
        if (_TaskFilder == TaskConstants.FILTER.ALL){
            _Taskrepository.all(listener)
        }else if(_TaskFilder == TaskConstants.FILTER.NEXT){
            _Taskrepository.nextWeek(listener)
        }else{
            _Taskrepository.overdue(listener)
        }
    }
    fun complete(id:Int){
        updateState(id, true)
    }
    fun undo(id:Int){
        updateState(id, false)
    }
    fun delete(id:Int){
        _Taskrepository.delete(id,  object : IApiListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                list(_TaskFilder)
                _Validation.value = ValidationListener()
            }
            override fun onFailure(mensage: String) {
                _Validation.value = ValidationListener(mensage)
            }
        })
    }
    private fun updateState(id:Int, complete:Boolean){
        _Taskrepository.updateState(id, complete, object : IApiListener<Boolean>{
            override fun onSuccess(model: Boolean) {
               list(_TaskFilder)
            }
            override fun onFailure(mensage: String) {
            }

        })

    }
}