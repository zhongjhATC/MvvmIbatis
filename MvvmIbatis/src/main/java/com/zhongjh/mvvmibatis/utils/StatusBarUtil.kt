package com.zhongjh.mvvmibatis.utils

import android.content.res.Resources
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zhongjh.mvvmibatis.constant.Constants
import com.zhongjh.mvvmibatis.utils.SPCacheUtil.getInt
import com.zhongjh.mvvmibatis.utils.SPCacheUtil.put

/**
 * 状态栏工具
 *
 * @author zhongjh
 * @date 2022/3/28
 */
object StatusBarUtil {

    /**
     * 初始化获取状态栏高度
     * 只在第一个打开MainActivity时调用
     *
     * @param view 该view必须有android:fitsSystemWindows="true"属性，并且生效，如果不生效是不会获取到状态栏高度的
     */
    fun initStatusBarHeight(view: View?) {
        val statusBarHeight = getInt(Constants.STATUS_BAR_HEIGHT, 0)
        if (statusBarHeight <= 0) {
            ViewCompat.setOnApplyWindowInsetsListener(view!!) { v: View?, insets: WindowInsetsCompat ->
                put(Constants.STATUS_BAR_HEIGHT, insets.systemWindowInsetTop)
                insets
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    fun getStatusBarHeight(resources: Resources): Int {
        // 获得状态栏高度
        val statusBarHeight = getInt(Constants.STATUS_BAR_HEIGHT, 0)
        return if (statusBarHeight <= 0) {
            getStatusBarHeightResources(resources)
        } else {
            statusBarHeight
        }
    }

    private fun getStatusBarHeightResources(resources: Resources): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}