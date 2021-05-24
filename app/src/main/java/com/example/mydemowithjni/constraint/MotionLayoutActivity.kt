package com.example.mydemowithjni.constraint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityMotionBinding

class MotionLayoutActivity: BaseActivity<ActivityMotionBinding>() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MotionLayoutActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initViewBinding(): ActivityMotionBinding = ActivityMotionBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {
    }

    override fun setListener() {
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}