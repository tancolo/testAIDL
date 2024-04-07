package com.example.myserviceapp.testbinder

import android.os.Binder
import android.os.Parcel
import android.util.Log
import com.example.myserviceapp.utility.getProcessName

class TestServerBinder : Binder() {
    companion object {
        const val TAG: String = "IPC"
    }

    object iTestServer : ITestServer {
        override fun say(word: String) {
            Log.d(TAG, "server receive <--- $word in process: ${getProcessName()}")
        }
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {

        val word: String = data.readString().orEmpty()
        iTestServer.say(word)

        val replyWord = "I am fine, and you?"
        Log.d(TAG, "server send ---> $replyWord in process: ${getProcessName()}")
        reply?.writeString(replyWord)

        return true
    }
}