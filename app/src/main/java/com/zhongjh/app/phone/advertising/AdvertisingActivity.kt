package com.zhongjh.app.phone.advertising

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityAdvertisingBinding
import com.zhongjh.app.phone.main.MainActivity
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.utils.FlowUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

/**
 * 这是一个倒计时的Activity,一般用于提供广告
 * https://dev.to/aniketsmk/kotlin-flow-implementing-an-android-timer-ieo
 * @author zhongjh
 * @date 2022/5/5
 */
@AndroidEntryPoint
class AdvertisingActivity :
    BaseActivity<ActivityAdvertisingBinding>(R.layout.activity_advertising) {

    @get:VisibleForTesting
    internal val viewModel: AdvertisingModel by viewModels()

    private var mCountdownJob: Job? = null

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        mBinding.viewCountdown.ivCountdown.setOnClickListener {
            completeCountdown()
        }
    }

    override fun initialize() {
        countDownCoroutines()
    }

    override fun initObserver() {

    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .titleBar(mBinding.vTop, false)
            .transparentBar()
            .init()
    }

    /**
     * 开始了倒计时
     */
    private fun countDownCoroutines() {
        mCountdownJob = FlowUtil.countDownCoroutines(
            5, lifecycleScope,
            onTick = {
                showCountdownSeconds(it)
            },
            onStart = {
                showAdvertising()
            },
            onFinish = {
                completeCountdown()
            }
        )
    }

    /**
     * 显示倒计时时间
     */
    private fun showCountdownSeconds(second: Int) {
        mBinding.viewCountdown.tvCountdownTime.visibility = View.VISIBLE
        mBinding.viewCountdown.tvCountdownTime.text = second.toString()
    }

    /**
     * 倒计时结束
     */
    private fun completeCountdown() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * 显示广告图
     */
    private fun showAdvertising() {
        Glide.with(this)
            .load("https://gitee.com/zhongjh/MvvmIbatis/raw/master/server/images/splash.png")
            .placeholder(R.drawable.ic_launcher)
            .into(mBinding.imgSplash)
    }


}