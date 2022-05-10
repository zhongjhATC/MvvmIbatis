package com.zhongjh.app.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.zhongjh.app.entity.Product

/**
 * [Product]的差异回调
 *
 * [教程](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/7-Diff.md)
 *
 * @author zhongjh
 * @date 2022/4/20
 */
class DiffProductCallback : DiffUtil.ItemCallback<Product>() {

    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return 是否同一个
     */
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return 是否同一个
     */
    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.pictureUrl == newItem.pictureUrl &&
                oldItem.price == newItem.price
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
    override fun getChangePayload(oldItem: Product, newItem: Product): Any? {
        return null
    }
}