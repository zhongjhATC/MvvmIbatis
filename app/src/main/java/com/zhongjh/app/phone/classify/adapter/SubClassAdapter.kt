package com.zhongjh.app.phone.classify.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.SubClass
import com.zhongjh.mvvmibatis.base.BaseApplication

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
            ?.addItemType(TYPE_STICKY_HEAD, R.layout.item_subclass_title)
            ?.addItemType(TYPE_DATA, R.layout.item_subclass)
    }

    override fun convert(holder: BaseViewHolder, item: SubClass) {
        if (item.id == -1) {
            // 粘性头部赋值
            val tvContext = holder.getView<TextView>(R.id.tvContext)
            tvContext.text = item.firstLetter.toString()
        } else {
            // item赋值
            val tvSubClassName = holder.getView<TextView>(R.id.tvSubClassName)
            tvSubClassName.text = item.name
            val imgSubClass = holder.getView<ImageView>(R.id.imgSubClass)
            Glide.with(BaseApplication.instance).load(item.image)
                .apply(RequestOptions().placeholder(R.mipmap.ic_banner))
                .centerCrop()
                .into(imgSubClass)
        }
    }

    /**
     * 获取跟首字母一样的索引
     * @param letters 首字母
     * @return 索引
     */
    fun getLettersFirstPosition(letters: String): Int {
        for (i in 0 until data.size) {
            data[i].firstLetter?.let {
                if (data[i].id == -1 && it.toString() == letters) {
                    return i
                }
            }
        }
        return -1
    }
}