package com.example.mydemowithjni.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.SafeBrowsingResponse
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mydemowithjni.BaseActivity
import com.example.mydemowithjni.databinding.ActivityWebviewBinding

class WebviewActivity: BaseActivity<ActivityWebviewBinding>() {
    override fun initViewBinding(): ActivityWebviewBinding = ActivityWebviewBinding.inflate(layoutInflater)

    private var safeBrowsingIsInitialized: Boolean = false

    override fun restroeState(savedInstanceState: Bundle?) {

    }

    override fun setListener() {

    }

    override fun initData() {
        viewBinding.webView.apply {
            settings.javaScriptEnabled = true
            // 为在WebView中运行的JavaScript船舰名为Android的接口
            /*
                <input type="button" value="Say hello" onClick="showAndroidToast('Hello Android!')" />

                <script type="text/javascript">
                    function showAndroidToast(toast) {
                        Android.showToast(toast);
                    }
                </script>
             */
            // 绑定到 JavaScript 的对象在另一个线程中运行，而不是在构造它的线程中运行。
            addJavascriptInterface(WebAppInterface(this@WebviewActivity), "Android")
            //当用户在 WebView 中点击网页中的链接时，Android 的默认行为是启动处理网址的应用。
            // 默认网络浏览器通常会打开并加载目标网址。
            // 不过，您可以为 WebView 替换此行为，以便在 WebView 内打开链接。
            // 然后，您可以允许用户向后/向前浏览由您的 WebView 维护的网页历史记录。
            // 如果您希望更好地控制用户点击的链接的加载位置，请创建您自己的 WebViewClient 以替换
            webViewClient = myWebViewClient
        }
        safeBrowsingIsInitialized = false

    }

    private val myWebViewClient = object: WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            if (request?.url?.host== "www.example.com"){
                // This is my web site, so do not override; let my WebView load the page
                return false
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent(Intent.ACTION_VIEW, request?.url).apply {
                startActivity(this)
            }
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && viewBinding.webView.canGoBack()){
            viewBinding.webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}