package com.nudriin.dicodingeventapp.ui.home

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

class HomeViewModel : ViewModel() {

    private val _upcomingEventList = MutableLiveData<List<ListEventsItem>>()
    val upcomingEventList: LiveData<List<ListEventsItem>> = _upcomingEventList

    private val _finishedEventList = MutableLiveData<List<ListEventsItem>>()
    val finishedEventList: LiveData<List<ListEventsItem>> = _finishedEventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingViewModel"
    }

    init {
        getUpcomingEvent()
        getFinishedEvent()
    }

    private fun getUpcomingEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents("1")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()

                    _upcomingEventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getFinishedEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents("0")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()

                    _finishedEventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}