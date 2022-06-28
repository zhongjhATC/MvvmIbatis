package com.zhongjh.stickyhead

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.math.min

/**
 * 配合粘性的分割线
 * @param stickyHeadType 头部类型，通过它判断是否头部
 * @param stickyHeadContainer 外面控件的头部容器
 * @author zhongjh
 * @date 2022/6/16
 */
class StickyItemDecoration(
    private val stickyHeadContainer: StickyHeadContainer,
    private val stickyHeadType: Int
) : RecyclerView.ItemDecoration() {

    private var mAdapter: RecyclerView.Adapter<*>? = null

    private var mRecyclerView : RecyclerView? = null

    /**
     * 第一个可见的item的position
     */
    private var mFirstVisiblePosition = 0

    /**
     * 需要粘性的头部标签索引
     */
    private var mStickyHeadPosition = 0

    /**
     * 九宫列表的第一行可见索引，九宫格的第一行不仅仅只有一条数据
     */
    private var mInto: IntArray? = null

    /**
     * 是否启用粘性头部
     */
    private var mEnableStickyHead = true

    private var mOnStickyChangeListener: OnStickyChangeListener? = null

    fun setOnStickyChangeListener(onStickyChangeListener: OnStickyChangeListener?) {
        mOnStickyChangeListener = onStickyChangeListener
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        checkCache(parent)

        if (mAdapter == null) {
            // checkCache赋值mAdapter有值之后才能继续下一步
            return
        }

        // 计算StickyHead位置
        mRecyclerView = parent
        calculateStickyHeadPosition()

        /**
         * 满足以下条件：
         * 1. 粘性头部启动
         * 2. 粘性头部索引有效时
         * 3. 当可见的索引>=粘性头部索引时
         *
         * 触发：
         * 通知外层的offset的转变
         */
        if (mEnableStickyHead && mStickyHeadPosition != -1 && mFirstVisiblePosition >= mStickyHeadPosition) {
            // 根据x,y获取RecyclerView对应的item
            val belowView = parent.findChildViewUnder(
                (c.width / 2).toFloat(),
                stickyHeadContainer.getChildHeight() + 0.01f
            )
            // 通知 外部的粘性控件 刷新 最新的粘性头部索引
            stickyHeadContainer.onDataChange(mStickyHeadPosition)
            var offset = 0
            belowView?.let {
                offset = if (isStickyHead(parent, it) && it.top > 0) {
                    it.top - stickyHeadContainer.getChildHeight()
                } else {
                    0
                }
            }
            // 通知外层的offset的转变
            mOnStickyChangeListener?.onScrollable(offset)
            stickyHeadContainer.scrollChild(offset)
            stickyHeadContainer.visibility = View.VISIBLE
        } else {
            mOnStickyChangeListener?.onInVisible()
            stickyHeadContainer.reset()
            stickyHeadContainer.visibility = View.INVISIBLE
        }
    }

    /**
     * 检查缓存
     *
     * @param parent [RecyclerView]控件
     */
    private fun checkCache(parent: RecyclerView) {
        val adapter = parent.adapter
        if (mAdapter !== adapter) {
            mAdapter = adapter
            // 适配器为null或者不同，清空缓存
            mStickyHeadPosition = -1
            mAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    reset()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    reset()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    reset()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    reset()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    reset()
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    reset()
                }
            })
        }
    }

    /**
     * 计算StickyHead位置
     * @param parent [RecyclerView]控件
     */
    fun calculateStickyHeadPosition() {
        val layoutManager = mRecyclerView?.layoutManager
        layoutManager?.let {
            // 获取第一个可见的item位置
            mFirstVisiblePosition = findFirstVisiblePosition(it)

            // 获取标签的位置
            val stickyHeadPosition: Int = findStickyHeadPosition(mFirstVisiblePosition)

            // 标签位置有效，并且和缓存的位置不同
            if (stickyHeadPosition >= 0 && mStickyHeadPosition != stickyHeadPosition) {
                // 赋值
                mStickyHeadPosition = stickyHeadPosition
            }
        }
    }

    /**
     * 找出第一个可见的Item的位置
     *
     * @param layoutManager
     * @return 索引位置
     */
    private fun findFirstVisiblePosition(layoutManager: RecyclerView.LayoutManager): Int {
        var firstVisiblePosition = 0
        when (layoutManager) {
            is GridLayoutManager -> {
                // 显示界面的第一个可见位置
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                // 显示界面的第一个可见位置
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                // 显示九宫的第一个可见的行的多个索引
                mInto = IntArray(layoutManager.spanCount)
                layoutManager.findFirstVisibleItemPositions(mInto)
                // 跟最大值比较，最终结果取最小的索引
                mInto?.let {
                    firstVisiblePosition = Int.MAX_VALUE
                    for (pos in it) {
                        firstVisiblePosition = min(pos, firstVisiblePosition)
                    }
                }
            }
        }
        return firstVisiblePosition
    }

    /**
     * 从传入位置递减找出标签的位置
     *
     * @param formPosition 第一个可见的位置
     * @return
     */
    private fun findStickyHeadPosition(formPosition: Int): Int {
        for (position in formPosition downTo 0) {
            mAdapter?.let {
                // 递减，只要查到位置是头部标签，立即返回该位置
                val type = it.getItemViewType(position)
                if (isStickyHeadType(type)) {
                    return position
                }
            }
        }
        return -1
    }

    /**
     * 判断是否为头部类型
     *
     * @param type
     * @return
     */
    private fun isStickyHeadType(type: Int): Boolean {
        return stickyHeadType == type
    }

    /**
     * 是否启用粘性头部
     */
    fun enableStickyHead(enableStickyHead: Boolean) {
        mEnableStickyHead = enableStickyHead
        if (!mEnableStickyHead) {
            stickyHeadContainer.visibility = View.INVISIBLE
        }
    }

    /**
     * 根据该view判断是否头部类型
     *
     * @param parent [RecyclerView]
     * @param view [RecyclerView]的某个item view
     * @return 是否头部类型
     */
    private fun isStickyHead(parent: RecyclerView, view: View): Boolean {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return false
        }
        mAdapter?.let {
            val type = it.getItemViewType(position)
            return isStickyHeadType(type)
        }
        return false
    }

    /**
     * 重置
     */
    private fun reset() {
        stickyHeadContainer.reset()
    }

}