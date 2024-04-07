package com.example.myserviceapp.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.myserviceapp.ITestServer.Stub

class TestAidlServer : Service(){
    private companion object {
        const val TAG = "IPC"
    }

    private val stub = object : Stub() {
        override fun say(word: String?) {
            Log.d(TAG, "<--- server receive say: $word")
        }

        override fun tell(word: String?, age: Int): Int {
            Log.d(TAG, "<--- server receive tell: <---- $word, age: $age")
            return age + 1
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind, stub is $stub")
        return stub
    }
}