package com.zhongjh.app.phone.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 *
 * @author zhongjh
 * @date 2022/5/23
 */
class TestViewModel(
    private val bannerApi: BannerApi,
    private val mainRepository: TestRepositoryImpl,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val sharingStrategyProvider: SharingStrategyProvider
) : ViewModel() {

    val countFlow = mainRepository
        .count3Flow()

    val doubleCountFlow = countFlow.map {
        delay(200)
        it * 2
    }

    val doubleCountSharedFlow =
        doubleCountFlow.shareIn(viewModelScope, sharingStrategyProvider.eagerly)


    /**-- 面板的 --*/
    var getBanner: LiveData<State<List<Banner>>> =
        launchFlow {
            bannerApi.json()
        }.asLiveData()

    val countFlow2: Flow<State<List<Banner>>> = launchFlow {
        bannerApi.json()
    }

    val doubleCountSharedFlow3 =
        countFlow2.shareIn(viewModelScope, sharingStrategyProvider.eagerly)


}