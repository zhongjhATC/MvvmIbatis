package com.zhongjh.app.view

import kotlin.jvm.JvmOverloads
import android.widget.LinearLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import android.graphics.drawable.AnimationDrawable
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.zhongjh.app.R
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * 自定义刷新动画
 * https://juejin.cn/post/6844903703867031559
 * @author zhongjh
 * @date 2021/4/14
 */
class CustomRefreshHeader @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), RefreshHeader {
    private val mImage: ImageView
    private var pullDownAnim: AnimationDrawable? = null
    private var refreshingAnim: AnimationDrawable? = null

    /**
     * 是否执行过翻跟头动画的标记
     */
    private var hasSetPullDownAnim = false
    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    @SuppressLint("RestrictedApi")
    override fun setPrimaryColors(vararg colors: Int) {
    }

    @SuppressLint("RestrictedApi")
    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    @SuppressLint("RestrictedApi")
    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.scaleX = percent
            mImage.scaleY = percent

            // 是否执行过翻跟头动画的标记
            if (hasSetPullDownAnim) {
                mImage.setImageResource(R.drawable.anim_pull_start)
                pullDownAnim = mImage.drawable as AnimationDrawable
                pullDownAnim!!.start()
                hasSetPullDownAnim = false
            }
        }

        // 当下拉的高度达到Header高度100%时，开始加载正在下拉的初始动画，即翻跟头，因为这个方法是不停调用的，防止重复
        if (percent >= 1.0 && !hasSetPullDownAnim) {
            mImage.setImageResource(R.drawable.anim_pull_end)
            pullDownAnim = mImage.drawable as AnimationDrawable
            pullDownAnim!!.start()
            hasSetPullDownAnim = true
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    @SuppressLint("RestrictedApi")
    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    @SuppressLint("RestrictedApi")
    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        // 结束动画
        if (pullDownAnim != null && pullDownAnim!!.isRunning) {
            pullDownAnim!!.stop()
        }
        if (refreshingAnim != null && refreshingAnim!!.isRunning) {
            refreshingAnim!!.stop()
        }
        // 重置状态
        hasSetPullDownAnim = false
        return 0
    }

    @SuppressLint("RestrictedApi")
    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    /**
     * 状态改变时调用。在这里切换第三阶段的动画卖萌小人
     */
    @SuppressLint("RestrictedApi")
    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.PullDownToRefresh -> {
                // 下拉刷新开始，正在下拉还没松手时调用
                Log.d("onStateChanged", "PullDownCanceled")
                // 每次重新下拉时，将图片资源重置为小人的大脑袋
                mImage.setImageResource(R.drawable.commonui_pull_image)
            }
            RefreshState.Refreshing -> {
                // 正在刷新。只调用一次
                Log.d("onStateChanged", "Refreshing")
                // 状态切换为正在刷新状态时，设置图片资源为小人卖萌的动画并开始执行
                mImage.setImageResource(R.drawable.anim_pull_refreshing)
                refreshingAnim = mImage.drawable as AnimationDrawable
                refreshingAnim!!.start()
            }
            RefreshState.ReleaseToRefresh -> {}
            else -> {}
        }
    }

    init {
        val view = inflate(context, R.layout.widget_custom_refresh_header, this)
        mImage = view.findViewById(R.id.iv_refresh_header)
    }
}