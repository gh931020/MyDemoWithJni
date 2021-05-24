package com.example.mydemowithjni.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityLocalServiceBinding
const val MSG_SAY_HELLO = 1
class MessengerServiceActivity: BaseActivity<ActivityLocalServiceBinding>() {

    private var mService: Messenger?= null

    private var bound: Boolean = false

    private val mConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            bound = false
        }

    }

    override fun initViewBinding(): ActivityLocalServiceBinding {
        return ActivityLocalServiceBinding.inflate(layoutInflater)
    }

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {
        viewBinding.serviceBth.setOnClickListener {
            if (!bound) return@setOnClickListener
            val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
            try {
                mService?.send(msg)
            }catch (e: RemoteException){
                e.printStackTrace()
            }
        }
    }

    override fun initData() {

    }

    override fun onStart() {
        super.onStart()
        // Bind to the service
        Intent(this, MessengerService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (bound){
            unbindService(mConnection)
            bound = false
        }
    }
}