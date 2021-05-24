package com.example.mydemowithjni

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.mydemowithjni.activity.FragmentActivity
import com.example.mydemowithjni.activity.LearnActivity
import com.example.mydemowithjni.constraint.ConstraintActivity
import com.example.mydemowithjni.databinding.ActivityMainBinding
import com.example.mydemowithjni.databinding.DataBindingActivity
import com.example.mydemowithjni.intent.IntentActivity
import com.example.mydemowithjni.notification.NotificationActivity
import com.example.mydemowithjni.res.ResActivity
import com.example.mydemowithjni.viewpager2.ViewPage2Activity
import com.example.mydemowithjni.workmanager.WorkActivity
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {
        // Example of a call to a native method
        findViewById<TextView>(R.id.sample_text).text = stringFromJNI()
    }

    override fun setListener() {

        viewBinding.apply {
            resBtn.setOnClickListener {
                ResActivity.start(this@MainActivity)
            }
            activityBtn.setOnClickListener {
                LearnActivity.start(this@MainActivity)
            }
            fragmentBtn.setOnClickListener {
                FragmentActivity.start(this@MainActivity)
            }
            dataBindingBtn.setOnClickListener {
                DataBindingActivity.start(this@MainActivity)
            }
            workManagerBtn.setOnClickListener {
                WorkActivity.start(this@MainActivity)
            }
            viewpage2Btn.setOnClickListener {
                ViewPage2Activity.start(this@MainActivity)
            }
            intentBtn.setOnClickListener {
                IntentActivity.start(this@MainActivity)
            }
            constraintBtn.setOnClickListener {
                ConstraintActivity.start(this@MainActivity)
            }
            notificationBtn.setOnClickListener {
                NotificationActivity.start(this@MainActivity)
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.example_menu, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem){
        Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by
        // <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)">onOptionsItemSelected()</a></code>
    }

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
    fun toCall(){
        val callIntent: Intent = Uri.parse("tel:5551234").let { number ->
            Intent(Intent.ACTION_DIAL, number)
        }
        // Try to invoke the intent.
        try {
            startActivity(callIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            // 没有可以处理这个Intent的Activity
        }
    }
    fun toMap(){
        val mapIntent: Intent = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California").let {location->
            Intent(Intent.ACTION_VIEW, location)
        }
    }
    fun toWeb(){
        val webIntent: Intent = Uri.parse("https://www.android.com").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
    }
    fun sendEmail(){
        Intent(Intent.ACTION_SEND).apply {
            // The intent does not have a URI, so declare the "text/plain" MIME type
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("zzj@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            putExtra(Intent.EXTRA_TEXT, "Email message text")
            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
        }
    }
    fun creatDaily(){
        // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
        Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
            val beginTime: Calendar = Calendar.getInstance().apply {
                set(2021, 0, 23, 7, 30)
            }
            val endTime = Calendar.getInstance().apply {
                set(2021, 0, 23, 10, 30)
            }
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            putExtra(CalendarContract.Events.TITLE, "Ninja class")
            putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo")
        }
    }

    /**
     * 每次都会弹出选择对话框
     */
    fun creatChooser(){
        val intent = Intent(Intent.ACTION_SEND)

        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        val title = "R.string.chooser_title"
        // Create intent to show chooser
        val chooser = Intent.createChooser(intent, title)

        // Try to invoke the intent.
        try {
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }
    }
}