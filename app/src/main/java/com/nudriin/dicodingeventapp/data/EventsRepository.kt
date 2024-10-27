package com.nudriin.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity
import com.nudriin.dicodingeventapp.data.local.room.EventDao
import com.nudriin.dicodingeventapp.data.response.EventResponse
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.data.retrofit.ApiService
import com.nudriin.dicodingeventapp.ui.home.HomeViewModel
import com.nudriin.dicodingeventapp.util.AppExecutors
import com.nudriin.dicodingeventapp.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors
) {
    private val upcomingResult = MediatorLiveData<Result<List<ListEventsItem>>>()
    private val finishedResult = MediatorLiveData<Result<List<ListEventsItem>>>()

    fun getUpcomingEvent(): LiveData<Result<List<ListEventsItem>>>{
        upcomingResult.value = Result.Loading

        val client = apiService.getAllEvents("1")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()
                    if(body?.listEvents?.size == 0){
                        upcomingResult.value = Result.Error(Event("Data tidak ditemukan"))
                    }
                    upcomingResult.value = Result.Success(body?.listEvents!!)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                upcomingResult.value = Result.Error(Event(t.message.toString()))
                Log.e(HomeViewModel.TAG, "onFailure: ${t.message}")
            }
        })
        return upcomingResult
    }

    fun getFinishedEvent(): LiveData<Result<List<ListEventsItem>>>{
        finishedResult.value = Result.Loading

        val client = apiService.getAllEvents("0")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()
                    if(body?.listEvents?.size == 0){
                        finishedResult.value = Result.Error(Event("Data tidak ditemukan"))
                    }
                    finishedResult.value = Result.Success(body?.listEvents!!)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                finishedResult.value = Result.Error(Event(t.message.toString()))
                Log.e(HomeViewModel.TAG, "onFailure: ${t.message}")
            }

        })

        return finishedResult
    }

    fun setFavoriteEvent(event: EventEntity) {
        appExecutors.diskIO.execute{
            event.isFavorite = true
            eventDao.insertEvent(event)
        }
    }

    fun getFavoriteEvent(): LiveData<List<EventEntity>>{
        return eventDao.getAllEvents()
    }

    fun getFavoriteEventById(id: Int): LiveData<EventEntity?> {
        return eventDao.getEventById(id)
    }

    fun deleteFavoriteEventById(id: Int) {
        appExecutors.diskIO.execute {
            eventDao.deleteEventById(id)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: EventsRepository? = null

        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
            appExecutors: AppExecutors
        ): EventsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: EventsRepository(apiService, eventDao, appExecutors)
            }.also { INSTANCE = it }
    }
}