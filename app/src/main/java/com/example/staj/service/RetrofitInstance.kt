package com.example.staj.service

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.staj.models.Event
import com.example.staj.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

class RetrofitInstance {
    private var matches: MutableList<String> = mutableListOf()
    private var dates: MutableList<String> = mutableListOf()
    private var homeratios: MutableList<String> = mutableListOf()
    private lateinit var retrofit: Retrofit
    private lateinit var eventService:RetrofitApi
    private lateinit var call:Call<Event>
    private lateinit var match:String
    private lateinit var home:String

    private var date:Long = 0


    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun connectRetrofit(callback: Callback) {
         retrofit = createRetrofit()
         eventService = retrofit.create(RetrofitApi::class.java)
         call = eventService.getEvents()
        call.enqueue(object : retrofit2.Callback<Event> {
            override fun onResponse(call: Call<Event>, response: retrofit2.Response<Event>) {
                if (response.isSuccessful) {
                    val size = response.body()?.data?.e?.size?.minus(1)
                    Log.d("TAG",response.body()?.data.toString())
                    for (i in 0..size!!) {
                        match = response.body()?.data?.e?.get(i)?.n.toString()
                        date = response.body()?.data?.e?.get(i)?.d!!
                        //home=response.body()?.data?.e?.get(i)?.m?.get(2)?.o?.get(0)?.od.toString()
                        //homeratios.add(home)
                        //Log.d("HOME"+i,home)
                        matches.add(match)
                        dates.add(Date(date).toString())
                    }

                    callback.onSuccess(matches,dates)
                } else {
                    callback.onFailure("API yanıtında hata var.")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                callback.onFailure("Başarısız Bağlantı ${call} ${t.message}")
            }
        })


    }
}