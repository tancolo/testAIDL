package com.example.myserviceapp.testbinder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TestServer : Service() {
//    private lateinit var mTestServerBinder: TestServerBinder
    private val TAG = TestServer::class.java.simpleName

    override fun onBind(intent: Intent?): IBinder? {
        val testServerBinder = TestServerBinder()
        Log.d(TAG, "onBind()-> will return object of TestServerBinder: $testServerBinder")
        return testServerBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind()-> intent: $intent")
        return super.onUnbind(intent)
    }
}