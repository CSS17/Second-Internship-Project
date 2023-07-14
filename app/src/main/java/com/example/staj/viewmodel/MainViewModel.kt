package com.example.staj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staj.models.EventItem
import com.example.staj.repository.SportsBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SportsBookRepository):ViewModel(){

    private val _eventResult = MutableLiveData<List<EventItem>>()
    val eventResult: LiveData<List<EventItem>>
        get() = _eventResult

    init {

        getEventList()

    }


    private fun getEventList()=viewModelScope.launch {
        repository.getEventList().collect{event->

            _eventResult.value=event.data.eventList
        }

    }

}