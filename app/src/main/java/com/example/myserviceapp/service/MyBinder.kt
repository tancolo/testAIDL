package com.example.myserviceapp.service

import android.app.Service
import android.os.Binder

class MyBinder(private val service: Service): Binder() {

    fun getService(): Service {
        return service
    }
}