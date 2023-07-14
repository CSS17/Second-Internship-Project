package com.example.staj.repository

import com.example.staj.models.Event
import com.example.staj.service.SportsBookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SportsBookRepositoryImpl(private  val service: SportsBookService): SportsBookRepository {
    override suspend fun getEventList(): Flow<Event> = flow {
        emit(service.getEvents())
    }


}