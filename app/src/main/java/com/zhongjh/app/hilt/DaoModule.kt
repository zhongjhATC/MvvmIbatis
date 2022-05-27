package com.zhongjh.app.hilt

import com.zhongjh.app.data.db.dao.SearchContentDao
import com.zhongjh.app.phone.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 生成数据库的处理
 * @author zhongjh
 * @date 2022/5/27
 */
@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun SearchContentDao(): SearchContentDao {
        return MyApplication.instance.daoSession.searchContentDao
    }
}