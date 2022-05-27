package com.zhongjh.app.data.http.service

import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 商品Api
 * @author zhongjh
 * @date 2022/5/27
 */
interface ProductApi {

    @GET("raw/master/server/api/products{position}.json")
    suspend fun products(@Path("position") position: Int): ApiEntity<PageEntity<Product>>

}