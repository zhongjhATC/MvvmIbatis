package com.zhongjh.app.phone.search

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivitySearchBinding
import com.zhongjh.app.diffcallback.DiffSearchHistoryCallback
import com.zhongjh.app.entity.SearchConditions
import com.zhongjh.app.entity.SearchType
import com.zhongjh.app.entity.db.SearchContent
import com.zhongjh.app.phone.main.fragment.shopping.ShopPingFragment
import com.zhongjh.app.phone.search.adapter.SearchHistoryAdapter
import com.zhongjh.app.phone.search.adapter.SearchNavigatorAdapter
import com.zhongjh.app.phone.search.adapter.SearchViewPagerAdapter
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.extend.onClick
import com.zhongjh.mvvmibatis.entity.State
import dagger.hilt.android.AndroidEntryPoint
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

    /**
     * 当前的搜索的条件
     */
    private val mSearchConditions = SearchConditions()

    /**
     * 用于记录索引，点击搜索时随时更新该position
     */
    private var mViewPagerPosition = 0

    private lateinit var mCommonNavigatorAdapter: SearchNavigatorAdapter
    private val mSearchHistoryAdapter = SearchHistoryAdapter()
    private val mSearchViewPagerAdapter: SearchViewPagerAdapter by lazy {
        SearchViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initialize() {
        initMagicIndicator()
        initRlSearchHistory()
        getSearchHistory()
    }

    override fun initListener() {
        mBinding.imgBack.onClick {
            this@SearchActivity.finish()
        }
        mBinding.etSearch.doAfterTextChanged {
            if (TextUtils.isEmpty(it)) {
                switchShowSearchView()
            }
            viewModel.search(it.toString())
        }
        mBinding.tvSearch.onClick {
            if (!TextUtils.isEmpty(mBinding.etSearch.text)) {
                mSearchConditions.content = mBinding.etSearch.text.toString()
                mSearchViewPagerAdapter.search(
                    mViewPagerPosition,
                    mBinding.etSearch.text.toString()
                )
                showDataListView()
            }
        }
        // 删除历史
        mBinding.imgDeleteHistory.onClick {
            MaterialDialog(this).show {
                message(R.string.whether_to_clear_the_search_history)
                positiveButton(R.string.agree) {
                    viewModel.deleteAllSearch()
                }
                negativeButton(R.string.cancel) {
                    this.dismiss()
                }
            }
        }
        // 点击搜索历史事件
        mSearchHistoryAdapter.setOnItemClickListener { _, _, position ->
            mBinding.etSearch.setText(mSearchHistoryAdapter.data[position].content)
        }
    }

    override fun initObserver() {
        // 传递给子Fragment进行处理搜索
        lifecycleScope.launchWhenStarted {
            viewModel.uiSearch.collect {
                when (it) {
                    is State.Success -> {
                        searchText(it.data)
                    }
                    else -> {}
                }
            }
        }

        // 获取到搜索记录
        lifecycleScope.launchWhenStarted {
            viewModel.uiSearchHistory.collect {
                when (it) {
                    is State.Success -> {
                        initSearchContentsState(it.data)
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
            .navigationBarColor(com.zhongjh.mvvmibatis.R.color.white)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    /**
     * 根据搜索文本进行搜索，传递给子Fragment进行处理搜索
     * @param searchContent 搜索文本
     */
    private fun searchText(searchContent: String) {
        mSearchViewPagerAdapter.search(mViewPagerPosition, searchContent)
        mSearchConditions.content = searchContent
        showDataListView()
    }

    /**
     * 初始化tab
     */
    private fun initMagicIndicator() {
        val listTitle: MutableList<String> = ArrayList()
        val listFragments: MutableList<Fragment> = ArrayList()
        // 实例化所有枚举信息
        val searchTypes = SearchType.values()
        for (searchType in searchTypes) {
            listTitle.add(searchType.value)
            listFragments.add(ShopPingFragment())
        }

        val commonNavigator = CommonNavigator(this)
        mCommonNavigatorAdapter = SearchNavigatorAdapter(listTitle, mBinding.viewPager2)
        commonNavigator.adapter = mCommonNavigatorAdapter
        commonNavigator.leftPadding = UIUtil.dip2px(this, 10.0)
        commonNavigator.rightPadding = UIUtil.dip2px(this, 15.0)
        mBinding.magicIndicator.navigator = commonNavigator
        initViewPager(listFragments)
    }

    /**
     * 初始化搜索记录历史列表
     */
    private fun initRlSearchHistory() {
        val manager: FlexboxLayoutManager =
            object : FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        mBinding.rvSearchHistory.layoutManager = manager
        mBinding.rvSearchHistory.adapter = mSearchHistoryAdapter
        mSearchHistoryAdapter.setDiffCallback(DiffSearchHistoryCallback())
    }

    /**
     * 初始化viewPager
     */
    private fun initViewPager(listFragments: MutableList<Fragment>) {
        mBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                mBinding.magicIndicator.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.magicIndicator.onPageSelected(position)
                // 根据索引构建一个enum
                val searchType = SearchType.match(position)
                searchType?.let {
                    mSearchConditions.type = it
                }
                mSearchViewPagerAdapter.search(position, mSearchConditions.content)
                mViewPagerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                mBinding.magicIndicator.onPageScrollStateChanged(state)
            }
        })
        mBinding.viewPager2.adapter = mSearchViewPagerAdapter
        mBinding.viewPager2.offscreenPageLimit = listFragments.size
    }

    /**
     * 获取搜索历史记录
     */
    private fun getSearchHistory() {
        viewModel.getSearchHistory()
    }

    /**
     * 初始化搜索历史
     */
    private fun initSearchContentsState(data: MutableList<SearchContent>) {
        mSearchHistoryAdapter.setDiffNewData(data)
    }

    /**
     * 显示搜索历史列表，隐藏产品列表
     */
    private fun switchShowSearchView() {
        mBinding.groupSearchHistory.visibility = View.VISIBLE
        mBinding.viewPager2.visibility = View.GONE
        mSearchViewPagerAdapter.clear(mViewPagerPosition)
    }

    /**
     * 显示产品列表，隐藏搜索历史列表
     */
    private fun showDataListView() {
        mBinding.groupSearchHistory.visibility = View.GONE
        mBinding.viewPager2.visibility = View.VISIBLE
    }
}