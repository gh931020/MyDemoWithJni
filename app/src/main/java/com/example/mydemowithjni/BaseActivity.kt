package com.example.mydemowithjni

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setListener()
        initData()
    }

    abstract fun initData()

    abstract fun getLayoutId(): Int

    abstract fun setListener()

}
