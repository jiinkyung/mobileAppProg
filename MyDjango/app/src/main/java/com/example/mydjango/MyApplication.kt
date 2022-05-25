package com.example.mydjango

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application {
    companion object {
        var apiService : ApiService
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://172.26.64.1:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init {
            apiService = retrofit.create(ApiService::class.java)
        }
    }
}