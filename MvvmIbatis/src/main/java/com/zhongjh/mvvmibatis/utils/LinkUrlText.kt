package com.zhongjh.mvvmibatis.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.zhongjh.mvvmibatis.phone.WebViewActivity

/**
 * 链接文字点击
 *
 * @author zhongjh
 * @date 2021/7/22
 */
class LinkUrlText(private val activity: Activity, private val url: String) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        //设置文本的颜色
        ds.color = Color.RED
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        activity.startActivity(intent)
    }

}