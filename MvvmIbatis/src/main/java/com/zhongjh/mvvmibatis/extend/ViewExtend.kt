package com.zhongjh.mvvmibatis.extend

import android.view.View
import com.zhongjh.mvvmibatis.R

/***
 * 带延迟过滤的点击事件View扩展
 * @param time Long 延迟时间，默认800毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.onClick(time: Long = 1000, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTime) != null) getTag(R.id.triggerLastTime) as Long else 0
    set(value) {
        setTag(R.id.triggerLastTime, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelay) != null) getTag(R.id.triggerDelay) as Long else -1
    set(value) {
        setTag(R.id.triggerDelay, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    if (flag) {
        triggerLastTime = currentClickTime
    }
    return flag
}






