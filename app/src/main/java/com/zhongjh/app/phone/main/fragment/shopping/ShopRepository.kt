//package com.zhongjh.app.phone.main.fragment.shopping
//
//import androidx.annotation.WorkerThread
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.asFlow
//import androidx.lifecycle.asLiveData
//import com.zhongjh.app.data.http.service.BannerApi
//import com.zhongjh.app.entity.ApiEntity
//import com.zhongjh.app.entity.Banner
//import com.zhongjh.mvvmibatis.base.IApiEntity
//import com.zhongjh.mvvmibatis.extend.launchFlow
//import com.zhongjh.mvvmibatis.model.State
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.flow.*
//import javax.inject.Inject
//
///**
// * 跟api关联的类
// * @author zhongjh
// * @date 2022/5/7
// */
//class ShopRepository @Inject constructor(
//    private val bannerApi: BannerApi
//) {
//
//    var weatherForecast: LiveData<State<IApiEntity<List<Banner>>>> =
//        launchFlow {
//            bannerApi.json()
//        }.asLiveData()
//
//}