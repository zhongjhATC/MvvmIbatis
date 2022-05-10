package com.zhongjh.app.phone.main.fragment.shopping.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.Product
import com.zhongjh.app.phone.MyApplication.Companion.instance
import com.zhongjh.mvvmibatis.utils.GlideRoundTransform

/**
 * 产品的竖向适配器
 * @author zhongjh
 * @date 2022/4/2
 */
class ShopPingVerticalAdapter :
    BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_shop_vertical) {

    override fun convert(holder: BaseViewHolder, item: Product) {
        val imageView = holder.getView<ImageView>(R.id.imgProduct)

        // 显示图片
        val options = RequestOptions().placeholder(R.mipmap.ic_banner)
            .error(R.mipmap.ic_banner)
            .transform(GlideRoundTransform(2, true, true, false, false))
        Glide.with(instance)
            .load(item.pictureUrl)
            .apply(options)
            .into(imageView)
        holder.setText(R.id.tvProductName, item.name)
        holder.setText(R.id.tvPrice, "当前价:￥" + item.price)
    }

}