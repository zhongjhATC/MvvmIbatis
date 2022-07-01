package com.zhongjh.sidebarview

/**
 * SideBarView的相关事件
 * @author zhongjh
 * @date 2022/7/1
 */
interface OnSideBarViewListener {

    fun onSideBarViewStart(letter: String)

    fun onSideBarViewChange(letter: String)

    fun onSideBarViewEnd()

}