package com.example.myserviceapp.testbinder

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Parcel
import android.util.Log
import com.example.myserviceapp.utility.getProcessName


class TestServiceConnection : ServiceConnection {
    private val TAG = TestServiceConnection::class.java.simpleName
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "service is : $service")
        //service 为 TestServer传递过来的IBinder引用
        //构造序列化对象
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        //写入String
        val word = "hello testServer, how are you ?"
        data.writeString(word)
        try {
            Log.d(TAG, "client sent ---> message to TestServer with transact()")
            //传递消息给TestServer
            service!!.transact(2, data, reply, 0)

            //收到Server的回复消息
            val responseWord = reply.readString()
            Log.d(TAG, "client receive <---: $responseWord in process: ${getProcessName()}")
        } catch (e: Exception) {
            println(e.printStackTrace())
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "onServiceDisconnected(), name: $name")
    }
}