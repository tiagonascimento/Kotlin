package com.tiagorocha.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiagorocha.tasks.service.model.HeaderModel
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.service.repository.PersonRepository
import com.tiagorocha.tasks.service.repository.local.SecurityPreferences

class FormUserViewModel (application: Application) : AndroidViewModel(application){
    private val _personRepository = PersonRepository(application)
    private val _sharedPreference = SecurityPreferences(application)

    private val _create = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = _create

    fun create (name:String, email:String, password: String, news:Boolean){

        _personRepository.create(name, email, password, news, object : IApiListener<HeaderModel> {
            override fun onSuccess(model: HeaderModel) {
                _sharedPreference.store(TaskConstants.SHAREND.PERSON_NAME, model.name)
                _sharedPreference.store(TaskConstants.SHAREND.PERSON_KEY, model.personKey)
                _sharedPreference.store(TaskConstants.SHAREND.TOKEN_KEY, model.token)

                _create.value = ValidationListener()

            }

            override fun onFailure(mensage: String) {
                _create.value = ValidationListener(mensage)
            }

        })

    }
}