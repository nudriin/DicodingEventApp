package com.nudriin.dicodingeventapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nudriin.dicodingeventapp.data.EventsRepository
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity

class FavoriteViewModel(private val eventRepository: EventsRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteEvents(): LiveData<List<EventEntity>>{
        return eventRepository.getFavoriteEvent()
    }
}