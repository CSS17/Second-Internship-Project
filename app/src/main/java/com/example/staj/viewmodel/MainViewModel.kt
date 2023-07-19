package com.example.staj.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.models.EventItem
import com.example.staj.models.M
import com.example.staj.repository.SportsBookRepository
import com.example.staj.view.EventAdapter
import com.example.staj.view.model.ViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SportsBookRepository):ViewModel(){
    private var list: List<ViewItem> = listOf()
    private val _eventResult = MutableLiveData<List<EventItem>>()
    private val _raitoResult = MutableLiveData<List<ViewItem>>()

    val eventResult: LiveData<List<EventItem>>
        get() = _eventResult
    val ratioResult:LiveData<List<ViewItem>>
        get()=_raitoResult




    init {

        getEventList()

    }


    private fun getEventList()=viewModelScope.launch {
        repository.getEventList().collect { event ->
            if (event.data != null && event.data.eventList != null) {
                Log.d("TAG", event.data.eventList.toString())

                list = event.data.eventList.map { event ->
                    val market = event.m?.find { it?.t == 1 && it?.st == 1 }
                    val ratio1 = market?.o?.getOrNull(0)?.od?: "kilitli"
                    val ratiox = market?.o?.getOrNull(1)?.od?: "kilitli"
                    val ratio2 = market?.o?.getOrNull(2)?.od?: "kilitli"
                    ViewItem(ratio1.toString(), ratiox.toString(), ratio2.toString())
                }

                _eventResult.value = event.data.eventList
                _raitoResult.value = list
            }

        }


    }

    fun checkAdapterSet(adapterSetFlag:Boolean, recyclerView: RecyclerView, eventList:MutableList<EventItem>, ratioList:MutableList<ViewItem>) {
        if (adapterSetFlag) {
            Log.d("TAG","2.Kısım")
            recyclerView.adapter = EventAdapter(eventList,ratioList)
            // İşlemlerinizi burada gerçekleştirin
        }
    }






}