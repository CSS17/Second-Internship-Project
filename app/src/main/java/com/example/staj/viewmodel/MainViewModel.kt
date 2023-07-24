package com.example.staj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staj.repository.SportsBookRepository
import com.example.staj.view.bigmodel.Mainmodel
import com.example.staj.view.model.ExclusiveRatio
import com.example.staj.view.model.FinalRatio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SportsBookRepository):ViewModel(){


    private val _matchInfoResult = MutableLiveData<List<Mainmodel>>()

    val matchInfo: LiveData<List<Mainmodel>>
        get() = _matchInfoResult


    init {

        getEventList()

    }


    private fun getEventList() = viewModelScope.launch {
        repository.getEventList().collect { event ->

            Log.d("TAG", event.data.eventList.toString())

            val ratioList = event.data.eventList.map { event ->
                val market = event.m?.find { it?.t == 1 && it?.st == 1 }
                val ratio1 = market?.o?.getOrNull(0)?.od ?: "kilitli"
                val ratiox = market?.o?.getOrNull(1)?.od ?: "kilitli"
                val ratio2 = market?.o?.getOrNull(2)?.od ?: "kilitli"
                FinalRatio(ratio1.toString(), ratiox.toString(), ratio2.toString())
            }
            val upperLowList = event.data.eventList.map { event ->
                val market = event.m?.find { it?.t == 2 && it?.st == 101 && it?.ov == "2.5" }
                val upper = market?.o?.getOrNull(0)?.od ?: "kilitli"
                val lower = market?.o?.getOrNull(1)?.od ?: "kilitli"
                ExclusiveRatio(upper.toString(), lower.toString())
            }

            val combinedList = event.data.eventList.mapIndexed { index, eventItem ->
                Mainmodel(
                    eventItem, // EventItem
                    ratioList[index], // FinalRatio
                    upperLowList[index] // ExclusiveRatio
                )
            }

            _matchInfoResult.value = combinedList
        }
    }








}