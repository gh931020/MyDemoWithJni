package com.example.mydemowithjni.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.ActivityStartBinding

class StartActivity: BaseActivity<ActivityStartBinding>() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, StartActivity::class.java)
            // 跨 Activity 发送复合对象或复杂对象,自定义类应实现 Parcelable
            // 通过 intent 发送数据时，应小心地将数据大小限制为几 KB。发送过多数据会导致系统抛出 TransactionTooLargeException 异常。
            // Binder 事务缓冲区的大小固定有限，目前为 1MB，由进程中正在处理的所有事务共享。
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        viewBinding.startBackBtn.setOnClickListener {
            this.finish()
        }
    }

    override fun initViewBinding(): ActivityStartBinding {
        return ActivityStartBinding.inflate(layoutInflater)
    }

    override fun restroeState(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setListener() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}