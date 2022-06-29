package com.zhongjh.app.phone.main.fragment.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.data.http.service.ProductApi
import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product
import com.zhongjh.app.entity.ShopHome
import com.zhongjh.mvvmibatis.extend.launchApiFlow
import com.zhongjh.mvvmibatis.entity.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * https://github.com/shounakmulay/KotlinFlowTest/tree/part2-end
 * @author zhongjh
 * @date 2022/5/7
 */
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val bannerApi: BannerApi,
    private val productApi: ProductApi
) : ViewModel() {

    /**
     * 获取首页数据
     */
    private val _uiShopHome = MutableStateFlow<State<ShopHome>>(State.Empty())
    val uiShopHome: StateFlow<State<ShopHome>> = _uiShopHome

    /**
     * 获取下一页数据
     */
    private val _uiLoadNextProduct = MutableStateFlow<State<PageEntity<Product>>>(State.Empty())
    val uiLoadNextProduct: StateFlow<State<PageEntity<Product>>> = _uiLoadNextProduct

    /**
     * 首页数据
     */
    fun getShopHome() {
        viewModelScope.launch {
            launchApiFlow(_uiShopHome) {
                // 面板广告
                val banners = bannerApi.json()
                // 面板广告2,只是为了更加贴合真实情况加的一个
                val banners2 = bannerApi.json()
                // 正在拍卖的产品
                val productsIn = productApi.products(0)
                // 商城显示的产品
                val products = productApi.products(0)
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
    }

    /**
     * 获取产品的下一页
     */
    fun loadNextProduct(page: Int) {
        viewModelScope.launch {
            launchApiFlow(_uiLoadNextProduct) {
                productApi.products(page)
            }
        }
    }

//    var flowBanner: Flow<State<List<Banner>>> =
//        launchFlow {
//            bannerApi.json()
//        }
}
