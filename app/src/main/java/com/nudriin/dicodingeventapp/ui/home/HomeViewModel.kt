package com.nudriin.dicodingeventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nudriin.dicodingeventapp.data.EventsRepository
import com.nudriin.dicodingeventapp.data.response.EventResponse
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.data.retrofit.ApiConfig
import com.nudriin.dicodingeventapp.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val eventRepository: EventsRepository) : ViewModel() {

    fun getUpcomingEvent() = eventRepository.getUpcomingEvent()
    fun getFinishedEvent() = eventRepository.getFinishedEvent()

    companion object {
        const val TAG = "UpcomingViewModel"
    }

}