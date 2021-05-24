package com.example.mydemowithjni.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityLocalServiceBinding

/**
 * bind是异步操作,并且bindService()可立即返回,无需将IBinder返回给客户端. 如要接收IBinder, 客户端必须创建
 * 一个ServiceConnection实例,并将其传递给bindService().
 * 只有Activity,服务,和内容提供程序可以绑定到服务,无法从广播接收器绑定到服务
 * 如要断开连接, 调用unbindService()
 *      当应用销毁客户端时,如果客户端仍与服务保持绑定状态,销毁会导致客户端取消绑定.更好的做法是在客户端与服务端交互
 *      完成后,就立即取消客户端的绑定.这样做可以关闭空闲服务.
 *
 * 当服务与所有客户端之间的绑定全部取消时,Android系统会销毁该服务,除非还是用startService()调用启动了该服务.
 *
 * 如果选择实现onStartCommand()回调方法, 就必须显式停止服务,因为系统现在将服务视为已启动状态.
 * 此情况下,服务将一直运行,知道其通过stopSelf()自行停止,或其他组件调用stopService()(与该服务是否绑定到任何客户端无关)
 *
 *
 * @property mService LocalService
 * @property mBound Boolean
 * @property connection <no name provided>
 */
class LocalServiceActivity: BaseActivity<ActivityLocalServiceBinding>() {

    private lateinit var mService:LocalService

    private var mBound = false

    private val connection = object : ServiceConnection{
        /**
         * 调用此方法以传递服务的Onbind()方法返回的IBinder
         * @param name ComponentName
         * @param service IBinder
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocalService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        /**
         * 当与服务的链接意外中断时,例如服务崩溃或者被终止时,会调用该方法
         * 当客户端取消绑定时,不会调用此方法
         * @param name ComponentName
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }

    }

    override fun initViewBinding(): ActivityLocalServiceBinding = ActivityLocalServiceBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {
    }

    override fun setListener() {
        viewBinding.serviceBth.setOnClickListener{
            if (mBound){
                val num = mService.randomNumber
            }
        }
    }

    override fun initData() {
    }

    override fun onStart() {
        super.onStart()
        // bind to service
        // 第一个参数是一个 Intent，用于显式命名要绑定的服务。
        // 第二个参数是 ServiceConnection 对象。
        // 第三个参数是指示绑定选项的标记。如要创建尚未处于活动状态的服务，此参数通常应为 BIND_AUTO_CREATE。
        // 其他可能的值为 BIND_DEBUG_UNBIND 和 BIND_NOT_FOREGROUND，或者 0（表示无此参数）。
        Intent(this, LocalService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

}