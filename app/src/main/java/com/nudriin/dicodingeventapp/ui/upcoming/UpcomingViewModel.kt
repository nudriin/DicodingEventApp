package com.nudriin.dicodingeventapp.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nudriin.dicodingeventapp.data.response.EventResponse
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.data.retrofit.ApiConfig
import com.nudriin.dicodingeventapp.ui.finished.FinishedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<ListEventsItem>>()
    val eventList: LiveData<List<ListEventsItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingViewModel"
    }

    init {
        getUpcomingEvent()
    }

    private fun getUpcomingEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents("1")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()

                    _eventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun searchUpcomingEvent(searchQ: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchEvents("1", searchQ)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()

                    _eventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}