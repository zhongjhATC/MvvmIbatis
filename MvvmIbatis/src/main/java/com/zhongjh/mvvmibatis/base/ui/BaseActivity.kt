package com.zhongjh.mvvmibatis.base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.mvvmibatis.R

/**
 * 一个基类Activity
 *
 * 初始化[mBinding],并且负责生命周期中的销毁
 *
 * 按照顺序拟定了代码规范可重写以下方法 [initParam]、[initListener]、[initialize]
 *
 * @date 2022/4/29
 * @author zhongjh
 */
abstract class BaseActivity<VDB : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {

    /**
     * 初始化布局，并且生成viewDataBinding
     */
    protected val mBinding: VDB by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, contentLayoutId, DataBindingUtil.getDefaultComponent())
    }

    /**
     * 用于绑定相关viewModel
     */
    protected inline fun binding(block: VDB.() -> Unit): VDB {
        return mBinding.apply(block)
    }

    /**
     * addOnContextAvailableListener 调用
     *
     * 确保[mDatabind]属性在被调用[onCreate]之前被执行。
     */
    init {
        addOnContextAvailableListener {
            mBinding.notifyChange()
        }
    }

    /**
     * 确保[binding]属性被执行并提供依赖于[contentLayoutId]视图。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam(savedInstanceState)
        initialize()
        initListener()
        initObserver()
        initImmersionBar()
    }

    /**
     * 初始化初始参数
     */
    abstract fun initParam(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    abstract fun initialize()

    /**
     * 初始化事件
     */
    abstract fun initListener()

    /**
     * 初始化观察者
     */
    abstract fun initObserver()

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        // 设置共同沉浸式样式
        ImmersionBar
            .with(this)
            .navigationBarColor(R.color.transparent)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    /**
     * 销毁防止内存泄漏
     */
    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}