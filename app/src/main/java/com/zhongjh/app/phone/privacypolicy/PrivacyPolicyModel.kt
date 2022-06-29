package com.zhongjh.app.phone.privacypolicy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongjh.app.data.local.MMKVLocal
import com.zhongjh.mvvmibatis.extend.launchFlow
import com.zhongjh.mvvmibatis.entity.State
import com.zhongjh.mvvmibatis.utils.SPCacheUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/5/18
 */
@HiltViewModel
class PrivacyPolicyModel @Inject constructor() : ViewModel() {

    /**
     * 同意协议
     */
    private val _uiAgreement = MutableStateFlow<State<Boolean>>(State.Empty())
    val uiAgreement: Flow<State<Boolean>> = _uiAgreement

    /**
     * 同意协议
     */
    fun agreement() {
        viewModelScope.launch {
            launchFlow(_uiAgreement) {
                // 设置不需要再运行隐私界面
                SPCacheUtil.put(MMKVLocal.IS_PRIVACY_POLICY, false)
                true
            }
        }
    }
}