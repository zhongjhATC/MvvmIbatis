package com.zhongjh.app.phone.classify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.http.service.ClassifyApi
import com.zhongjh.app.entity.Classify
import com.zhongjh.mvvmibatis.entity.State
import com.zhongjh.mvvmibatis.extend.launchApiFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/6/14
 */
@HiltViewModel
class ClassifyModel @Inject constructor(private val classifyApi: ClassifyApi) : ViewModel() {

    /**
     * 获取分类数据
     */
    private val _uiClassify = MutableStateFlow<State<MutableList<Classify>>>(State.Empty())
    val uiClassify: StateFlow<State<MutableList<Classify>>> = _uiClassify

    /**
     * 分类数据
     */
    fun getClassify() {
        viewModelScope.launch {
            launchApiFlow(_uiClassify) {
                // 分类数据
                val classifies = classifyApi.classify()
                classifies.data?.get(0)?.isCheck = true
                classifies
            }
        }
    }

}