package com.example.mydemowithjni.activity

import android.os.Bundle
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.ActivityDatastoreBinding
import com.example.mydemowithjni.proto.settingsDataStore
import com.example.mydemowithjni.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStoreActivity: BaseActivity<ActivityDatastoreBinding>() {

    lateinit var EXAMPLE_COUNTER: Preferences.Key<Int>

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun initViewBinding(): ActivityDatastoreBinding = ActivityDatastoreBinding.inflate(layoutInflater)

    override fun setListener() {

    }

    override fun initData() {
        // 从Preferences DataStore中读取数据
        // 由于Preference DataStore不适用预定义的架构,因此必须使用相应的键类型函数为需要存储在DataStore<Preferences>
        // 实例中的每一个值定义键,例如,为一个int值定义一个键,使用intPreferencesKey(),然后使用DataStore.data属性
        // 通过Flow提供适当的存储值

        EXAMPLE_COUNTER = intPreferencesKey("example_counter")
        val exampleCounterFlow: Flow<Int> = this.dataStore.data.map {preferences->
            preferences[EXAMPLE_COUNTER] ?: 0
        }
        // 通过阻塞的方式获取数据
        runBlocking {
            exampleCounterFlow.first()
        }
        // 从proto DataStore中读取内容
        val exampleCounterFlowByProto: Flow<Int> = settingsDataStore.data.map {mySettings->
            mySettings.exampleCounter
        }

    }
    suspend fun incrementCounter(){
        // Preferences DataStore 写入数据
        dataStore.edit {settings->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = currentCounterValue +1
        }
        // Proto DataStore写入数据
        settingsDataStore.updateData {currentSettings->
            currentSettings.toBuilder()
                .setExampleCounter(currentSettings.exampleCounter + 1)
                .build()
        }

    }

}