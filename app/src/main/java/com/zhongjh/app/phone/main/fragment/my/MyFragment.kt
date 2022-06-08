package com.zhongjh.app.phone.main.fragment.my

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.FragmentMyBinding
import com.zhongjh.app.phone.MyApplication
import com.zhongjh.mvvmibatis.base.ui.BaseFragment
import com.zhongjh.mvvmibatis.utils.GlideCircleBorderTransform
import dagger.hilt.android.AndroidEntryPoint

/**
 * 我的
 * @author zhongjh
 * @date 2022/5/30
 */
@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    override fun initParam(savedInstanceState: Bundle?) {

    }

    override fun initialize() {
        Glide.with(MyApplication.instance)
            .load("https://gitee.com/zhongjh/MvvmIbatis/raw/master/server/images/11.jpg")
            .apply(
                RequestOptions().error(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.placeholder_res,
                        MyApplication.instance.theme
                    )
                )
                    .placeholder(R.mipmap.ic_launcher).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(GlideCircleBorderTransform(3F, com.zhongjh.mvvmibatis.R.color.white))
            )
            .into(mBinding.imgPhoto)
    }

    override fun initListener() {

    }

    override fun initObserver() {
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar
            .with(this)
            .titleBar(mBinding.clMain)
            .statusBarColorTransformEnable(false)
            .navigationBarColor(com.zhongjh.mvvmibatis.R.color.white)
            .navigationBarDarkIcon(true)
            .init()
    }


}