package com.uxharshit.procodelc.services

import com.google.gson.JsonObject
import com.uxharshit.procodelc.models.CompanyInfoModel
import com.uxharshit.procodelc.models.CompanyInfoModelItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CompanyListIn {

    @Headers("Content-Type: application/json")
    @POST("getCompanyInfo")
    suspend fun getCompanyInfo(@Body jsonObject: JsonObject): Response<ArrayList<CompanyInfoModelItem>>

    @GET("getCompany")
    suspend fun getCompanyList(): Response<List<String>>
}