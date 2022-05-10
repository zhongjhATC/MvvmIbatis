package com.zhongjh.app.phone.main.fragment.shopping.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerAdapter
import com.zhongjh.app.R
import com.zhongjh.app.entity.Banner
import com.zhongjh.app.phone.main.fragment.shopping.adapter.ShopPingBannerAdapter.BannerViewHolder
import com.zhongjh.mvvmibatis.base.BaseApplication

/**
 * banner的Adapter
 * @author zhongjh
 * @date 2022/3/29
 */
class ShopPingBannerAdapter(banners: List<Banner>) :
    BannerAdapter<Banner, BannerViewHolder>(banners) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // 创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
        val imageView = ImageView(parent.context)
        // 注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(
        imageView
    )

    override fun onBindView(holder: BannerViewHolder, data: Banner, position: Int, size: Int) {
        Glide.with(BaseApplication.instance).load(data.imagePath)
            .apply(RequestOptions().placeholder(R.mipmap.ic_banner))
            .centerCrop()
            .into(holder.imageView)
    }

}