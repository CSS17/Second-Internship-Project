package com.example.staj.service


import com.example.staj.models.Event
import com.example.staj.utils.Constants.END_POINT
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface SportsBookService {
    @GET(END_POINT)
    suspend fun getEvents():Event
}