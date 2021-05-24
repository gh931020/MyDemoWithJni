package com.example.mydemowithjni.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import com.example.mydemowithjni.databinding.ActivityStartForResultBinding

class StartForResultActivity : BaseActivity<ActivityStartForResultBinding>() {

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

        viewBinding.resultBackBtn.setOnClickListener {
            // 设置回调
            setResult(Activity.RESULT_OK, Intent().putExtra(LearnActivity.START_FOR_RESULT_KEY, "activity_start_for_result"))
            finish()
        }
    }

    override fun initViewBinding(): ActivityStartForResultBinding {
        return ActivityStartForResultBinding.inflate(layoutInflater)
    }

    override fun restroeState(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setListener() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}