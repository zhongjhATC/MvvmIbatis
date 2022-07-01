package com.zhongjh.app.phone.classify

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zhongjh.app.R
import com.zhongjh.app.databinding.ActivityClassifyBinding
import com.zhongjh.app.diffcallback.DiffClassifyCallback
import com.zhongjh.app.diffcallback.SubClassCallback
import com.zhongjh.app.entity.Classify
import com.zhongjh.app.entity.SubClass
import com.zhongjh.app.phone.classify.adapter.ClassifyAdapter
import com.zhongjh.app.phone.classify.adapter.SubClassAdapter
import com.zhongjh.app.phone.classify.adapter.SubClassAdapter.Companion.TYPE_STICKY_HEAD
import com.zhongjh.mvvmibatis.base.ui.BaseActivity
import com.zhongjh.mvvmibatis.entity.State
import com.zhongjh.sidebarview.OnSideBarViewListener
import com.zhongjh.stickyhead.StickyHeadContainer
import com.zhongjh.stickyhead.StickyItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * 分类
 * @author zhongjh
 * @date 2022/6/14
 */
@AndroidEntryPoint
class ClassifyActivity :
    BaseActivity<ActivityClassifyBinding>(R.layout.activity_classify) {

    companion object {
        const val SUB_CLASS_SPAN_COUNT = 3
    }

    @get:VisibleForTesting
    internal val viewModel: ClassifyModel by viewModels()

    private var mClassifyAdapter = ClassifyAdapter()
    private var mSubClassAdapter = SubClassAdapter()

    private lateinit var mStickyItemDecoration: StickyItemDecoration

    private val mSpanSizeLookup: SpanSizeLookup = object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            // 当id为-1的时候即是行头，其他为正常数据
            return if (mSubClassAdapter.data[position].id == -1) {
                SUB_CLASS_SPAN_COUNT
            } else {
                1
            }
        }
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initialize() {
        // 初始化 分类列表-竖向列表
        mBinding.rvContent.layoutManager = LinearLayoutManager(this)
        mBinding.rvContent.adapter = mClassifyAdapter
        mClassifyAdapter.setDiffCallback(DiffClassifyCallback())

        // 初始化 小类列表-格子列表
        val gridLayoutManager = GridLayoutManager(this, SUB_CLASS_SPAN_COUNT)
        gridLayoutManager.spanSizeLookup = mSpanSizeLookup
        mBinding.rvSubclass.layoutManager = gridLayoutManager
        mBinding.rvSubclass.adapter = mSubClassAdapter
        mSubClassAdapter.setDiffCallback(SubClassCallback())

        viewModel.getClassify()
        viewModel.getSubClass(1)
    }

    override fun initListener() {
        // 大类点击事件
        mClassifyAdapter.setOnItemClickListener { _, _, position ->
            viewModel.getSubClass(mClassifyAdapter.getItem(position).id)
        }

        // 更新头部值
        mBinding.stickyHeadContainer.setDataCallback(object : StickyHeadContainer.DataCallback {
            override fun onDataChange(pos: Int) {
                mBinding.includeSubclassTitle.tvContext.text = mSubClassAdapter.data[pos].name
            }
        })

        // 侧边栏事件
        mBinding.sideBarView.setOnLetterChangeListener(object : OnSideBarViewListener {
            override fun onSideBarViewStart(letter: String) {
                mBinding.tvSideBarTips.text = letter
                mBinding.tvSideBarTips.visibility = View.VISIBLE
                scrollRecyclerViewSubclass(letter)
            }

            override fun onSideBarViewChange(letter: String) {
                mBinding.tvSideBarTips.text = letter
                mBinding.tvSideBarTips.visibility = View.VISIBLE
                scrollRecyclerViewSubclass(letter)
            }

            override fun onSideBarViewEnd() {
                mBinding.tvSideBarTips.text = ""
                mBinding.tvSideBarTips.visibility = View.GONE
            }
        })
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

        lifecycleScope.launchWhenStarted {
            viewModel.uiSubClass.collect {
                when (it) {
                    is State.Success -> {
                        showSubClass(it.data)
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

    /**
     * 刷新小类数据
     */
    private fun showSubClass(data: MutableList<SubClass>) {
        // 显示分类数据
        mSubClassAdapter.setDiffNewData(data)
        mStickyItemDecoration = StickyItemDecoration(mBinding.stickyHeadContainer, TYPE_STICKY_HEAD)
        mBinding.rvSubclass.addItemDecoration(mStickyItemDecoration)
    }

    /**
     * 根据首字母滑动RecyclerViewSubclass
     * @param letter 首字母
     */
    private fun scrollRecyclerViewSubclass(letter: String) {
        val position: Int = mSubClassAdapter.getLettersFirstPosition(letter)
        if (position != -1) {
            val manager = mBinding.rvSubclass.layoutManager as GridLayoutManager
            manager.scrollToPositionWithOffset(position, 0)
        }
    }
}