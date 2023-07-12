package com.example.staj.models

import com.google.gson.annotations.SerializedName

data class At(
    @SerializedName("c")
    val c: Int,
    val ht: Int,
    val r: Int,
    val rc: Int
)