package com.nudriin.dicodingeventapp.data.retrofit

import com.nudriin.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getAllEvents(@Query("active") active: String): Call<EventResponse>
}