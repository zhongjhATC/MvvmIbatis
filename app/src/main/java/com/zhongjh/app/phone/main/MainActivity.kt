package com.zhongjh.app.phone.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.viewpager2.widget.ViewPager2
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityMainBinding
import com.zhongjh.app.phone.main.viewpager.ViewPagerFragmentStateAdapter
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.utils.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * @author zhongjh
 * @date 2022/5/6
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @get:VisibleForTesting
    private val viewModel2 by viewModels<TestViewModel>()

    private val mAdapter: ViewPagerFragmentStateAdapter by lazy {
        ViewPagerFragmentStateAdapter(supportFragmentManager, lifecycle)
    }

    /**
     * @author zhongjh
     * @date 2022/5/6
     */
    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        // 当ViewPager切换页面时，改变底部导航栏的状态
        mBinding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        // 当底部导航栏切换页面时，改变ViewPager的显示
        mBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_shop -> {
                    mBinding.viewPager2.setCurrentItem(0, false)
                }
                R.id.navigation_auction -> {
                    mBinding.viewPager2.setCurrentItem(1, false)
                }
                R.id.navigation_fast_auction -> {
                    mBinding.viewPager2.setCurrentItem(2, false)
                }
                R.id.navigation_exchange -> {
                    mBinding.viewPager2.setCurrentItem(3, false)
                }
                R.id.navigation_my -> {
                    mBinding.viewPager2.setCurrentItem(4, false)
                }
            }
            true
        }
    }

    override fun initialize() {
        StatusBarUtil.initStatusBarHeight(mBinding.viewPager2)
        initViewPager()
    }

    private fun initViewPager() {
        mBinding.viewPager2.adapter = mAdapter
        mBinding.viewPager2.isUserInputEnabled = false
        mBinding.viewPager2.offscreenPageLimit = 5
    }

}