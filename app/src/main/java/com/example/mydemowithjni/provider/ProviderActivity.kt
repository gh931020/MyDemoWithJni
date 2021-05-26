package com.example.mydemowithjni.provider

import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityProviderBinding

class ProviderActivity : BaseActivity<ActivityProviderBinding>(){
    override fun initViewBinding(): ActivityProviderBinding = ActivityProviderBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {

    }

    override fun initData() {

    }
}