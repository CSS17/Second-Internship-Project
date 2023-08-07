package com.example.staj.socketmodel

import com.google.gson.annotations.SerializedName

data class SocketOutcome(
    @SerializedName("on")
    val no: Int?,

    @SerializedName("od")
    val odd: Float,

    @SerializedName("ro")
    val retailerOdd: Float
)
