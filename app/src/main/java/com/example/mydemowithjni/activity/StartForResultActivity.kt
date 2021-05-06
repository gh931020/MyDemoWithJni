package com.example.mydemowithjni.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemowithjni.R
import kotlinx.android.synthetic.main.activity_start_for_result.*

class StartForResultActivity : AppCompatActivity() {

    companion object{
        @JvmStatic
        fun startForResult(activity: Activity, requestCode:Int) {
            val starter = Intent(activity, StartForResultActivity::class.java)

            activity.startActivityForResult(starter, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_for_result)

        resultBackBtn.setOnClickListener {
            // 设置回调
            setResult(Activity.RESULT_OK, Intent().putExtra(LearnActivity.START_FOR_RESULT_KEY, "activity_start_for_result"))
            finish()
        }
    }
}