package com.zhongjh.stickyhead

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.ViewCompat

/**
 * 粘性顶部的容器
 * @author zhongjh
 * @date 2022/6/15
 */
class StickyHeadContainer : ViewGroup {

    private var mOffset = 0
    private var mLastOffset = Int.MIN_VALUE
    private var mLastStickyHeadPosition = Int.MIN_VALUE

    private var mLeft = 0
    private var mRight = 0
    private var mTop = 0
    private var mBottom = 0

    private var mDataCallback: DataCallback? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val child = getChildAt(0)
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop

        // 获取view的margin设置参数
        val lp = child.layoutParams as MarginLayoutParams
        mLeft = paddingLeft + lp.leftMargin
        mTop = paddingTop + lp.topMargin + mOffset
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

    /**
     * 生成布局参数,将布局参数包装成我们的
     */
    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    /**
     * 生成布局参数,不使用默认的LayoutParams，而使用MarginLayoutParams
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    /**
     * 查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
     */
    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParams
    }

    fun scrollChild(offset: Int) {
        if (mLastOffset != offset) {
            mOffset = offset
            ViewCompat.offsetTopAndBottom(getChildAt(0), mOffset - mLastOffset)
        }
        mLastOffset = mOffset
    }

    fun getChildHeight(): Int {
        return getChildAt(0).height
    }

    fun onDataChange(stickyHeadPosition: Int) {
        if (mLastStickyHeadPosition != stickyHeadPosition) {
            mDataCallback?.onDataChange(stickyHeadPosition)
        }
        mLastStickyHeadPosition = stickyHeadPosition
    }

    fun reset() {
        mLastStickyHeadPosition = Int.MIN_VALUE
    }

    fun setDataCallback(dataCallback: DataCallback) {
        mDataCallback = dataCallback
    }

    interface DataCallback {
        fun onDataChange(pos: Int)
    }
}