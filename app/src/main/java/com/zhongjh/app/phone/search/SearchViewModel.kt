package com.zhongjh.app.phone.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.mvvmibatis.extend.launchFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/25
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bannerApi: BannerApi
) : ViewModel() {

    private val searchTextFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchTextFlow
                // 限流，1000毫秒
                .debounce(1000)
                // 值如果相等就不触发
                .distinctUntilChanged()
                .filter {
                    // 空文本过滤掉
                    it.isNotBlank()
                }.collect {
                    // 订阅数据
                    Log.i("123", it)
                }
        }
    }

    /**
     * 进行搜索
     */
    fun search(searchText: String) {
        searchTextFlow.value = searchText
    }

}