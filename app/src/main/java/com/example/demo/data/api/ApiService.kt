package com.example.demo.data.api

import com.example.demo.data.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getDataFromAPI(@Query("page") query: Int): Response<ApiResponse>

    @GET("character")
    fun getDataFromApi(@Query("page") query: Int): Call<ApiResponse>
}