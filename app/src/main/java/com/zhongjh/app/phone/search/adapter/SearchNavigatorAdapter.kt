package com.zhongjh.app.phone.search.adapter

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import com.zhongjh.app.view.ScaleTransitionPagerTitleView
import androidx.core.content.res.ResourcesCompat
import com.zhongjh.app.R
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

/**
 * 指标适配器
 *
 * @author zhongjh
 * @date 2022/4/07
 */
class SearchNavigatorAdapter(var mListTitle: List<String>?, var mViewPager2: ViewPager2) :
    CommonNavigatorAdapter() {
    var mDisable = false
    override fun getCount(): Int {
        return if (mListTitle == null) 0 else mListTitle!!.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
        simplePagerTitleView.normalColor =
            ResourcesCompat.getColor(context.resources, R.color.grey, context.theme)
        simplePagerTitleView.selectedColor =
            ResourcesCompat.getColor(context.resources, R.color.grey, context.theme)
        simplePagerTitleView.textSize = 16f
        simplePagerTitleView.text = mListTitle!![index]
        val padding = UIUtil.dip2px(context, 7.0)
        simplePagerTitleView.setPadding(padding, 0, padding, 0)
        simplePagerTitleView.setOnClickListener {
            // 判断是否禁用状态才决定是否移动viewpager
            if (!mDisable) {
                mViewPager2.currentItem = index
            }
        }
        return simplePagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        val linePagerIndicator = LinePagerIndicator(context)
        linePagerIndicator.mode = LinePagerIndicator.MODE_EXACTLY
        linePagerIndicator.setColors(
            ResourcesCompat.getColor(
                context.resources,
                R.color.grey,
                context.theme
            )
        )
        // 指示器线条动画
        linePagerIndicator.startInterpolator = AccelerateInterpolator()
        linePagerIndicator.endInterpolator = DecelerateInterpolator(1.6f)
        // 指示器线条宽高及圆角弧度
        linePagerIndicator.lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
        linePagerIndicator.lineWidth = UIUtil.dip2px(context, 25.0).toFloat()
        linePagerIndicator.roundRadius = 3f
        return linePagerIndicator
    }

    fun enable() {
        mDisable = false
    }

    fun disable() {
        mDisable = true
    }
}