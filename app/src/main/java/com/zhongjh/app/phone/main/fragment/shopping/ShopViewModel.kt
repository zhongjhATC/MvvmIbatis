package com.zhongjh.app.phone.main.fragment.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.entity.Banner
import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/7
 */
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val bannerApi: BannerApi
) : ViewModel() {

    var weatherForecast: LiveData<State<List<Banner>>> =
        launchFlow {
            bannerApi.json()
        }.asLiveData()



}