package com.example.staj.socketmodel

import com.google.gson.annotations.SerializedName

data class SocketMarket(
    @SerializedName("mi")
    val marketId: Int,

    @SerializedName("mv")
    val marketVersion: Int,

    @SerializedName("ms")
    val marketStatus: Int,

    @SerializedName("mcc")
    val mbc: Int,

    @SerializedName("sub")
    val subType: Int,

    @SerializedName("t")
    val type: Int,

    @SerializedName("sov")
    val sov: Float,

    @SerializedName("ov1")
    val oddValueOne: String,

    @SerializedName("ov2")
    val oddValueTwo: String,

    @SerializedName("ov3")
    val oddValueThree: String,

    @SerializedName("out")
    val outcomes: ArrayList<SocketOutcome>
)
