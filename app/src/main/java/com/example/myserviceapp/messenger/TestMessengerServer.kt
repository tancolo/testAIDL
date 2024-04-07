package com.example.myserviceapp.messenger

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

class TestMessengerServer : Service(){
    private companion object {
        const val TAG = "IPC"
    }

    private val handle = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {

            // 1. receive message from client
            msg.data.let {
                val id = it.getInt("id")
                Log.d(TAG, "receive id from client, id = $id")
            }

            // 2. send message to client
            val replyMessenger: Messenger = msg.replyTo
            replyMessenger?.let {
                val message = Message.obtain()
                Bundle().let { bundle ->
                    bundle.putString("name", "zhang san")
                    bundle.putInt("age", 18)
                    message.data = bundle
                }
                it.send(message)
            }

        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(handle).binder
    }
}