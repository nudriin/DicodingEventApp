package com.nudriin.dicodingeventapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nudriin.dicodingeventapp.data.EventsRepository
import com.nudriin.dicodingeventapp.ui.detail.DetailViewModel
import com.nudriin.dicodingeventapp.ui.settings.SettingViewModel

class ViewModelFactory(
    private val preferences: SettingPreferences? = null,
    private val eventsRepository: EventsRepository? = null
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences!!) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(eventsRepository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(preferences = null, eventsRepository = Injector.provideRepository(context))
            }.also { instance = it }
    }
}