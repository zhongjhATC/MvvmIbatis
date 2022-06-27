package com.zhongjh.app.phone.classify.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.SubClass

/**
 * 小类适配器
 * @author zhongjh
 * @date 2022/6/27
 */
class SubClassAdapter :
    BaseQuickAdapter<SubClass, BaseViewHolder>(R.layout.item_classify) {


    override fun convert(holder: BaseViewHolder, item: SubClass) {
        val tvContext = holder.getView<TextView>(R.id.tvContext)
        tvContext.text = item.name
    }

}