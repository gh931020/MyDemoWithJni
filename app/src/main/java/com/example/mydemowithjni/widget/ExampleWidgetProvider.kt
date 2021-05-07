package com.example.mydemowithjni.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.example.mydemowithjni.MainActivity
import com.example.mydemowithjni.R

class ExampleWidgetProvider: AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // 用户可能放置多个widget,
        appWidgetIds.forEach {appWidgetId->
            // 创建指向Activity的Intent
            val pendingIntent = Intent(context, MainActivity::class.java).let {
                PendingIntent.getActivity(context, 0, it, 0)
            }
            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views:RemoteViews = RemoteViews(
                context.packageName, R.layout.example_appwidget
            ).apply {
                setOnClickPendingIntent(R.id.widgetBtn, pendingIntent)
            }
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }
}