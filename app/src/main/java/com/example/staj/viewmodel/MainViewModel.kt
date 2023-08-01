package com.example.staj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staj.repository.SportsBookRepository
import com.example.staj.scoresocketmodel.UpdatedScoreData
import com.example.staj.service.SocketHandler
import com.example.staj.socketmodel.M
import com.example.staj.socketmodel.UpdatedData
import com.example.staj.view.bigmodel.Mainmodel
import com.example.staj.view.model.ExclusiveRatio
import com.example.staj.view.model.FinalRatio
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject
import com.google.gson.JsonParser
import org.json.JSONObject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SportsBookRepository):ViewModel(){


    private val _matchInfoResult = MutableLiveData<List<Mainmodel>>()
    val gson = Gson()

    val matchInfo: LiveData<List<Mainmodel>>
        get() = _matchInfoResult



    var mSocket: Socket?=null
    var sSocket: Socket?=null


    init {
        getEventList()
        SocketHandler.initEventUpdateSocket()
        SocketHandler.initScoreUpdateSocket()
        mSocket = SocketHandler.getSocket()
        sSocket = SocketHandler.getScoreSocket()
    }


    fun getEventUpdatedData(callback: (String,UpdatedData) -> Unit) {
        Log.d("SOCKET", "DENEME İÇİNDE")
        mSocket?.on("event") { args ->
            if (args.isNotEmpty()) {
                val jsonStr = args[0] as String
                // JSON veriyi 'UpdatedData' nesnesine çözümle
                val data = gson.fromJson(jsonStr, UpdatedData::class.java)
                val dataList = data?.m
                dataList?.let { updatedData ->
                    callback(jsonStr,data)
                }
                //Log.d("SOCKET",jsonObject.toString())

            } else {
                Log.d("SOCKET", "ARGÜMAN GELMEDİ")
            }
        }

    }



    fun getScoreUpdatedData(callback: (String,UpdatedScoreData) -> Unit) {
        Log.d("SOCKET","DENEME İÇİNDE 2")
        sSocket?.on("score") { args ->
            if (args.isNotEmpty()) {
                val jsonStr = args[0] as String
                val data = gson.fromJson(jsonStr, UpdatedScoreData::class.java)
                data?.let {
                    callback(jsonStr,data)
                }
                //Log.d("SOCKET", "JSON verisi: $jsonStr")

            } else {
                Log.d("SOCKET", "ARGÜMAN GELMEDİ 2")
            }
        }
    }



    private fun getEventList() = viewModelScope.launch {
        repository.getEventList().collect { event ->

            //Log.d("TAG", event.data.eventList.toString())

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