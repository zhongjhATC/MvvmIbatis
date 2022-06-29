package com.zhongjh.mvvmibatis.phone

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.zhongjh.mvvmibatis.R
import com.zhongjh.mvvmibatis.databinding.ActivityWebViewBinding

/**
 * WebViewActivity
 * @author zhongjh
 * @date 2022/3/28
 */
class WebViewActivity : AppCompatActivity() {

    private val binding: ActivityWebViewBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_web_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.layoutToolbar.toolbar.setNavigationOnClickListener { finish() }
        initWebView()
        initData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        // 开启js脚本支持,会有警告，因为启用js可能不安全，忽略警告即可
        val settings: WebSettings = binding.webView.settings
        settings.javaScriptEnabled = true
        // WebSettings.MIXED_CONTENT_ALWAYS_ALLOW 这种模式下，WebView是允许一个安全站点（https）去加载另一个非安全站点内容（http）,这是WebView最不安全的操作模式，官方文档也不推荐使用；
        settings.mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
        // 让WebView支持<meta>标签的viewport属性
        settings.useWideViewPort = true
        // 可能的话使所有列的宽度不超过屏幕宽度
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        // 设置加载进来的页面自适应手机屏幕 WebView加载的页面的模式
        settings.loadWithOverviewMode = true
        // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放
        settings.setSupportZoom(true)
        // 设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        settings.builtInZoomControls = true
        // 设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上，默认true，展现在控件上
        settings.displayZoomControls = false
        // 设置是否开启定位功能，默认true，开启定位
        settings.setGeolocationEnabled(true)
        // 设置 缓存模式 缓冲大小 15M
        val dir: String = applicationContext.getDir("database", Context.MODE_PRIVATE).path
        settings.setGeolocationDatabasePath(dir)
        settings.domStorageEnabled = true
        settings.setAppCacheMaxSize((1024 * 1024 * 15).toLong())
        val cacheDirPath: String = applicationContext.filesDir.absolutePath.toString() + "cache/"
        settings.setAppCachePath(cacheDirPath)
        settings.setAppCacheEnabled(true)
        // 设置在WebView内部是否允许访问文件，默认允许访问
        settings.allowFileAccess = true
        // 触摸焦点起作用
        binding.webView.requestFocus()
        binding.webView.webChromeClient = webChromeClient
        binding.webView.webViewClient = webViewClient
        // 下载事件
        binding.webView.setDownloadListener { url, _, _, _, _ ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun initData() {
        binding.webView.loadUrl(intent.getStringExtra("url"))
    }

    /**
     * 进度、标题显示
     */
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                this@WebViewActivity.binding.gifLoading.visibility = GONE
                this@WebViewActivity.binding.progressBar.visibility = GONE
            } else {
                this@WebViewActivity.binding.gifLoading.visibility = VISIBLE
                this@WebViewActivity.binding.progressBar.visibility = VISIBLE
                this@WebViewActivity.binding.progressBar.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(webView: WebView, title: String) {
            super.onReceivedTitle(webView, title)
            this@WebViewActivity.binding.layoutToolbar.tvTitle.text = title
        }
    }

    /**
     * webView其他事件
     */
    private val webViewClient: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // 这里需要写成false，true会导致h5中的二级界面可能无法跳转
            return false
        }

        override fun onReceivedSslError(
            webView: WebView,
            sslErrorHandler: SslErrorHandler,
            sslError: SslError
        ) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError)
            // 设置接收所有证书
            sslErrorHandler.proceed()
        }

        override fun onPageFinished(view: WebView, url: String) {
            this@WebViewActivity.binding.gifLoading.visibility = GONE
            this@WebViewActivity.binding.progressBar.visibility = GONE
            super.onPageFinished(view, url)
        }
    }
}