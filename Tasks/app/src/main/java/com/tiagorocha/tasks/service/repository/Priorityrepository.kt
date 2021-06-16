package com.tiagorocha.tasks.service.repository

import android.content.Context
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import com.tiagorocha.tasks.service.model.HeaderModel
import com.tiagorocha.tasks.service.model.PriorityModel
import com.tiagorocha.tasks.service.repository.local.TaskDataBase
import com.tiagorocha.tasks.service.repository.remote.Interfaces.IPriorityService
import com.tiagorocha.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Priorityrepository (context: Context) : BaseRepository(context){

    private  val _dataBase = TaskDataBase.getDataBase(context).IPriorityDAO()
    private val _remote = RetrofitClient.createService(IPriorityService::class.java)

     fun all (listener: IApiListener<List<PriorityModel>>){
        val call : Call<List<PriorityModel>> = _remote.all()
         CallApi(call, listener)

/*
         call.enqueue(object: Callback<List<PriorityModel>>{
            override fun onResponse(
                call: Call<List<PriorityModel>>,response: Response<List<PriorityModel>>) {
                if(response.code()== TaskConstants.HTTP.SUCCESS){
                    _dataBase.clear()
                    response.body()?.let { _dataBase.save(it) }
                }
            }
            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })*/
    }
     fun list () = _dataBase.all()
     fun getById (id:Int)= _dataBase.getPriorityById(id)

     fun save(list: List<PriorityModel>){
          _dataBase.clear()
          _dataBase.save(list)
      }


}
