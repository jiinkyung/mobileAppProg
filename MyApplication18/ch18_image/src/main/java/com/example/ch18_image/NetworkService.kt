package com.example.ch18_image

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("CountryFlagService2/getCountryFlagList2")
    fun getList(
        @Query("pageNo") page:Int,
        @Query("numOfRows") pageSize:Int,
        @Query("returnType") returnType: String,
        @Query("serviceKey") apiKey: String?
    ): Call<PageListModel>
}