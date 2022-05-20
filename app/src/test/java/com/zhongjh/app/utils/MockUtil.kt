package com.zhongjh.app.utils

import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner

/**
 * 模拟类
 * @author zhongjh
 * @date 2022/5/20
 */
object MockUtil {

    fun mockApiBannerList(value: List<Banner>): ApiEntity<List<Banner>> {
        val apiEntity = ApiEntity<List<Banner>>()
        apiEntity.data = value
        return apiEntity
    }

    fun mockBannerList() = listOf(mockBannerInfo())

    fun mockBannerInfo(): Banner {
        val banner = Banner()
        banner.id = "1"
        banner.desc = "desc"
        banner.imagePath = "imagePath"
        banner.isVisible = "isVisible"
        banner.order = "order"
        banner.title = "title"
        banner.type = "type"
        banner.url = "url"
        return banner
    }

}