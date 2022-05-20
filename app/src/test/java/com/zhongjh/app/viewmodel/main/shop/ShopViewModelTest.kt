package com.zhongjh.app.viewmodel.main.shop

import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.pavneet_singh.temp.MainCoroutineScopeRule
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.Banner
import com.zhongjh.app.phone.main.fragment.shopping.ShopViewModel
import com.zhongjh.app.utils.MockUtil
import com.zhongjh.mvvmibatis.model.State
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

/**
 *
 * @author zhongjh
 * @date 2022/5/20
 */
class ShopViewModelTest {

    private lateinit var viewModel: ShopViewModel
    private val bannerApi: BannerApi = mock()

    private val mockObserver: Observer<State<List<Banner>>> = mock()

    @Captor
    private lateinit var captor: ArgumentCaptor<State<List<Banner>>>

    @get:Rule
    val coroutinesRule = MainCoroutineScopeRule()

    @Before
    fun setup() {
        viewModel = ShopViewModel(bannerApi)
    }

//    @Test
//    fun getBanner() = runTest {
//        val mockDatas = MockUtil.mockBannerList()
//        val flow: Flow<State<List<Banner>>> = flow {
//            emit(State.Loading())
//            delay(10)
//            emit(State.Success(mockDatas))
//        }
//
//        flow.test {
//            val item = awaitItem()
//            val b = item
//        }
//
//
//    }

    @Test
    fun getBanner() {
        coroutinesRule.runBlockingTest {
            val mockDatas = MockUtil.mockBannerList()
            val mockApiDatas = MockUtil.mockApiBannerList(mockDatas)

            whenever(bannerApi.json())
                .thenReturn(mockApiDatas)

            viewModel.bannerFlow.test {
                val item = awaitItem()
                if (item is State.Loading<List<Banner>>) {
                    val a = 5
                }
                val item2 = awaitItem()
                if (item is State.Success<List<Banner>>) {
                    val a = 5
                }
            }


//            val flow: Flow<ApiEntity<List<Banner>>> = flow {
////                emit(State.Loading())
////                delay(10)
////                emit(State.Success(mockDatas))
//                emit(MockUtil.mockApiBannerList())
//            }
//            Mockito.`when`(viewModel.getBanner)
//                .thenReturn(flow)
//            val a = viewModel.b()
//            val mock: ShopViewModel = Mockito.mock(ShopViewModel::class.java)

//            Mockito.`when`(bannerApi.json())
//                .thenReturn(MockUtil.mockApiBannerList())
//            val liveData = viewModel.weatherForecast
//            liveData.observeForever(mockObserver)
//
//            Mockito.verify(mockObserver)
//                .onChanged(captor.capture()) // loading state has been received
//
//            Assert.assertEquals(true, captor.value)

        }
    }

}