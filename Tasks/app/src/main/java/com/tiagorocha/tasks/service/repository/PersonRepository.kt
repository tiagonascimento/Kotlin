package com.tiagorocha.tasks.service.repository

import android.content.Context
import com.google.gson.Gson
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.model.HeaderModel
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.repository.remote.Interfaces.IPersonService
import com.tiagorocha.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository( context: Context): BaseRepository(context) {
    private val _remote = RetrofitClient.createService(IPersonService::class.java)

    fun login(email: String, password: String, listener: IApiListener<HeaderModel>) {


        var call: Call<HeaderModel> = _remote.login(email, password)
        CallApi(call,listener)

        //assincrona
       // teste(call,listener)

        /*
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {

                    val valitation  = Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                      listener.onFailure(valitation)
                } else {
                    response.body()?.let {listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
              //  listener.onFailure(t.message.toString())
            }

        })
       */
    }

    fun create(name:String, email: String, password: String, news: Boolean, listener: IApiListener<HeaderModel>) {

        var call: Call<HeaderModel> = _remote.create(name, email, password, news)

        //assincrona
        CallApi(call,listener)
        /*
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
         */
    }
    // _remote.login(email, password)
}
