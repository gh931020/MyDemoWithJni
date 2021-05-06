package com.example.mydemowithjni

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.mydemowithjni.activity.FragmentActivity
import com.example.mydemowithjni.activity.LearnActivity
import com.example.mydemowithjni.res.ResActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        // Example of a call to a native method
        findViewById<TextView>(R.id.sample_text).text = stringFromJNI()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setListener() {
        resBtn.setOnClickListener {
            ResActivity.start(this@MainActivity)
        }
        activityBtn.setOnClickListener {
            LearnActivity.start(this@MainActivity)
        }
        fragmentBtn.setOnClickListener {
            FragmentActivity.start(this@MainActivity)
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
}