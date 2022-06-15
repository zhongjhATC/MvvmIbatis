package com.zhongjh.stickyhead

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * 粘性顶部的容器
 * @author zhongjh
 * @date 2022/6/15
 */
class StickyHeadContainer : ViewGroup {

    private val mOffset = 0
    private var mLeft = 0
    private var mRight = 0
    private var mTop = 0
    private var mBottom = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val child = getChildAt(0)

        val params: LayoutParams = child.layoutParams
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        //获取view的margin设置参数
        if (params is MarginLayoutParams) {
            val lp = child.layoutParams as MarginLayoutParams
            mLeft = paddingLeft + lp.leftMargin
            mTop = paddingTop + lp.topMargin + mOffset
        } else {
            mLeft = paddingLeft
            mTop = paddingTop + mOffset
        }
        mRight = child.measuredWidth + mLeft
        mBottom = child.measuredHeight + mTop


        // 根据子view设置本身的宽高
        child.layout(mLeft, mTop, mRight, mBottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var desireHeight: Int
        var desireWidth: Int

        val count = childCount

        require(count == 1) { "只允许存在一个子View" }

        val child = getChildAt(0)
        // 测量子元素并考虑外边距
        measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
        // 获取子元素的布局参数
        val lp = child.layoutParams as MarginLayoutParams
        // 计算子元素宽度，取子控件最大宽度
        desireWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
        // 计算子元素高度
        desireHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

        // 考虑父容器内边距

        // 考虑父容器内边距
        desireWidth += paddingLeft + paddingRight
        desireHeight += paddingTop + paddingBottom
        // 尝试比较建议最小值和期望值的大小并取大值
        desireWidth = desireWidth.coerceAtLeast(suggestedMinimumWidth)
        desireHeight = desireHeight.coerceAtLeast(suggestedMinimumHeight)
        // 设置最终测量值
        setMeasuredDimension(
            resolveSize(desireWidth, widthMeasureSpec),
            resolveSize(desireHeight, heightMeasureSpec)
        )
    }
}