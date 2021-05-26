package com.example.mydemowithjni.hilt

import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityHiltBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Hilt 可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项
 * 目前支持: Application, Activity, Fragment, View, Service, BroadcastReceiver
 *
 * 如果您使用@AndroidEntryPoint为某个Android类添加注释,则还必须为依赖于该类的Andorid类添加注释.
 * 例如,为某个Fragment添加注释,则还必须为使用该Fragment的所有Activity添加注释
 *
 * 仅支持扩展ComponentActivity的Activity,如AppCompatActivity
 * 仅支持扩展androidx.Fragment的Fragment
 *
 * 使用@Inject 注释执行字段注入]
 *
 * Hilt注入的类可以有同样使用注入的其他基类,如果这些类是抽象类,则他们不需要@AndroidEntryPoint注释
 */
@AndroidEntryPoint
class HiltActivity: BaseActivity<ActivityHiltBinding>() {

    override fun initViewBinding(): ActivityHiltBinding = ActivityHiltBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}