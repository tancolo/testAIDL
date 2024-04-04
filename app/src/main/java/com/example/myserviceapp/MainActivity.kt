package com.example.myserviceapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myserviceapp.service.MyBinder
import com.example.myserviceapp.service.MyService


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName
    private lateinit var mService: MyService
    private lateinit var mTextView: TextView
    private lateinit var mServiceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mTextView = findViewById(R.id.tx_random_number)
        val intent = Intent(this, MyService::class.java)

        val buttonStart: Button = findViewById(R.id.button_start_service)
        buttonStart.setOnClickListener {
            Log.d(TAG, "click start button")
            // 1.
            startService(intent)

            // 2. bind service
            //bindService()
        }

        val buttonStop: Button = findViewById(R.id.button_stop_service)
        buttonStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d(TAG, "click stop button")
                // 1. stop server
                stopService(intent)

                // 2. unbind service
                //unBindService()
            }
        })
    }

    private fun bindService() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Log.d(TAG, "onServiceConnected, name: ${name.toString()}")
                //service即是从onBind(xx)方法返回的(绑定者和Service同一进程)
                val myBinder = service as MyBinder
                //获取Service的引用
                mService = myBinder.getService() as MyService

                //mTextView.setText(mService.getRandomNumber().toString())
                mTextView.text = mService.getCount().toString()
            }

            override fun onServiceDisconnected(name: ComponentName) {
                //Service被销毁时调用(内存不足等，正常解绑不会走这)
                Log.d(TAG, "onServiceDisconnected, name: ${name.toString()}")
            }

            override fun onBindingDied(name: ComponentName?) {
                super.onBindingDied(name)
                Log.d(TAG, "onBindingDied, name: ${name?.toString()}")
            }

            override fun onNullBinding(name: ComponentName?) {
                super.onNullBinding(name)
                Log.d(TAG, "onNullBinding, name: ${name?.toString()}")
            }
        }
        val intent = Intent(this, MyService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        Log.d(TAG, "unBindService()...")
        unbindService(mServiceConnection)
    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity -> onDestroy()")
//        unbindService(mServiceConnection)

        super.onDestroy()
    }
}