package com.zhongjh.app.phone.main.fragment.shopping.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.app.R
import com.zhongjh.app.entity.Product
import com.zhongjh.app.phone.MyApplication.Companion.instance
import com.zhongjh.mvvmibatis.utils.GlideRoundTransform


/**
 * 产品的横向适配器
 * @author zhongjh
 * @date 2022/4/2
 */
class ShopPingHorizontalAdapter :
    BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_shop_horizontal) {

    var width = 0

    override fun convert(holder: BaseViewHolder, item: Product) {
        val imageView = holder.getView<ImageView>(R.id.imgProduct)

        // 设置每个item是屏幕的1/3 , -16 是控件的左右间距
        val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
        if (width == 0) {
            width = (ScreenUtils.getScreenWidth()) / 3
            layoutParams.width = width
        }
        if (width != layoutParams.width) {
            layoutParams.width = width
        }

        // 显示图片
        val options = RequestOptions().placeholder(R.mipmap.ic_banner)
            .error(R.mipmap.ic_banner)
            .transform(GlideRoundTransform(10))
        Glide.with(instance)
            .load(item.pictureUrl)
            .apply(options)
            .into(imageView)
        holder.setText(R.id.tvProductName, item.name)
        // 设置价格，后半红色字体
        val spannableString = SpannableString("当前价:￥" + item.price)
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            4,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.setText(R.id.tvPrice, spannableString)
    }

}