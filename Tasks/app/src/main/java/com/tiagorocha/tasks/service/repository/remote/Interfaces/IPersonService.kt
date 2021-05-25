package com.tiagorocha.tasks.service.repository.remote.Interfaces

import com.tiagorocha.tasks.service.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IPersonService {
    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(
        @Field("email")
        email: String,
        @Field("password")
        password: String
    ): Call<HeaderModel>

}