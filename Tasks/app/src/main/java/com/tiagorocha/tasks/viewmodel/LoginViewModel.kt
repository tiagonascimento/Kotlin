package com.tiagorocha.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiagorocha.tasks.service.HeaderModel
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.service.repository.PersonRepository
import com.tiagorocha.tasks.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _personRepository = PersonRepository(application)

    private val _sharedPreference = SecurityPreferences(application)

    private val _login = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = _login

    private val _loogedUser = MutableLiveData<Boolean>()
    var loogedUser: LiveData<Boolean> = _loogedUser

    fun doLogin(email: String, password: String) {
        _personRepository.login(email, password, object : IApiListener {
            override fun onSuccess(model: HeaderModel) {

                _sharedPreference.store(TaskConstants.SHAREND.PERSON_NAME, model.name)
                _sharedPreference.store(TaskConstants.SHAREND.PERSON_KEY, model.personKey)
                _sharedPreference.store(TaskConstants.SHAREND.TOKEN_KEY, model.token)

                _login.value = ValidationListener()
            }

            override fun onFailure(menssage: String) {
                _login.value = ValidationListener(menssage)
            }

        })
    }

    fun verifyLoggedUser() {
        val token  = _sharedPreference.get(TaskConstants.SHAREND.TOKEN_KEY)
        val key  = _sharedPreference.get(TaskConstants.SHAREND.PERSON_KEY)
        _loogedUser.value = (token !="" && key!="")
    }
}
