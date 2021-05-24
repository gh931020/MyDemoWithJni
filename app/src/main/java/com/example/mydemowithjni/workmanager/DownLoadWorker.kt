package com.example.mydemowithjni.workmanager

import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.contentValuesOf
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.mydemowithjni.R
import java.security.PrivateKey

class DownLoadWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    // 获取通知管理器
    private val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val inputUrl = inputData.getString(KEY_INPUT_URL)?: return Result.failure()
        val outputFile =inputData.getString(KEY_OUTPUT_FILE_NAME)?: return Result.failure()

        val progress = "Starting Download"
        setForeground(createForegroundInfo(progress))

        download(inputUrl, outputFile)
        return Result.success()
    }

    private fun download(inputUrl: String, outputFile: String) {
        // Downloads a file and updates bytes read
        // Calls setForegroundInfo() periodically when it needs to update
        // the ongoing Notification
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id  = applicationContext.getString(R.string.notification_channel_id)
        val title = applicationContext.getString(R.string.notification_title)
        val cancle = applicationContext.getString(R.string.cancle_download)
        // create PendingIntent ,can be used to cancle the worker
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(getId())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.number_2_circle)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, cancle , intent)
            .build()
        // 指定前台服务类型
        return ForegroundInfo(0, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
    }

    private fun createChannel() {
        // 创建一个notification channel
    }
    companion object {
        const val KEY_INPUT_URL = "KEY_INPUT_URL"
        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
    }
}