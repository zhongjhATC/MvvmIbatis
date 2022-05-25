package com.zhongjh.app.phone.search

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivitySearchBinding
import com.zhongjh.app.entity.SearchType
import com.zhongjh.app.phone.main.fragment.shopping.ShopPingFragment
import com.zhongjh.app.phone.main.fragment.shopping.ShopViewModel
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.extend.onClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * 搜索的Activity
 * @author zhongjh
 * @date 2022/5/25
 */
@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    @get:VisibleForTesting
    internal val viewModel: SearchViewModel by viewModels()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initialize() {
        initMagicIndicator()
//        initRlSearchHistory()


    }


    override fun initListener() {
        mBinding.imgBack.onClick {
            this@SearchActivity.finish()
        }
        mBinding.etSearch.doAfterTextChanged {
            viewModel.search(it.toString())
//            // 往流里写数据
//            _etState.value = (it ?: "").toString()
        }
    }

    override fun initObserver() {
    }


    /**
     * 初始化tab
     */
    private fun initMagicIndicator() {
//        val listTitle: MutableList<String> = ArrayList()
//        val listFragments: MutableList<Fragment> = ArrayList()
//        // 实例化所有枚举信息
//        val searchTypes = SearchType.values()
//        for (searchType in searchTypes) {
//            listTitle.add(searchType.value)
//            listFragments.add(ShopPingFragment())
//        }
//
//        val commonNavigator = CommonNavigator(this)
//        mCommonNavigatorAdapter = SearchNavigatorAdapter(listTitle, viewPager2)
//        commonNavigator.adapter = mCommonNavigatorAdapter
//        commonNavigator.leftPadding = UIUtil.dip2px(this, 10.0)
//        commonNavigator.rightPadding = UIUtil.dip2px(this, 15.0)
//        magicIndicator.navigator = commonNavigator
//        initViewPager(listFragments)
    }

}