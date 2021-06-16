package com.tiagorocha.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.service.repository.local.SecurityPreferences

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _SharedPreference = SecurityPreferences(application)
    private val _UserName = MutableLiveData<String>()
    var userName: LiveData<String> = _UserName

    private val _Logout = MutableLiveData<Boolean>()
    var logout: LiveData<Boolean> = _Logout

    fun loadUserName(){
        _UserName.value = _SharedPreference.get(TaskConstants.SHAREND.PERSON_NAME)
    }
    fun logout(){
        _SharedPreference.remove(TaskConstants.SHAREND.PERSON_KEY)
        _SharedPreference.remove(TaskConstants.SHAREND.TOKEN_KEY)
        _SharedPreference.remove(TaskConstants.SHAREND.PERSON_NAME)
        _Logout.value = true
    }
}