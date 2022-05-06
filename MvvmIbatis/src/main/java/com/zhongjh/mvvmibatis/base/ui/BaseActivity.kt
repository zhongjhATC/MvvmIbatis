package com.zhongjh.mvvmibatis.base.ui

import androidx.annotation.LayoutRes
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.zhongjh.mvvmibatis.base.viewmodel.BaseViewModel

/**
 *
 * @author zhongjh
 * @date 2022/4/29
 */
abstract class BaseActivity<VDB : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {

    /**
     * 初始化布局，并且生成viewDataBinding
     */
    protected val mDatabind: VDB by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, contentLayoutId, DataBindingUtil.getDefaultComponent())
    }

    /**
     * 用于绑定相关viewModel
     */
    protected inline fun binding(block: VDB.() -> Unit): VDB {
        return mDatabind.apply(block)
    }

    init {
        /**
         * addOnContextAvailableListener 调用
         * 确保 [mDatabind] 属性在被调用 [onCreate] 之前被执行。
         */
        addOnContextAvailableListener {
            mDatabind.notifyChange()
        }
    }

    /**
     * 销毁防止内存泄漏
     */
    override fun onDestroy() {
        super.onDestroy()
        mDatabind.unbind()
    }


}