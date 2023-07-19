package com.example.staj.repository

import com.example.staj.models.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SportsBookRepository{

    suspend fun getEventList():Flow<Event>


}