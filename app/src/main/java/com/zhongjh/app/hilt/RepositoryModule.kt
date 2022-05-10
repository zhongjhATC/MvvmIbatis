package com.zhongjh.app.hilt

import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.phone.main.fragment.shopping.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Repository的依赖注入
 * @author zhongjh
 * @date 2022/5/9
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideShopRepository(bannerApi: BannerApi): ShopRepository {
        return ShopRepository(bannerApi)
    }


}