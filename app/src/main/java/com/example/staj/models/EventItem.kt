package com.example.staj.models

import com.google.gson.annotations.SerializedName

data class EventItem(
    val bp: Int,
    val bri: Int,
    val cp: Int,
    val cri: Int,
    @SerializedName("d")
    val date: Long,
    val i: Int,
    val isSo: Boolean,
    val ist: Boolean,
    val l: Boolean,
    val m: List<M>,
    val mbc: Int,
    val mrg: Double,
    @SerializedName("n")
    val name: String,
    val pa: String,
    val ph: String,
    val s: Int,
    val soc: Int,
    val som: List<Som>,
    val sot: Int,
    val st: String,
    val t: Int,
    val tv: String,
    val v: Int
)