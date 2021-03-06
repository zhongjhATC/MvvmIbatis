package com.zhongjh.app.data.http.service

import retrofit2.http.GET
import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner

/**
 * @author zhongjh
 * @date 2022/3/30
 * 面板api
 */
interface BannerApi {

    @GET("raw/master/server/api/banner.json")
    suspend fun json(): ApiEntity<List<Banner>>
}