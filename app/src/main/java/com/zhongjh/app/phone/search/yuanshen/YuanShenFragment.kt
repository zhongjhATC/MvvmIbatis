package com.zhongjh.app.phone.search.yuanshen

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentYuanshenBinding
import com.zhongjh.app.phone.main.fragment.shopping.ShopViewModel
import com.zhongjh.app.phone.search.yuanshen.adapter.YuanShenVerticalAdapter
import com.zhongjh.app.view.CustomRefreshHeader
import com.zhongjh.mvvmibatis.base.ui.BaseFragment
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
    }

    /**
     * 查询数据
     * 如果查询条件没变化，并且有数据，就不再查询
     * @param searchContent 搜索文本
     */
    fun search(searchContent: String) {
        if (mSearchContent == searchContent && mYuanShenVerticalAdapter.data.size > 0) {
            return
        }
        mSearchContent = searchContent
//        // 请求网络数据
//        mSearchObserver.onNext(searchContent)
    }

    /**
     * 清空数据
     */
    fun clear() {
        mYuanShenVerticalAdapter.setList(null)
        mYuanShenVerticalAdapter.notifyDataSetChanged()
    }

}