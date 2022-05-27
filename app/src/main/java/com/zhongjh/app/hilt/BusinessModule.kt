package com.zhongjh.app.hilt

import com.zhongjh.app.data.db.business.SearchContentBusiness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 生成数据库的业务处理
 * @author zhongjh
 * @date 2022/5/27
 */
@Module
@InstallIn(SingletonComponent::class)
class BusinessModule {

    @Provides
    @Singleton
    fun SearchContentBusiness(): SearchContentBusiness {
        return com.zhongjh.app.data.db.business.SearchContentBusiness()
    }
}