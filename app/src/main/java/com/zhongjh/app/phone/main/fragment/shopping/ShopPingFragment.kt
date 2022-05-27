package com.zhongjh.app.phone.main.fragment.shopping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentShoppingBinding
import com.zhongjh.app.diffcallback.DiffProductCallback
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product
import com.zhongjh.app.entity.ShopHome
import com.zhongjh.app.extend.showError
import com.zhongjh.app.extend.showLoading
import com.zhongjh.app.extend.showSuccess
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingBannerAdapter
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingHorizontalAdapter
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingVerticalAdapter
import com.zhongjh.app.phone.search.SearchActivity
import com.zhongjh.app.state.LottieWaitingState
import com.zhongjh.app.view.CustomRefreshHeader
import com.zhongjh.mvvmibatis.base.ui.BaseFragment
import com.zhongjh.mvvmibatis.extend.*
import com.zhongjh.mvvmibatis.model.State
import com.zhongjh.mvvmibatis.utils.ToastUtils
import com.zy.multistatepage.MultiStatePage
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.LoadingState
import dagger.hilt.android.AndroidEntryPoint

/**
 * 一个商城的Fragment
 * @author zhongjh
 * @date 2022/5/7
 */
@AndroidEntryPoint
class ShopPingFragment : BaseFragment<FragmentShoppingBinding>(R.layout.fragment_shopping) {

    @get:VisibleForTesting
    internal val viewModel: ShopViewModel by viewModels()

    private var mShopPingHorizontalAdapter = ShopPingHorizontalAdapter()
    private var mShopPingVerticalAdapter = ShopPingVerticalAdapter()

    private val container by lazy {
        MultiStatePage.bindMultiState(mBinding.clMain)
    }

    /**
     * 竖型列表的当前页码
     */
    private var mVerticalPage = 0

    override fun initParam(savedInstanceState: Bundle?) {
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

        // 初始刷新首页
        getShopHome()
        container.show<LottieWaitingState>()
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiShopHome.collect {
                when (it) {
                    is State.Success -> {
                        showShopHome(it.data)
                    }
                    is State.Error -> {
                        showShopHomeError(it)
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiLoadNextProduct.collect {
                when (it) {
                    is State.Success -> {
                        loadNextProduct(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun initListener() {
        mBinding.vSearchTouch.setOnClickListener {
            // 打开搜索界面
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        mBinding.refreshLayout.setOnRefreshListener {
            getShopHome()
        }
        mBinding.refreshLayout.setOnLoadMoreListener {
            loadNextProduct()
        }
    }

    /**
     * 获取首页数据
     */
    private fun getShopHome() = viewModel.getShopHome()

    /**
     * 加载下一页
     */
    private fun loadNextProduct() {
        mVerticalPage++
        viewModel.loadNextProduct(mVerticalPage)
    }

    /**
     * 刷新首页数据
     */
    private fun showShopHome(data: ShopHome) {
        container.showSuccess()
        mBinding.refreshLayout.finishRefresh()
        mBinding.refreshLayout.setEnableRefresh(true)
        mBinding.refreshLayout.setEnableLoadMore(true)
        // 显示Banner
        if (data.banners != null) {
            mBinding.banner.adapter = ShopPingBannerAdapter(data.banners!!)
        }
        // 显示横向数据
        mShopPingHorizontalAdapter.setDiffNewData(data.productsIn)
        // 显示竖向数据
        data.products?.let { pageEntity ->
            checkNextPage(pageEntity)
            mShopPingVerticalAdapter.setDiffNewData(pageEntity.data)
        }
        resetShopHome()
    }

    /**
     * 访问首页数据错误
     */
    private fun showShopHomeError(state: State.Error<ShopHome>) {
        state.throwable.message?.let { it1 -> ToastUtils.showLong(it1) }
        mBinding.refreshLayout.finishRefresh()
        container.showError {
            it.retry {
                container.show<LottieWaitingState>()
                getShopHome()
            }
        }
    }

    /**
     * 加载下一页
     */
    private fun loadNextProduct(data: PageEntity<Product>) {
        data.let { pageEntity ->
            checkNextPage(pageEntity)
            pageEntity.data?.let { products ->
                mShopPingVerticalAdapter.addData(products)
            }
        }
        mBinding.refreshLayout.finishLoadMore()
    }

    /**
     * 检查是否能下一页
     */
    private fun checkNextPage(pageEntity: PageEntity<Product>) {
        if (mVerticalPage >= pageEntity.pages - 1) {
            // 关闭下一页，页码已经最大
            mBinding.refreshLayout.setEnableLoadMore(false)
        }
    }

    /**
     * 重置基础数据
     */
    private fun resetShopHome() {
        mVerticalPage = 0
        mBinding.refreshLayout.setEnableLoadMore(true)
    }

}