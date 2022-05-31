package com.zhongjh.app.phone.main.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zhongjh.app.phone.main.fragment.my.MyFragment;
import com.zhongjh.app.phone.main.fragment.shopping.ShopPingFragment;
import com.zhongjh.app.phone.main.ui.BlankFragment;

/**
 * main的ViewPager适配器
 *
 * @author zhongjh
 * @date 2022/3/23
 */
public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ShopPingFragment();
            case 1:
                return new BlankFragment();
            case 2:
                return new BlankFragment();
            case 3:
                return new BlankFragment();
            case 4:
                return new MyFragment();
            default:
                return new BlankFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
