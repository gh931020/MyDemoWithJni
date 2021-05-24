package com.example.mydemowithjni.viewpager2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityViewpage2Binding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPage2Activity: BaseActivity<ActivityViewpage2Binding>() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ViewPage2Activity::class.java)
            context.startActivity(starter)
        }
    }

    lateinit var demoCollectionAdapter: DemoCollectionAdapter

    override fun initViewBinding(): ActivityViewpage2Binding {
        return ActivityViewpage2Binding.inflate(layoutInflater)
    }

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {

    }

    override fun initData() {
        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewBinding.viewPager2.adapter = demoCollectionAdapter
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager2){ tab, position->
            tab.text = "OBJECT ${(position +1)}"
        }.attach()
    }
}