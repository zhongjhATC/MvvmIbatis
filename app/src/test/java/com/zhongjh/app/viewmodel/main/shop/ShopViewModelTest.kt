package com.zhongjh.app.viewmodel.main.shop

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zhongjh.app.MainCoroutinesRule
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.phone.main.fragment.shopping.ShopViewModel
import com.zhongjh.app.utils.MockUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

/**
 *
 * @author zhongjh
 * @date 2022/5/20
 */
@ExperimentalTime
class ShopViewModelTest {

    private lateinit var viewModel: ShopViewModel
    private val bannerApi: BannerApi = mock()

    @get:Rule
    val coroutineScope = MainCoroutinesRule()

    @Before
    fun setup() {
        viewModel = ShopViewModel(bannerApi)
    }

    @Test
    fun getBanner() = runTest {
        val mockDatas = MockUtil.mockBannerList()
        val mockApiDatas = MockUtil.mockApiBannerList(mockDatas)
        whenever(bannerApi.json()).doReturn(mockApiDatas)
        val doubleCountSharedFlow3 =
            viewModel.flowBanner.shareIn(viewModel.viewModelScope, SharingStarted.Lazily)

        doubleCountSharedFlow3.test {
            val item = awaitItem()
            Assert.assertEquals(item.javaClass.simpleName, "Loading")
            val item2 = awaitItem()
            Assert.assertEquals(item2.javaClass.simpleName, "Success")
        }
    }

    fun flowShopHome() = runTest {
        val mockDatas = MockUtil.mockBannerList()
        val mockApiDatas = MockUtil.mockApiBannerList(mockDatas)
    }

}