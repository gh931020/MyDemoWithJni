package com.example.mydemowithjni.databinding

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.elvishew.xlog.XLog

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("AppCompatCustomView")
class View(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    TextView(context, attrs, defStyleAttr, defStyleRes) {
    init {
        XLog.i("com.example.mydemowithjni.databinding.View")
    }
}