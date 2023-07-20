package com.example.staj.service


import com.example.staj.models.EventResponse
import com.example.staj.utils.Constants.END_POINT
import retrofit2.http.GET

interface SportsBookService {
    @GET(END_POINT)
    suspend fun getEvents():EventResponse
}