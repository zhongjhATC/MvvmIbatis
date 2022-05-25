package com.zhongjh.app.phone.search.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zhongjh.app.entity.SearchType
import com.zhongjh.app.phone.search.yuanshen.YuanShenFragment

/**
 * SearchActivity的ViewPager的适配器
 *
 * @author zhongjh
 * @date 2022/4/07
 */
class SearchViewPagerAdapter(private val fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mTag = SearchViewPagerAdapter::class.java.simpleName

    override fun createFragment(position: Int): Fragment {
        Log.d(mTag,"createFragment")
        return YuanShenFragment()
    }

    override fun getItemCount(): Int {
        return SearchType.values().size
    }

    /**
     * 该事件初始化时会调用，谨慎使用免得影响性能
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 切换到某个Fragment的时候，进行查询
     * 如果已经查询过了，则不再查询
     * 如果传值是“”，也不查询
     *
     * @param position 查询的索引列表
     * @param
     */
    fun search(position: Int,searchContent: String ) {
        val yuanShenFragment = getPageFragment(position.toLong()) as? YuanShenFragment
//        yuanShenFragment?.search(searchContent)
    }

    /**
     * 清空数据
     *
     * @param position 查询的索引列表
     */
    fun clear(position: Int) {
        val yuanShenFragment = getPageFragment(position.toLong()) as? YuanShenFragment
//        yuanShenFragment?.clear()
    }

    private fun getPageFragment(id: Long): Fragment? {
        return fragmentManager.findFragmentByTag("f$id")
    }



}