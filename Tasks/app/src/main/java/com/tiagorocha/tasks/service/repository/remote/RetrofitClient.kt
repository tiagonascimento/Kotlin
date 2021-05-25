package com.tiagorocha.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var _retrofit: Retrofit
        private val _baseURL = "http://devmasterteam.com/CursoAndroidAPI/"

        private fun getRetrofitInstance(): Retrofit {
            val httpcliente = OkHttpClient.Builder()

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

    }
}