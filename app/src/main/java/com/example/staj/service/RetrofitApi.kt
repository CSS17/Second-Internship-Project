package com.example.staj.service

import com.example.staj.models.Event
import com.example.staj.utils.Constants.END_POINT
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {
    @GET(END_POINT)
    fun getEvents(): Call<Event>
}



