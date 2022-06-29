package com.zhongjh.app.data.http.service

import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Classify
import com.zhongjh.app.entity.SubClass
import retrofit2.http.GET

/**
 * 分类
 * @author zhongjh
 * @date 2022/6/14
 */
interface ClassifyApi {

    @GET("raw/master/server/api/class.json")
    suspend fun classify(): ApiEntity<MutableList<Classify>>

    @GET("raw/master/server/api/subclass.json")
    suspend fun subclass(): ApiEntity<MutableList<SubClass>>

    @GET("raw/master/server/api/subclass2.json")
    suspend fun subclass2(): ApiEntity<MutableList<SubClass>>
}