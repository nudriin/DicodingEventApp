package com.nudriin.dicodingeventapp.data

import androidx.lifecycle.LiveData
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity
import com.nudriin.dicodingeventapp.data.local.room.EventDao
import com.nudriin.dicodingeventapp.data.retrofit.ApiService
import com.nudriin.dicodingeventapp.util.AppExecutors

class EventsRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors
) {
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
        eventDao.deleteEventById(id)
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