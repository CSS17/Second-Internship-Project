package com.example.staj.socketmodel

import com.google.gson.annotations.SerializedName

data class SocketEvent(
    @SerializedName("i")
    val eventId: Int?,

    @SerializedName("v")
    val eventVersion: Int,

    @SerializedName("bp")
    val bettingPhase: Int?,

    @SerializedName("bs")
    val bettingStatus: Int?,

    @SerializedName("ts")
    val timestamp: Long?,

    @SerializedName("m")
    val markets: ArrayList<SocketMarket>
) {
    override fun toString(): String {
        return "EventID: $eventId EventVersion: $eventVersion"
    }
}
