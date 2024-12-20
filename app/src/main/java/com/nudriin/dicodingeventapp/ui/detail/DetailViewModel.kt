package com.nudriin.dicodingeventapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.nudriin.dicodingeventapp.data.EventsRepository
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity
import com.nudriin.dicodingeventapp.data.response.Event
import com.nudriin.dicodingeventapp.data.response.EventDetailResponse
import com.nudriin.dicodingeventapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val eventRepository: EventsRepository) : ViewModel() {
    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<com.nudriin.dicodingeventapp.util.Event<String>>()
    val toastText: LiveData<com.nudriin.dicodingeventapp.util.Event<String>> = _toastText

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getEventDetailById(id: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailById(id)
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()
                    _eventDetail.value = body?.event
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = com.nudriin.dicodingeventapp.util.Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })


    }

    fun setFavoriteEvent() {
        viewModelScope.launch {
            eventDetail.asFlow().collect{event ->
                val eventEntity = EventEntity(
                    event?.id!!,
                    event.summary,
                    event.mediaCover,
                    event.registrants,
                    event.imageLogo,
                    event.link,
                    event.description,
                    event.ownerName,
                    event.cityName,
                    event.quota,
                    event.name,
                    event.beginTime,
                    event.endTime,
                    event.category,
                    true
                )
                eventRepository.setFavoriteEvent(eventEntity)
            }
        }
    }

    fun getEventById(id: Int): LiveData<EventEntity?> {
        return eventRepository.getFavoriteEventById(id)
    }

    fun deleteEventById(id: Int) {
        eventRepository.deleteFavoriteEventById(id)
    }


}