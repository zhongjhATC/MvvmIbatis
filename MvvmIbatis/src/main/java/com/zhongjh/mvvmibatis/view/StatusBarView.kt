package com.zhongjh.mvvmibatis.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.zhongjh.mvvmibatis.R
import com.zhongjh.mvvmibatis.utils.StatusBarUtil.getStatusBarHeight

/**
 *
 * @author zhongjh
 * @date 2021/4/9
 * 这是使用状态栏的默认高度，配合StatusBarUtils.initStatusBarHeight使用
 */
class StatusBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var statusBarHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 设置高度
        setMeasuredDimension(widthMeasureSpec, statusBarHeight)
    }

    private fun setup() {
        statusBarHeight = getStatusBarHeight(resources)
    }

    init {
        setup()
    }
}