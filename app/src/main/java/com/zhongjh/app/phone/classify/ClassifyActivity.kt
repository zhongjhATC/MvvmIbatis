package com.zhongjh.app.phone.classify

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityClassifyBinding
import com.zhongjh.app.diffcallback.DiffClassifyCallback
import com.zhongjh.app.entity.Classify
import com.zhongjh.app.phone.classify.adapter.ClassifyAdapter
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.entity.State
import dagger.hilt.android.AndroidEntryPoint

/**
 * 分类
 * @author zhongjh
 * @date 2022/6/14
 */
@AndroidEntryPoint
class ClassifyActivity :
    BaseActivity<ActivityClassifyBinding>(R.layout.activity_classify) {

    @get:VisibleForTesting
    internal val viewModel: ClassifyModel by viewModels()

    private var mClassifyAdapter = ClassifyAdapter()

    override fun initParam(savedInstanceState: Bundle?) {

    }

    override fun initialize() {
        // 初始化列表竖向列表
        mBinding.rvContent.layoutManager = LinearLayoutManager(this)
        mBinding.rvContent.adapter = mClassifyAdapter
        mClassifyAdapter.setDiffCallback(DiffClassifyCallback())

        viewModel.getClassify()
    }

    override fun initListener() {
        mClassifyAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiClassify.collect {
                when (it) {
                    is State.Success -> {
                        showClassify(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun initImmersionBar() {
        ImmersionBar
            .with(this)
            .titleBarMarginTop(mBinding.vTop)
            .statusBarColorTransformEnable(false)
            .navigationBarColor(R.color.aurora_grey)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }


    /**
     * 刷新分类数据
     */
    private fun showClassify(data: MutableList<Classify>) {
        // 显示分类数据
        mClassifyAdapter.setDiffNewData(data)
    }
}