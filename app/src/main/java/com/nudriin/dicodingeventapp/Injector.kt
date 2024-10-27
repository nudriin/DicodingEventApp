package com.nudriin.dicodingeventapp

import android.content.Context
import com.nudriin.dicodingeventapp.data.EventsRepository
import com.nudriin.dicodingeventapp.data.local.room.EventDatabase
import com.nudriin.dicodingeventapp.data.retrofit.ApiConfig
import com.nudriin.dicodingeventapp.util.AppExecutors

object Injector {
    fun provideRepository(context: Context): EventsRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventsRepository.getInstance(apiService, dao, appExecutors)
    }
}