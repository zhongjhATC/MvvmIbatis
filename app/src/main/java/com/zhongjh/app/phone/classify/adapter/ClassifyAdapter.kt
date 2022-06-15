package com.zhongjh.app.phone.classify.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.Classify

/**
 * 分类的适配器
 * @author zhongjh
 * @date 2022/6/15
 */
class ClassifyAdapter :
    BaseQuickAdapter<Classify, BaseViewHolder>(R.layout.item_classify) {

    private val auroraGreyColor by lazy {
        ResourcesCompat.getColor(context.resources, R.color.aurora_grey, context.theme)
    }
    private val whiteColor by lazy {
        ResourcesCompat.getColor(
            context.resources,
            com.zhongjh.mvvmibatis.R.color.white,
            context.theme
        )
    }

    override fun convert(holder: BaseViewHolder, item: Classify) {
        val tvContext = holder.getView<TextView>(R.id.tvContext)
        tvContext.text = item.title
        if (item.isCheck) {
            tvContext.setBackgroundColor(whiteColor)
        } else {
            tvContext.setBackgroundColor(auroraGreyColor)
        }
    }

    override fun setOnItemClick(v: View, position: Int) {
        super.setOnItemClick(v, position)
        // 改变ui，所有设置false，选择的一个设置为true
        for (i in 0 until data.size) {
            if (data[i].isCheck) {
                data[i].isCheck = false
                notifyItemChanged(i)
            }
        }
        data[position].isCheck = true
        notifyItemChanged(position)
    }


}