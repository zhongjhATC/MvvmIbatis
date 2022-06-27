package com.zhongjh.app.phone.classify.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.SubClass

/**
 * 小类适配器
 * @author zhongjh
 * @date 2022/6/27
 */
class SubClassAdapter : BaseDelegateMultiAdapter<SubClass, BaseViewHolder>() {

    companion object {
        const val TYPE_DATA = 1
        const val TYPE_STICKY_HEAD = 2
    }

    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<SubClass>() {
            override fun getItemType(data: List<SubClass>, position: Int): Int {
                // 根据数据，返回对应类型
                val subClass = data[position]
                return if (subClass.id == -1) {
                    TYPE_STICKY_HEAD
                } else {
                    TYPE_DATA
                }
            }
        })
        getMultiTypeDelegate()
            ?.addItemType(TYPE_STICKY_HEAD, R.layout.item_classify)
            ?.addItemType(TYPE_DATA, R.layout.item_classify)
    }


    override fun convert(holder: BaseViewHolder, item: SubClass) {
        val tvContext = holder.getView<TextView>(R.id.tvContext)
        tvContext.text = item.name
    }

}