package com.example.myserviceapp.aidlcallback

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class StudentService : Service() {
    private companion object {
        const val TAG = "StudentService"
    }
    private lateinit var mStudent: Student
    private var mCallback: RemoteCallback? = null

    private val studentStub = object : IStudentInfo.Stub() {
        override fun getStudentInfo(): Student {
            Log.d(TAG, "getStudentInfo()")
            return mStudent
        }

        override fun registor(callback: RemoteCallback?) {
            mCallback = callback
            Log.d(TAG, "register callback successfully")
        }
    }

    override fun onCreate() {
        super.onCreate()

        //create a new Student
        mStudent = Student("Xiao ming", 18, 80.0F)

        // create a thread and sleep 8 seconds and then callback to the client.
        thread {
            Log.d(TAG, "Create a thread")
            Thread.sleep(8000)
            changeScore()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind Student Service")
        return studentStub.asBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind StudentService")
        return super.onUnbind(intent)
    }

    private fun changeScore() {
        val randomScore = (1..100).random().toFloat()
        mStudent.setScore(randomScore)

        mCallback?.let {
            it.onCallback(mStudent)
        }
    }
}