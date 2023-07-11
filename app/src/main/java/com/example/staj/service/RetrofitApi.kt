package com.example.staj.service

import com.example.staj.models.Event
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {
    @GET("api/mobile/v2/sportsbook/events/SOCCER/1/0")
    fun getEvents(): Call<Event>
}



