package com.zhongjh.mvvmibatis.base.ui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhongjh.mvvmibatis.base.viewmodel.BaseViewModel

/**
 *
 * @author zhongjh
 * @date 2022/4/29
 */
abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {
    /**
     * A data-binding property will be initialized before being called [onCreate].
     * And inflates using the [contentLayoutId] as a content view for activities.
     */
    @BindingOnly
    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, contentLayoutId, bindingComponent)
    }
}