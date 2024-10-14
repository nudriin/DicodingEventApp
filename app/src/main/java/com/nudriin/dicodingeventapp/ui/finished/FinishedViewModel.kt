package com.nudriin.dicodingeventapp.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nudriin.dicodingeventapp.data.response.EventResponse
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.data.retrofit.ApiConfig
import com.nudriin.dicodingeventapp.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<ListEventsItem>>()
    val eventList: LiveData<List<ListEventsItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    companion object {
        private const val TAG = "FinishedViewModel"
    }

    init {
        getFinishedEvent()
    }

    private fun getFinishedEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllEvents("0")
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()
                    if(body?.listEvents?.size == 0){
                        _toastText.value = Event("Data tidak ditemukan")
                    }
                    _eventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun searchFinishedEvent(searchQ: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchEvents("-1", searchQ)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    val body = response.body()
                    if(body?.listEvents?.size == 0){
                        _toastText.value = Event("Data tidak ditemukan")
                    }
                    _eventList.value = body?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
                _toastText.value = Event(t.message.toString())
            }

        })
    }
}