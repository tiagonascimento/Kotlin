package com.tiagorocha.tasks.service.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.reflect.TypeToken
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.HeaderModel
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.repository.remote.Interfaces.IPersonService
import com.tiagorocha.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {
    private val _remote = RetrofitClient.createService(IPersonService::class.java)

    fun login(email: String, password: String, listener: IApiListener) {

        var call: Call<HeaderModel> = _remote.login(email, password)
        //assincrona
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {

                    val valitation  = Gson().fromJson(response.errorBody()?.string(), String::class.java)                  
                      listener.onFailure(valitation)
                } else {
                    response.body()?.let {listener  .onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
              //  listener.onFailure(t.message.toString())
            }

        })
    }


    // _remote.login(email, password)
}
