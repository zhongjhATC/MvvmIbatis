package com.zhongjh.app.hilt

import com.zhongjh.app.data.http.retrofit.RetrofitClient
import com.zhongjh.app.data.http.service.BannerApi
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

}