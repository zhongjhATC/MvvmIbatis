package com.zhongjh.app.data.http.service

import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Classify
import com.zhongjh.app.entity.PageEntity
import retrofit2.http.GET

/**
 * 分类
 * @author zhongjh
 * @date 2022/6/14
 */
interface ClassifyApi {

    @GET("raw/master/server/api/class.json")
    suspend fun classify(): ApiEntity<MutableList<Classify>>
}