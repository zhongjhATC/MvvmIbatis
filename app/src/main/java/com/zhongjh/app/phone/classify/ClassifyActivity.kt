package com.zhongjh.app.phone.classify

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityAdvertisingBinding
import com.zhongjh.app.databinding.ActivityClassifyBinding
import com.zhongjh.app.diffcallback.DiffProductCallback
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 分类
 * @author zhongjh
 * @date 2022/6/14
 */
@AndroidEntryPoint
class ClassifyActivity :
    BaseActivity<ActivityClassifyBinding>(R.layout.activity_classify) {

    override fun initParam(savedInstanceState: Bundle?) {

    }

    override fun initialize() {
        // 初始化列表竖向列表
        mBinding.rvContent.layoutManager = GridLayoutManager(this, 2)
        mBinding.rvContent.adapter = mShopPingVerticalAdapter
        mShopPingVerticalAdapter.setDiffCallback(DiffProductCallback())
    }

    override fun initListener() {

    }

    override fun initObserver() {

    }

    override fun initImmersionBar() {
        ImmersionBar
            .with(this)
            .titleBarMarginTop(mBinding.vTop)
            .statusBarColorTransformEnable(false)
            .navigationBarColor(com.zhongjh.mvvmibatis.R.color.white)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }
}