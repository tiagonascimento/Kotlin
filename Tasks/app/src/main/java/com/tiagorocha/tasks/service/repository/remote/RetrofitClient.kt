package com.tiagorocha.tasks.service.repository.remote

import com.tiagorocha.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var _retrofit: Retrofit
        private val _baseURL = "http://devmasterteam.com/CursoAndroidAPI/"
        private var tokenKey =""
        private var personKey =""

        private fun getRetrofitInstance(): Retrofit {
            val httpcliente = OkHttpClient.Builder()
            httpcliente.addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                   val request = chain.request()
                       .newBuilder()
                       .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
                       .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                       .build()
                    return chain.proceed(request)
                }
            })
            if (!Companion::_retrofit.isInitialized) {
                _retrofit = Retrofit.Builder()
                    .baseUrl(_baseURL)
                    .client(httpcliente.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return _retrofit
        }

        fun <T> createService(serviceClass:  Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }
        fun addHeader(token:String, personKey:String){
            this.personKey = personKey
            this.tokenKey = token
        }

    }
}