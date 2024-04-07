package com.example.myserviceapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyService : Service() {
    private val TAG: String = MyService::class.java.simpleName
    private var count: Int = 0

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MyService onCreate()")

        //startThreadForCounting()
        //testANR()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d(TAG, "MyService onStart()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "MyService onStartCommand()")
//        Thread.sleep(3000)
//        stopSelf()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "MyService onBind() MyBinder")
        return MyBinder(this)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "MyService onUnBind() ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "MyService onDestroy() ")
        super.onDestroy()
    }

    fun getRandomNumber(): Int {
        //return Random(100).nextInt()
        return (1..1000).random()
    }

    private fun startThreadForCounting() {
        // 1. new Thread()
//        object : Thread() {
//            override fun run() {
//                while (true) {
//                    count++
//                    Log.d(TAG, "count is $count")
//                    try {
//                        sleep(2000)
//                    } catch (e: Throwable) {
//                        Log.d(TAG, e.message ?: "")
//                    }
//                }
//            }
//        }.start()

        // 2. thread()
        thread {
            while (true) {
                count++
                Log.d(TAG, "count is $count")
                try {
                    Thread.sleep(2000)
                } catch (e: Throwable) {
                    Log.d(TAG, e.message ?: "")
                }
            }
        }
    }

    fun getCount() = count

    fun testANR() {
        while (true) {
            Thread.sleep(2000)
        }
    }

}