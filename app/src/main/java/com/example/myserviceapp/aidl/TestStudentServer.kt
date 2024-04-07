package com.example.myserviceapp.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.myserviceapp.ITestStudentServer

class TestStudentServer : Service() {
    private companion object {
        const val TAG = "IPC"
    }

    private val stub = object : ITestStudentServer.Stub() {
        override fun getStudentInfo(age: Int, student: Student?) {
            //Log.d(TAG, "${student!!.getName()} in server")

            Log.d(TAG, "student name:  ${student?.getName()} in server in getStudentInfo--In")
            student?.setName("change name getStudentInfoIn");
        }

        override fun getStudentInfo2(age: Int, student: Student?) {
            Log.d("TAG", "student: $student ")
            Log.d(TAG, "student name:  ${student?.getName()} in server in getStudentInfo--out")
            student?.setName("change name getStudentInfoOut");
        }

        override fun getStudentInfo3(age: Int, student: Student?) {
            Log.d(TAG, "student name:  ${student?.getName()} in server in getStudentInfo--InOut")
            student?.setName("change name getStudentInfoInOut");
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind(), will return Stub object")
        return stub
    }
}