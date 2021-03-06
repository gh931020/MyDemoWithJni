package com.example.mydemowithjni.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class LocalService: Service() {

    private val binder = LocalBinder()

    private val mGenerator = Random()

    /**
     * method fo clients
     */
    val randomNumber: Int
        get() = mGenerator.nextInt(100)

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder: Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LocalService = this@LocalService
    }


    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}