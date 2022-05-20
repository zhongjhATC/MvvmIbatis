package com.zhongjh.app.phone.main.fragment.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    var bannerFlow = launchFlow {
//        bannerApi.json()
        mockApiBannerList(mockBannerList())
    }

    var getBanner: LiveData<State<List<Banner>>> =
        bannerFlow.asLiveData()

    val countFlow = mainRepository
        .count3Flow()
        .flowOn(dispatcherProvider.default)

    val doubleCountFlow = countFlow.map {
        delay(200)
        it * 2
    }.flowOn(dispatcherProvider.io)

    val doubleCountSharedFlow =
        doubleCountFlow.shareIn(viewModelScope, sharingStrategyProvider.eagerly)


    fun mockApiBannerList(value: List<Banner>): ApiEntity<List<Banner>> {
        val apiEntity = ApiEntity<List<Banner>>()
        apiEntity.data = value
        return apiEntity
    }

    fun mockBannerList() = listOf(mockBannerInfo())

    fun mockBannerInfo(): Banner {
        val banner = Banner()
        banner.id = "1"
        banner.desc = "desc"
        banner.imagePath = "imagePath"
        banner.isVisible = "isVisible"
        banner.order = "order"
        banner.title = "title"
        banner.type = "type"
        banner.url = "url"
        return banner
    }


}