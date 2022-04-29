package com.zhongjh.mvvmibatis.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 *
 * @author zhongjh
 * @date 2021/3/25
 * Window有关工具
 */
object ScreenUtil {
    /**
     * 设置全屏
     *
     * @param activity            当前activity
     * @param isBottomTranslucent 是否优化底部，建议只有splash界面设置，其他界面因为兼容问题，还是不处理合适
     */
    fun setFullScreen(activity: Activity, isBottomTranslucent: Boolean) {
        // http为空的才启用全屏，因为外链是没有title返回的
        val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        var flagTranslucentNavigation = 0
        if (isBottomTranslucent) {
            // 用这个会导致缩小导航栏的时候底部空白
            flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val attributes = window.attributes
            if (isBottomTranslucent) {
                // 用这个会导致缩小导航栏的时候底部空白
                attributes.flags = attributes.flags or flagTranslucentNavigation
            }
            window.attributes = attributes
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            val window = activity.window
            val attributes = window.attributes
            if (isBottomTranslucent) {
                // 用这个会导致缩小导航栏的时候底部空白
                attributes.flags =
                    attributes.flags or (flagTranslucentStatus or flagTranslucentNavigation)
            }
            attributes.flags = attributes.flags or flagTranslucentStatus
            window.attributes = attributes
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
}