package com.example.mydemowithjni.databinding

import android.view.View
import com.elvishew.xlog.XLog

class MyHandler {
    fun onClickHandler(view: View){
        XLog.i("onClickHandler")
    }
    fun onClickBindHandler(user: User){
        XLog.i("onClickBindHandler + $user")
    }

    fun onCompletedChanged(user: User, completed: Boolean){
        XLog.i("onCompletedChanged + $user")
    }

    fun onLongClick(view: View, user: User): Boolean {
        XLog.i("onLongClick + $user")
        return true
    }
}