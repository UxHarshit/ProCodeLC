package com.uxharshit.procodelc.services

import com.uxharshit.procodelc.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoadingCompany {
    private val okHttp = OkHttpClient.Builder()
    private var urlBase = "http://192.100.18.27:3000"
    private val builder = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}