package com.example.myserviceapp.aidl

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.myserviceapp.ITestServer

class TestAidlServiceConnection : ServiceConnection {
    private val TAG = "IPC"
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val iTestServer = ITestServer.Stub.asInterface(service)
        try {
            iTestServer.say("how are you?")
            val result = iTestServer.tell("how are you?", 18)
            Log.d("IPC", "<--- client receive content: $result")

        } catch (e: Throwable) {
            Log.d(TAG, "throwable is : ${e.printStackTrace()}")
        }

    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }
}