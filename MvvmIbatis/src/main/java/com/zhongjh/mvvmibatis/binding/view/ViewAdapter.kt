package com.zhongjh.mvvmibatis.binding.view

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.zhongjh.mvvmibatis.extend.clickFlow
import com.zhongjh.mvvmibatis.extend.throttleFirst
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 构造View的一些事件
 * @author zhongjh
 * @date 2022/5/5
 */
object ViewAdapter {

    /**
     * 防止重复点击
     * @param view 控件本身
     * @param clickListener 点击事件
     */
    @BindingAdapter(value = ["android:onClick"])
    @JvmStatic
    fun noRepeatClick(view: View, clickListener: View.OnClickListener) {
        view.clickFlow().throttleFirst().onEach {
            clickListener.onClick(view)
        }.launchIn(MainScope())
    }
}