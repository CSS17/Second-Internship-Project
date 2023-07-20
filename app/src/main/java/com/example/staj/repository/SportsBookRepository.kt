package com.example.staj.repository

import com.example.staj.models.EventResponse
import kotlinx.coroutines.flow.Flow

interface SportsBookRepository{

    suspend fun getEventList():Flow<EventResponse>


}