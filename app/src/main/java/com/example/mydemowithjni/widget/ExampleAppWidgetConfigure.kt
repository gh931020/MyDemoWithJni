package com.example.mydemowithjni.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import com.example.mydemowithjni.R

class ExampleAppWidgetConfigure : AppCompatActivity() {
    var appWidgetId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);
        // Set the view layout resource to use.
        setContentView(R.layout.appwidget_configure)

        findViewById<Button>(R.id.configureCompleteBtn).setOnClickListener {
            // 1.获取当前启动Activity的widgetId
            appWidgetId = intent?.extras?.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
            // 2.执行widget配置

            // 3.获取AppWidgetManager
            val appWidgetManager = AppWidgetManager.getInstance(this)

            // 4.通过调用updateAppWidget(int ,RemoteViews)来使用RemoteViews布局更新widget
            RemoteViews(packageName, R.layout.example_appwidget).also { views->
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }

            // 5.创建返回Intent, 为其设置Acitivity结果,然后finish()
            val resultValue = Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }
}