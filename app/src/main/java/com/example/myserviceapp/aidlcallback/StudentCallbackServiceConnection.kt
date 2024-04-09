package com.example.myserviceapp.aidlcallback

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class StudentCallbackServiceConnection(
    private val callback: RemoteCallback.Stub
) : ServiceConnection {
    private val TAG = "StudentCallbackServiceConnection"

    private var mIsConnected: Boolean = false

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "Client bind the StudentService!!!")
        mIsConnected = true

        val studentService = IStudentInfo.Stub.asInterface(service)
        studentService.registor(callback)

        val studentInfo = studentService.studentInfo
        Log.d(TAG, "student info: $studentInfo")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mIsConnected = false
    }
}