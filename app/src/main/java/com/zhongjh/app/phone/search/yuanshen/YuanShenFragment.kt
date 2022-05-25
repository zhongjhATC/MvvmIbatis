package com.zhongjh.app.phone.search.yuanshen

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentYuanshenBinding
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

}