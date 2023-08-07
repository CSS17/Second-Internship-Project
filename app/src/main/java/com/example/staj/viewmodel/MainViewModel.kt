package com.example.staj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.example.staj.difutil.RatioDifUtil
import com.example.staj.models.EventItem
import com.example.staj.models.EventResponse
import com.example.staj.models.M
import com.example.staj.repository.SportsBookRepository
import com.example.staj.service.SocketHandler
import com.example.staj.socketmodel.SocketEvent
import com.example.staj.view.bigmodel.Mainmodel
import com.example.staj.view.model.EventId
import com.example.staj.view.model.ExclusiveRatio
import com.example.staj.view.model.FinalRatio
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SportsBookRepository):ViewModel(){

    private var eventIdList: List<EventId> = emptyList()
    private var allEventList: List<EventItem> = emptyList()
    private val _matchInfoResult = MutableLiveData<List<Mainmodel>>()
    private var combinedList:List<Mainmodel>?=null
    private var newFinalRatioList:MutableList<Mainmodel>?=null
    val gson = Gson()

    val matchInfo: LiveData<List<Mainmodel>>
        get() = _matchInfoResult



    var mSocket: Socket?=null


    init {
        getEventList()
        SocketHandler.initEventUpdateSocket()
        mSocket = SocketHandler.getSocket()
    }


    fun getEventUpdatedData(callback: (String, SocketEvent) -> Unit) {
        Log.d("SOCKET", "DENEME İÇİNDE")
        mSocket?.on("event") { args ->
            if (args.isNotEmpty()) {
                val jsonStr = args[0] as String

                // JSON veriyi 'SocketEvent' nesnesine çözümle
                val data = gson.fromJson(jsonStr, SocketEvent::class.java)
                val allEventIds: List<String> = eventIdList.map { it.eventId }
                val ratiodIds: List<String> = eventIdList.map { it.exclusiveRatioId }
                Log.d("SOCKET","SOKETTEN GELEN VERİ ORJİNAL:"+args[0])
                Log.d("SOCKET","SOKETTEN GELEN VERİ:"+data)
                Log.d("SOCKET","LİSTELENEN MAÇLARIN ID'SI:"+allEventIds.toString())
                Log.d("SOCKET","ORANI GÜNCELLENEN MAÇIN ID'SI (SOKETTEN):"+data.eventId.toString())
                val marketIds=data.markets.map { it.marketId.toString() }
                val matchtags=allEventList.map { it.name }






                Log.d("SOCKET","ORANI GÜNCELLENEN MAÇIN MARKET IDLERI (mi): "+marketIds)

                if(allEventIds.contains(data.eventId.toString())){
                    Log.d("SOCKET","LİSTEDEKİ MAÇLARDAN BİRİNİN BİR ORANI UPDATE ALMIŞ")
                    val index=allEventIds.indexOf(data.eventId.toString())
                    Log.d("SOCKET","MAÇIN INDEXI:$index")

                    if(marketIds.any { element -> ratiodIds.contains(element) }){
                        val commonElements = marketIds.filter { element -> ratiodIds.contains(element) }.joinToString(",")
                        Log.d("SOCKET","${commonElements} MARKET ID'Lİ  ${matchtags[index]} MAÇININ MAÇ SONU ORANI DEĞİŞTİ")
                        Log.d("SOCKET","*")
                        val raitoHolder = data.markets.find { it.marketId.toString() == commonElements }
                        val ratio = raitoHolder?.outcomes

                        val oddValues = ratio?.map { it.odd } ?: emptyList()
                        newFinalRatioList?.get(index)?.finalRatio?.ratio1 = oddValues.get(0).toString()

                        //newFinalRatioList?.get(index)?.finalRatio?.ratiox=oddValues.get(1).toString()
                        Log.d("SOCKET", "GÜNCELLENEN DEĞER:" + oddValues)

                        Log.d("SOCKET", "İLK LİSTE:" + combinedList)
                        Log.d("SOCKET", "GÜNCELLENEN LİSTE:" + newFinalRatioList)
                        Log.d("SOCKET","ÖNCEKİ LİSTE İLE SONRAKİ LİSTE AYNI MI? ${combinedList == newFinalRatioList}")
                        Log.d("SOCKET","GÜNCELLENEN KIISMDAKİ DEĞER: ${newFinalRatioList?.get(index)?.finalRatio?.ratio1} ve İLK DEĞER: ${combinedList?.get(index)?.finalRatio?.ratio1}")





                        combinedList = newFinalRatioList?.map { mainModel ->
                            Mainmodel(
                                mainModel.matchTag,
                                FinalRatio(mainModel.finalRatio.ratio1, mainModel.finalRatio.ratiox, mainModel.finalRatio.ratio2),
                                ExclusiveRatio(mainModel.exclusiveRatio.upper, mainModel.exclusiveRatio.lower),
                                EventId(mainModel.eventId.eventId, mainModel.eventId.exclusiveRatioId, mainModel.eventId.finalRatioId)
                            )
                        }?.toList()


                        val areListsEqual = combinedList == newFinalRatioList
                        Log.d("SOCKET", "Gerçek Karşılaştırma Sonucu: $areListsEqual")


                    }
                    Log.d("SOCKET","--------------------")

                }
                else{
                    Log.d("SOCKET","--------------------")
                }

                data?.let { updatedData ->

                    callback(jsonStr,data)
                }
                //Log.d("SOCKET",jsonObject.toString())

            } else {
                Log.d("SOCKET", "ARGÜMAN GELMEDİ")
            }
        }

    }




    private fun getEventList() = viewModelScope.launch {
        repository.getEventList().collect { event ->
            allEventList=event.data.eventList
            Log.d("TAG", event.data.eventList.toString())

            eventIdList= event.data.eventList.map { id->
                val exclusiveRatio = id.m?.find { it?.t == 1 && it?.st == 1 }
                val finalRatio = id.m?.find { it?.t == 2 && it?.st == 101 && it?.ov == "2.5" }
                val miexclusiveRatio=exclusiveRatio?.id
                val mifinalRatio=finalRatio?.id

                EventId(id.eventId.toString(),miexclusiveRatio.toString(),mifinalRatio.toString())
            }

            val ratioList = event.data.eventList.map { event ->
                val market = event.m?.find { it?.t == 1 && it?.st == 1 }
                val ratioAdapterList=market?.o?.map { it.od.toString() } //EN SON BURADA KALDIM
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

            combinedList = event.data.eventList.mapIndexed { index, eventItem ->
                Mainmodel(
                    eventItem, // EventItem
                    ratioList[index], // FinalRatio
                    upperLowList[index] ,// ExclusiveRatio
                    eventIdList[index]
                )
            }
            val firstFinalRatio = combinedList?.map { it.finalRatio }
            val firstFinalRatioList=firstFinalRatio?.map { finalRatio ->
                listOf(finalRatio.ratio1, finalRatio.ratiox, finalRatio.ratio2)
            }

            Log.d("SOCKET", "FİNALRATİOLİST: $firstFinalRatioList")

            _matchInfoResult.value = combinedList
        }


        newFinalRatioList = combinedList?.map { mainModel ->
            Mainmodel(
                mainModel.matchTag,
                FinalRatio(mainModel.finalRatio.ratio1, mainModel.finalRatio.ratiox, mainModel.finalRatio.ratio2),
                ExclusiveRatio(mainModel.exclusiveRatio.upper, mainModel.exclusiveRatio.lower),
                EventId(mainModel.eventId.eventId, mainModel.eventId.exclusiveRatioId, mainModel.eventId.finalRatioId)
            )
        }?.toMutableList()
    }









}