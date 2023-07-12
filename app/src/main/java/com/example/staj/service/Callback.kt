package com.example.staj.service

interface Callback {
    fun onSuccess(matches: MutableList<String>,time: MutableList<String>)
    fun onFailure(message: String)
}