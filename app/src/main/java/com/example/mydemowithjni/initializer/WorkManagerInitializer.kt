package com.example.mydemowithjni.initializer

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import java.util.Collections.emptyList

class WorkManagerInitializer: Initializer<WorkManager> {
    /**
     * 需要在manifest中配置信息
     */
    override fun create(context: Context): WorkManager {
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }
    // 如果WorkManager的初始化需要依赖其他库,则需要在这里进行返回
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return emptyList()
    }
    /*
     // Initializes ExampleLogger.
    class ExampleLoggerInitializer : Initializer<ExampleLogger> {
        override fun create(context: Context): ExampleLogger {
            // WorkManager.getInstance() is non-null only after
            // WorkManager is initialized.
            return ExampleLogger(WorkManager.getInstance(context))
        }

        override fun dependencies(): List<Class<out Initializer<*>>> {
            // Defines a dependency on WorkManagerInitializer so it can be
            // initialized after WorkManager is initialized.
            return listOf(WorkManagerInitializer::class.java)
        }
    }
     */
}