package com.zhongjh.app.phone.main.fragment.shopping

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentShoppingBinding
import com.zhongjh.app.diffcallback.DiffProductCallback
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingHorizontalAdapter
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingVerticalAdapter
import com.zhongjh.app.view.CustomRefreshHeader
import com.zhongjh.mvvmibatis.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * 一个商城的Fragment
 * @author zhongjh
 * @date 2022/5/7
 */
@AndroidEntryPoint
class ShopPingFragment : BaseFragment<FragmentShoppingBinding>(R.layout.fragment_shopping) {

    private val mTag = ShopPingFragment::class.qualifiedName
    private var mShopPingHorizontalAdapter = ShopPingHorizontalAdapter()
    private var mShopPingVerticalAdapter = ShopPingVerticalAdapter()

    @get:VisibleForTesting
    internal val viewModel: ShopViewModel by viewModels()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
        // 添加生命周期观察者
        mBinding.banner.addBannerLifecycleObserver(this).indicator = CircleIndicator(context)

        // 自定义刷新
        mBinding.refreshLayout.setRefreshHeader(CustomRefreshHeader(activity))

        // 初始化列表横向列表
        val ms = LinearLayoutManager(context)
        ms.orientation = LinearLayoutManager.HORIZONTAL
        mBinding.rlContentHorizontal.layoutManager = ms
        mBinding.rlContentHorizontal.adapter = mShopPingHorizontalAdapter
        mShopPingHorizontalAdapter.setDiffCallback(DiffProductCallback())

        // 初始化列表竖向列表
        mBinding.rlContent.layoutManager = GridLayoutManager(context, 2)
        mBinding.rlContent.adapter = mShopPingVerticalAdapter
        mShopPingVerticalAdapter.setDiffCallback(DiffProductCallback())
    }


}