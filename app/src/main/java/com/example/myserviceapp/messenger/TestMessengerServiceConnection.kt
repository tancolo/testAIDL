package com.example.myserviceapp.messenger

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

class TestMessengerServiceConnection : ServiceConnection {
    private val TAG = "IPC"

    // 2. receive message from server
    private val handle = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            msg.data?.let {
                val name = it.getString("name")
                val age = it.getInt("age")
                Log.d(TAG, "receive name: $name, age: $age from server")
            }
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // 1. send message to server
        try {
            val message = Message.obtain()
            Messenger(service).let {
                Bundle().let {
                    it.putInt("id", 1000)
                    message.data = it

                    // 2. set value of replyto
                    message.replyTo = Messenger(handle)
                }.apply {
                    it.send(message)
                }
            }

        } catch (e: Exception) {
            println(e.printStackTrace())
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "onServiceDisconnected()")
    }
}