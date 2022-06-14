package com.zhongjh.mvvmibatis.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * 一个基类Fragment
 *
 * 初始化[mBinding],并且负责生命周期中的销毁
 *
 * 按照顺序拟定了代码规范可重写以下方法 [initParam]、[initListener]、[initialize]
 * 1. 跟Activity差不多的初始化，但缺少 initImmersionBar 方法，因为Fragment只适合在onResume初始化ImmersionBar
 *
 * @author zhongjh
 * @date 2022/5/7
 */
abstract class BaseFragment<VDB : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : Fragment() {

    /**
     * 此接口是在编译期间生成的，以包含所有使用的实例BindingAdapters的getters
     */
    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    /**
     * 提供不可变[binding]属性的支持字段。
     */
    private var _binding: VDB? = null

    /**
     * [binding]将在onCreateView中初始化。依赖于[contentLayoutId]视图。
     */
    protected val mBinding: VDB
        get() = checkNotNull(_binding) {
            "Fragment $this binding 不能在onCreateView()之前或onDestroyView()之后被访问  "
        }

    /**
     * 在lambda对[binding]进行一些操作，例如赋值viewModel
     * @param block lambda块将与[binding]一起执行。
     * @return 一个扩展ViewDataBinding的泛型类，由DataBinding在编译时生成。
     */
    protected inline fun binding(block: VDB.() -> Unit): VDB {
        return mBinding.apply(block)
    }


    /**
     * 确保[binding]属性被执行并提供依赖于[contentLayoutId]视图。
     */
    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false, bindingComponent)
        initParam(savedInstanceState)
        initialize()
        initListener()
        initObserver()
        return mBinding.root
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
     * 破坏[_binding]支持属性以防止泄漏引用Context的[ViewDataBinding]。
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }


}