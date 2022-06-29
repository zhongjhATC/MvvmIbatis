package com.zhongjh.app.hilt

import com.zhongjh.app.data.http.retrofit.RetrofitClient
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.data.http.service.ClassifyApi
import com.zhongjh.app.data.http.service.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 生成网络api
 * @author zhongjh
 * @date 2022/5/10
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun BannerApi(): BannerApi {
        return RetrofitClient.get().create(BannerApi::class.java)
    }

    @Provides
    @Singleton
    fun ProductApi(): ProductApi {
        return RetrofitClient.get().create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun ClassifyApi(): ClassifyApi {
        return RetrofitClient.get().create(ClassifyApi::class.java)
    }
}