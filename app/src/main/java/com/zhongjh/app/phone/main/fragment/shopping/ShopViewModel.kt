package com.zhongjh.app.phone.main.fragment.shopping

import androidx.lifecycle.*
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.data.http.service.ProductApi
import com.zhongjh.app.entity.*
import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * https://github.com/shounakmulay/KotlinFlowTest/tree/part2-end
 * @author zhongjh
 * @date 2022/5/7
 */
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val bannerApi: BannerApi
) : ViewModel() {

    var flowBanner: Flow<State<List<Banner>>> =
        launchFlow {
            bannerApi.json()
        }

    var flowShopHome: Flow<State<ShopHome>> =
        launchFlow {
            // 面板广告
            val banners = bannerApi.json()
            // 面板广告2,只是为了更加贴合真实情况加的一个
            val banners2 = bannerApi.json()
            // 正在拍卖的产品
            val productsIn = ProductApi.getProducts(0)
            // 商城显示的产品
            val products = ProductApi.getProducts(0)
            val apiEntity = ApiEntity<ShopHome>()
            val shopHome = ShopHome()
            shopHome.banners = banners.data
            shopHome.banners2 = banners2.data
            shopHome.productsIn = productsIn.data?.data
            shopHome.products = products.data
            apiEntity.data = shopHome
            apiEntity
        }

}