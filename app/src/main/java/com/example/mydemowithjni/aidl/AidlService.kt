package com.example.mydemowithjni.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AidlService: Service() {

    private val binder = object : IRemoteService.Stub(){
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}