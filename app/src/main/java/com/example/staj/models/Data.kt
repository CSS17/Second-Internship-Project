package com.example.staj.models

import com.google.gson.annotations.SerializedName

data class Data(
    val diff: Boolean,
    @SerializedName("e")
    val eventList: List<EventItem>,
    val ewc: List<Int>,
    val lse: List<Int>,
    val sc: Sc,
    val v: Long
)