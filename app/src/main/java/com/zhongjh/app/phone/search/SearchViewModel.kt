package com.zhongjh.app.phone.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.db.business.SearchContentBusiness
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val bannerApi: BannerApi,
    private val searchContentBusiness: SearchContentBusiness,
) : ViewModel() {

    /**
     * ui端传递的搜索文本
     */
    private val searchTextFlow = MutableStateFlow("")

    /**
     * 根据搜索文本获取的数据
     */
    private val _uiSearch = MutableStateFlow<State<String>>(State.Empty())

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
                    // 进行文本搜索
                    requestSearch(it)
                }
        }
    }

    /**
     * 前端输入文本进行搜索
     */
    fun search(searchText: String) {
        searchTextFlow.value = searchText
    }

    /**
     * 存储搜索文本到历史记录，通知ui搜索该文本
     */
    private fun requestSearch(searchText: String) {
        viewModelScope.launch {
            launchFlow(_uiSearch, suspend {
                // 添加到数据库
                searchContentBusiness.addSearchContents(searchText)
                searchText
            })
        }
    }

}