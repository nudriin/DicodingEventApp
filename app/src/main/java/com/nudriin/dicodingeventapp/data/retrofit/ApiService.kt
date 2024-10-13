package com.nudriin.dicodingeventapp.data.retrofit

import com.nudriin.dicodingeventapp.data.response.EventDetailResponse
import com.nudriin.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getAllEvents(@Query("active") active: String): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailById(@Path("id") id: String): Call<EventDetailResponse>
}