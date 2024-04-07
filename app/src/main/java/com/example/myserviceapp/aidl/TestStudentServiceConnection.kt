package com.example.myserviceapp.aidl

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.myserviceapp.ITestServer
import com.example.myserviceapp.ITestStudentServer

class TestStudentServiceConnection : ServiceConnection {
    private val TAG = "IPC"
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val iTestStudentServer = ITestStudentServer.Stub.asInterface(service)
        try {
            //iTestStudentServer.getStudentInfo(18, Student("zhang san", 18))

            // test data flow "in"
            val student = Student("xiaoming", 18)
            Log.d(TAG, "student name: ${student.getName()} in client before getStudentInfoIn")
            iTestStudentServer.getStudentInfo(2, student)
            Log.d(TAG, "student name: ${student.getName()} in client after getStudentInfoIn")

            // test data flow "out"
            val student2 = Student("xiaoming", 18)
            Log.d(TAG, "student2 name: ${student2.getName()} in client before getStudentInfoOut")
            iTestStudentServer.getStudentInfo2(2, student2)
            Log.d(TAG, "student2 name: ${student2.getName()} in client after getStudentInfoOut")


            // test data flow "inout"
            val student3 = Student("xiaoming", 18)
            Log.d(TAG, "student3 name: ${student3.getName()} in client before getStudentInfoInOut")
            iTestStudentServer.getStudentInfo3(2, student3)
            Log.d(TAG, "student3 name: ${student3.getName()} in client after getStudentInfoInOut")


        } catch (e: Throwable) {
            Log.d(TAG, "throwable is : ${e.printStackTrace()}")
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }
}