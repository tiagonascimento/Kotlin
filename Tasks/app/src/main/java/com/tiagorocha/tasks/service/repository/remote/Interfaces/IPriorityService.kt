package com.tiagorocha.tasks.service.repository.remote.Interfaces

import com.tiagorocha.tasks.service.model.PriorityModel
import retrofit2.Call
import retrofit2.http.GET


interface IPriorityService {
    @GET("Priority")
    fun all(): Call<List<PriorityModel>>
}