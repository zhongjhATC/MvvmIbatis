package com.zhongjh.app.phone.main.fragment.shopping

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/7
 */
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val shopFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    private val shopBanner = shopFetchingIndex.flatMapLatest {
        shopRepository.getBanners()
    }
}