package com.gsrg.data.common.remote.adapter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitAdapterImpl @Inject constructor() : RetrofitAdapter {

    private val gson: Gson = GsonBuilder()
        .create()

    override fun getRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
