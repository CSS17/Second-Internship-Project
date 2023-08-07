package com.example.staj.service

import android.util.Log
import com.example.staj.network.NetworkConstants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket
import java.net.URISyntaxException


object SocketHandler {
    private var eventUpdateSocket: Socket? = null
    private var scoreUpdateSocket: Socket? = null


    fun initEventUpdateSocket() {
        if (eventUpdateSocket == null) {
            try {
                val opts = IO.Options()
                opts.forceNew = true
                opts.reconnection = true
                opts.timeout = -1
                opts.multiplex = false
                opts.transports = arrayOf(WebSocket.NAME)
                eventUpdateSocket = IO.socket(NetworkConstants.WebSocket.EVENT_UPDATE, opts)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            eventUpdateSocket?.let{
                Log.d("SOCKET","ALTTAKİ İF İÇERİSİNDE")
                it.on(Socket.EVENT_CONNECT,socketConnected)
                it.on(Socket.EVENT_CONNECT_ERROR, connectionError)
                it.connect()
            }

        }
    }












    private val onScoreConnect=Emitter.Listener {
        Log.d("SOCKET","Score Update Socket connected")
    }

    private val onScoreConnectionError=Emitter.Listener {
        Log.e("SOCKET","Score Update Socket disconnected")
    }

    private val socketConnected = Emitter.Listener {
        // Socket disconnected, handle any necessary cleanup or reconnection logic here
        Log.d("SOCKET","Socket connected")
    }
    private val connectionError = Emitter.Listener {
        // Socket disconnected, handle any necessary cleanup or reconnection logic here
        Log.e("SOCKET",it.toString())
    }


    fun getSocket(): Socket? {
        return eventUpdateSocket
    }

    fun getScoreSocket(): Socket? {
        return scoreUpdateSocket
    }

    fun establishConnection() {
        eventUpdateSocket?.connect()
    }

    fun closeConnection() {
        eventUpdateSocket?.disconnect()
    }
}
