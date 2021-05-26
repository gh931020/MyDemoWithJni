package com.example.mydemowithjni.webview

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface(private val mContext: Context) {
    /**
     * 创建javaScript接口,用于网页调用
     * @param toast String
     */
    @JavascriptInterface
    fun showToast(toast: String){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}