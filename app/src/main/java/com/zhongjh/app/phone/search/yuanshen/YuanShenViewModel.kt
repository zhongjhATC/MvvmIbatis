package com.zhongjh.app.phone.search.yuanshen

import androidx.lifecycle.ViewModel
import com.zhongjh.app.data.db.business.SearchContentBusiness
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.mvvmibatis.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/27
 */
@HiltViewModel
class YuanShenViewModel  @Inject constructor() : ViewModel() {

    /**
     * 根据搜索文本获取的数据
     */
    private val _uiSearch = MutableStateFlow<State<String>>(State.Empty())
    val uiSearch: StateFlow<State<String>> = _uiSearch

}