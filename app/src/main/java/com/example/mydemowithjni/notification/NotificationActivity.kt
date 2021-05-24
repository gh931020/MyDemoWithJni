package com.example.mydemowithjni.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.ActivityNotificationBinding
const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
class NotificationActivity: BaseActivity<ActivityNotificationBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, NotificationActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initViewBinding(): ActivityNotificationBinding = ActivityNotificationBinding.inflate(layoutInflater)

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {
        viewBinding.apply {
            notificationBtn.setOnClickListener {
                // 创建用于相应通知点击事件的intent
                val intent = Intent(this@NotificationActivity, DetailsActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent = PendingIntent.getActivity(this@NotificationActivity, 0, intent, 0)

                val builder = NotificationCompat.Builder(this@NotificationActivity, "id")
                    .setSmallIcon(R.drawable.wifi)
                    .setContentTitle("My notification")
                    .setContentText("much longer text that cannot fit one line..")
                    .setStyle(NotificationCompat.BigTextStyle().bigText("much longer text that cannot fit one line.."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                        // 用户点击后自动移除通知
                    .setAutoCancel(true).build()

                with(NotificationManagerCompat.from(this@NotificationActivity)){
                    // notificationId is a unique int for each notification that you must define
                    notify(886, builder)
                }
            }

            actionBroadBtn.setOnClickListener {
                val snoozeIntent = Intent(this@NotificationActivity, BroadcastReceiver::class.java).apply {
                    action = "com.example.mydemowithjni.notification.ACTION_SNOOZE"
                    putExtra(EXTRA_NOTIFICATION_ID, 0)
                }

                val snoozePendingIntent = PendingIntent.getBroadcast(this@NotificationActivity, 0, snoozeIntent, 0)
                val builder = NotificationCompat.Builder(this@NotificationActivity, "snoozeId")
                    .setSmallIcon(R.drawable.wifi)
                    .setContentTitle("My snooze notification")
                    .setContentText(" hello world")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.wifi_1, "snooze", snoozePendingIntent)
            }
        }
    }

    /**
     * 在8.0及以后系统中创建通知channel
     */
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "channel name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("id", name, importance).apply {
                description = descriptionText
            }
            // register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun initData() {

    }
}