package com.zhongjh.app.phone.search.yuanshen

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentYuanshenBinding
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product
import com.zhongjh.app.phone.main.fragment.shopping.ShopViewModel
import com.zhongjh.app.phone.search.yuanshen.adapter.YuanShenVerticalAdapter
import com.zhongjh.app.view.CustomRefreshHeader
import com.zhongjh.mvvmibatis.base.ui.BaseFragment
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * @author zhongjh
 * @date 2022/5/25
 */
@AndroidEntryPoint
class YuanShenFragment : BaseFragment<FragmentYuanshenBinding>(R.layout.fragment_yuanshen) {

    @get:VisibleForTesting
    internal val viewModel: YuanShenViewModel by viewModels()

    private var mSearchContent = ""
    private val mYuanShenVerticalAdapter = YuanShenVerticalAdapter()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initialize() {
        // 自定义刷新
        mBinding.refreshLayout.setRefreshHeader(CustomRefreshHeader(activity))
        // 初始化列表竖向列表
        mBinding.rlContent.layoutManager = GridLayoutManager(context, 2)
        mBinding.rlContent.adapter = mYuanShenVerticalAdapter
    }

    override fun initListener() {
        mBinding.refreshLayout.setOnRefreshListener {
        }
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiSearch.collect {
                when (it) {
                    is State.Success -> {
                        showProducts(it.data)
                    }
                    is State.Error -> {
                        showProductsError(it)
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * 查询数据
     * 如果查询条件没变化，并且有数据，就不再查询
     * 查询内容为空也不查询
     * @param searchContent 搜索文本
     */
    fun search(searchContent: String) {
        if (mSearchContent == searchContent && mYuanShenVerticalAdapter.data.size > 0) {
            return
        }
        if (TextUtils.isEmpty(searchContent)) {
            return
        }
        mSearchContent = searchContent
        // 请求网络数据
        viewModel.search(mSearchContent)
    }

    /**
     * 清空数据
     */
    fun clear() {
        mYuanShenVerticalAdapter.setList(null)
        mYuanShenVerticalAdapter.setDiffNewData(null)
    }

    /**
     * 显示产品数据
     */
    private fun showProducts(data: PageEntity<Product>) {
        mBinding.refreshLayout.finishRefresh()
        // 显示竖向数据
        mYuanShenVerticalAdapter.setList(data.data)
        mYuanShenVerticalAdapter.setDiffNewData(data.data)
        // 隐藏软键盘
        KeyboardUtils.hideSoftInput(activity)
    }

    /**
     * 报错数据
     */
    private fun showProductsError(data: State.Error<PageEntity<Product>>) {
        mBinding.refreshLayout.finishRefresh()
    }

}