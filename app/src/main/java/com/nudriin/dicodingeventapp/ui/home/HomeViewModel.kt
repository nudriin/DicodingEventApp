package com.nudriin.dicodingeventapp.ui.home

import androidx.lifecycle.ViewModel
import com.nudriin.dicodingeventapp.data.EventsRepository

class HomeViewModel(private val eventRepository: EventsRepository) : ViewModel() {

    fun getUpcomingEvent() = eventRepository.getUpcomingEvent()
    fun getFinishedEvent() = eventRepository.getFinishedEvent()

    companion object {
        const val TAG = "UpcomingViewModel"
    }

}