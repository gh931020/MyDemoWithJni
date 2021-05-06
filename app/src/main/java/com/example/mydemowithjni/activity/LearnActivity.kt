package com.example.mydemowithjni.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.elvishew.xlog.XLog
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.R
import kotlinx.android.synthetic.main.activity_learn.*

const val GAME_STATE_KEY = "game_state_key"
const val TEXT_VIEW_KEY = "text_view_key"
class LearnActivity: BaseActivity() {

    companion object{
        const val REQUEST_CODE = 1
        const val START_FOR_RESULT_KEY = "start_for_result_key"

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LearnActivity::class.java)

            context.startActivity(starter)
        }
    }

    var gameState: String? = null
    override fun restroeState(savedInstanceState: Bundle?) {
        // recovering the instance state
        gameState = savedInstanceState?.getString(GAME_STATE_KEY)
    }

    override fun initData() {
        //隐式启动Activity
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "textMessage")
        }
       // startActivity(sendIntent)
    }

    override fun getLayoutId(): Int =
        R.layout.activity_learn

    override fun setListener() {
        startBtn.setOnClickListener {
            StartActivity.start(this@LearnActivity)
            // Activity A 的 onPause() 方法执行。
            // Activity B 的 onCreate()、onStart() 和 onResume() 方法依次执行（Activity B 现在具有用户焦点）。
            // 然后，如果 Activity A 在屏幕上不再显示，其 onStop() 方法执行。
        }
        startForResultBtn.setOnClickListener {
            StartForResultActivity.startForResult(this@LearnActivity, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK){
                    XLog.i(data?.getStringExtra(START_FOR_RESULT_KEY))
                }
            }
            else -> {
            }
        }
    }

    /**
     * This callback is called only when there is a saved instance that is previously saved by using
     * onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
     * other state here, possibly usable after onStart() has completed.
     * The savedInstanceState Bundle is same as the one used in onCreate().
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        saveTv.text = savedInstanceState.getString(GAME_STATE_KEY)
        // 注意：您应始终调用 onRestoreInstanceState() 的父类实现，以便默认实现可以恢复视图层次结构的状态。
        super.onRestoreInstanceState(savedInstanceState)
    }

    /**
     * invoked when the activity may be temporarily destroyed, save the instance state here
     * 注意：当用户显式关闭 Activity 时，或者在其他情况下调用 finish() 时，系统不会调用 onSaveInstanceState()。
     * 建议您将保存的状态保持在 50k 数据以下。
     */
    override fun onSaveInstanceState(outState: Bundle) {
        // Bundle 对象并不适合保留大量数据，因为它需要在主线程上进行序列化处理并占用系统进程内存
        // 如需保存大量数据，您应组合使用持久性本地存储、onSaveInstanceState() 方法和 ViewModel 类来保存数据，正如保存界面状态中所述。
        outState.run {
            putString(GAME_STATE_KEY, gameState)
            putString(TEXT_VIEW_KEY, saveTv.text.toString())
        }
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState)
    }

}
