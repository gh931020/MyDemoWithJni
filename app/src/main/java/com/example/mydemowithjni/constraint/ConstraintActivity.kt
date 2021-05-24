package com.example.mydemowithjni.constraint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityConstraintBinding

class ConstraintActivity: BaseActivity<ActivityConstraintBinding>() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ConstraintActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initViewBinding(): ActivityConstraintBinding = ActivityConstraintBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {
        viewBinding.motionLayoutbtn.setOnClickListener {
            MotionLayoutActivity.start(this@ConstraintActivity)
        }
    }

    override fun initData() {

    }
}