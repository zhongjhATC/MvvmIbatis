package com.zhongjh.mvvmibatis.listener

import android.view.View
import com.zhongjh.mvvmibatis.constant.Constants.CLICK_DURATION

/**
 * 1秒只允许点击一次
 *
 * @author zhongjh
 * @date 2022/3/24
 */
abstract class ThrottleOnClickListener : View.OnClickListener {
    private var mLastClickTime: Long = 0
    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastClickTime > CLICK_DURATION) {
            // 经过了足够长的时间，允许点击
            onClick()
            mLastClickTime = currentTime
        }
    }

    /**
     * 开放出来的点击方法
     */
    protected abstract fun onClick()
}