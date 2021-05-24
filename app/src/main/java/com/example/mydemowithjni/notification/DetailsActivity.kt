package com.example.mydemowithjni.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityDetailsBinding

class DetailsActivity: BaseActivity<ActivityDetailsBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DetailsActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initViewBinding(): ActivityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)

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