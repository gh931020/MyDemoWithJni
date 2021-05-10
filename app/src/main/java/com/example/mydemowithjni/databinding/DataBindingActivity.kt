package com.example.mydemowithjni.databinding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
import com.example.mydemowithjni.MyDataBindingSample
import com.example.mydemowithjni.R

/**
 * 添加 apply plugin: 'kotlin-kapt'
 * 添加 dataBinding {
 *          enabled true
 *      }
 */
class DataBindingActivity : AppCompatActivity(){

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DataBindingActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 通过代理的方式创建viewModel
         * Create a ViewModel the first time the system calls an activity's onCreate() method.
         * Re-created activities receive the same MyViewModel instance created by the first activity.
         * Use the 'by viewModels()' Kotlin property delegate from the activity-ktx artifact
         *
         * 注意：ViewModel 绝不能引用视图、Lifecycle 或可能存储对 Activity 上下文的引用的任何类。
         */
        val viewModel: NameViewModel by viewModels()

        setContentView(R.layout.activity_databinding)
        // 这里泛型的名字要注意自己有没有在xml中自定义生成的class名称!!!!
        val dataBinding = DataBindingUtil.setContentView<MyDataBindingSample>(this, R.layout.activity_databinding)
        dataBinding.user = User("Test", "unit", 18)
        dataBinding.lifecycleOwner = this
        //为viewModel中的liveData对象添加观察者
        viewModel.currentName.observe(this,
            {
                XLog.i("currentName is $it")
            })

        dataBinding.clickBindBtn.setOnClickListener {
            val anotherName = "zzzzz"
            // 您必须调用 setValue(T) 方法以从主线程更新 LiveData 对象。
            // 如果在子线程中执行代码，您可以改用 postValue(T) 方法来更新 LiveData 对象。
            viewModel.currentName.value = anotherName
        }
    }



}


/**
使用可观察对象类型的map进行数据的存储
ObservableArrayMap<String, Any>().apply {
    put("firstName", "Google")
    put("lastName", "Inc.")
    put("age", 17)
}

<data>
//导入Map类
<import type="android.databinding.ObservableMap"/>
//创建user变量
<variable name="user" type="ObservableMap<String, Object>"/>
</data>
…
<TextView
使用map.key的方式获取value
android:text="@{user.lastName}"
android:layout_width="wrap_content"
android:layout_height="wrap_content"/>
<TextView
android:text="@{String.valueOf(1 + (Integer)user.age)}"
android:layout_width="wrap_content"
android:layout_height="wrap_content"/>

即时绑定
当可变或可观察对象发生更改时，绑定会按照计划在下一帧之前发生更改。但有时必须立即执行绑定。
要强制执行，请使用 executePendingBindings() 方法。
*/