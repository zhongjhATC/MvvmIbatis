package com.zhongjh.app.phone.search.adapter;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.zhongjh.app.R;
import com.zhongjh.app.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * 指标适配器
 *
 * @author zhongjh
 * @date 2022/4/07
 */
public class SearchNavigatorAdapter extends CommonNavigatorAdapter {

    List<String> mListTitle;
    ViewPager2 mViewPager2;
    boolean mDisable;

    public SearchNavigatorAdapter(List<String> mListTitle, ViewPager2 viewPager2) {
        this.mListTitle = mListTitle;
        this.mViewPager2 = viewPager2;
    }

    @Override
    public int getCount() {
        return mListTitle == null ? 0 : mListTitle.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(ResourcesCompat.getColor(context.getResources(), R.color.grey, context.getTheme()));
        simplePagerTitleView.setSelectedColor(ResourcesCompat.getColor(context.getResources(), R.color.grey, context.getTheme()));
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setText(mListTitle.get(index));
        int padding = UIUtil.dip2px(context, 7);
        simplePagerTitleView.setPadding(padding, 0, padding, 0);
        simplePagerTitleView.setOnClickListener(v -> {
            // 判断是否禁用状态才决定是否移动viewpager
            if (!mDisable) {
                mViewPager2.setCurrentItem(index);
            }
        });

        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setColors(ResourcesCompat.getColor(context.getResources(), R.color.grey, context.getTheme()));
        // 指示器线条动画
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
        // 指示器线条宽高及圆角弧度
        linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3));
        linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 25));
        linePagerIndicator.setRoundRadius(3);
        return linePagerIndicator;
    }

    public void enable() {
        mDisable = false;
    }

    public void disable() {
        mDisable = true;
    }

}
