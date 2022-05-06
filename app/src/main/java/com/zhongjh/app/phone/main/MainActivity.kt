package com.zhongjh.app.phone.main

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityMainBinding
import com.zhongjh.app.phone.main.viewpager.ViewPagerFragmentStateAdapter
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.utils.StatusBarUtil

/**
 *
 * @author zhongjh
 * @date 2022/5/6
 */
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mAdapter: ViewPagerFragmentStateAdapter by lazy {
        ViewPagerFragmentStateAdapter(supportFragmentManager, lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.initStatusBarHeight(mDatabind.viewPager2)
        initViewPager()
        initTabBar()
        initListener()
    }

    private fun initListener() {
        // 当ViewPager切换页面时，改变底部导航栏的状态
        mDatabind.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mDatabind.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        // 当底部导航栏切换页面时，改变ViewPager的显示
        mDatabind.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_shop -> {
                    mDatabind.viewPager2.setCurrentItem(0, true)
                }
                R.id.navigation_auction -> {
                    mDatabind.viewPager2.setCurrentItem(1, true)
                }
                R.id.navigation_fast_auction -> {
                    mDatabind.viewPager2.setCurrentItem(2, true)
                }
                R.id.navigation_exchange -> {
                    mDatabind.viewPager2.setCurrentItem(3, true)
                }
                R.id.navigation_my -> {
                    mDatabind.viewPager2.setCurrentItem(4, true)
                }
            }
            true
        }
    }

    private fun initViewPager() {
        mDatabind.viewPager2.adapter = mAdapter
        mDatabind.viewPager2.isUserInputEnabled = false
        mDatabind.viewPager2.offscreenPageLimit = 5
    }

    /**
     * 初始化tabBar
     * 必须要赋值，否则会报错奔溃
     */
    private fun initTabBar() {
    }

}