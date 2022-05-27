package com.zhongjh.app.phone.search.yuanshen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.db.business.SearchContentBusiness
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.data.http.service.ProductApi
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product
import com.zhongjh.mvvmibatis.extend.launchApiFlow
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/27
 */
@HiltViewModel
class YuanShenViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    /**
     * 根据搜索文本获取的数据
     */
    private val _uiSearch = MutableStateFlow<State<PageEntity<Product>>>(State.Empty())
    val uiSearch: StateFlow<State<PageEntity<Product>>> = _uiSearch

    /**
     * 搜索产品数据
     * @param searchContent 搜索文本
     */
    fun search(searchContent: String) {
        viewModelScope.launch {
            launchApiFlow(_uiSearch) {
                productApi.products(0)
            }
        }
    }

}