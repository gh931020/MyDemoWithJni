package com.example.mydemowithjni.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast

/**
 * 可用于服务与远程进程通信.使用Messenger可不需要AIDL(进程间通信)
 * Messenger经所有的服务调用加入队列,一次处理一个调用
 * AIDL可执行多线程处理
 *
 * 1. 服务实现一个Handler, 由其接收来自客户端的每个调用回调
 * 2. 服务使用Handler来创建Messenger对象,该对象是对Handler的引用
 * 3. Messenger创建一个IBinder, 服务通过onBind()将其返回给客户端
 * 4. 客户端使用IBinder将Messenger实例化,然后再用其将Message对象发送给服务
 * 5. 服务在其Hander中接收每个Message
 *
 */
class MessengerService: Service() {
    companion object{
        val MSG_SAY_HELLO = 1
    }
    private lateinit var mMessenger: Messenger

    /**
     * Handler of incoming messages from clients.
     */
    internal class IncomingHandler(context: Context, private val applicationContext: Context = context.applicationContext): Handler(){
        override fun handleMessage(msg: Message) {

            when (msg.what) {
                MSG_SAY_HELLO -> {
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(applicationContext, "binding", Toast.LENGTH_SHORT).show()
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }
}