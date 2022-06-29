package com.zhongjh.app.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.zhongjh.app.entity.Classify

/**
 * [Classify]的差异回调
 *
 * [教程](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/7-Diff.md)
 *
 * @author zhongjh
 * @date 2022/6/15
 */
class DiffClassifyCallback : DiffUtil.ItemCallback<Classify>() {

    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return 是否同一个
     */
    override fun areItemsTheSame(oldItem: Classify, newItem: Classify): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return 是否同一个
     */
    override fun areContentsTheSame(oldItem: Classify, newItem: Classify): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.imagePath == newItem.imagePath &&
                oldItem.isCheck == newItem.isCheck
    }

    /**
     * 可选实现
     * 如果需要精确修改某一个view中的内容，请实现此方法。
     * 如果不实现此方法，或者返回null，将会直接刷新整个item。
     *
     * @param oldItem Old data
     * @param newItem New data
     * @return Payload info. if return null, the entire item will be refreshed.
     */
    override fun getChangePayload(oldItem: Classify, newItem: Classify): Any? {
        return null
    }
}