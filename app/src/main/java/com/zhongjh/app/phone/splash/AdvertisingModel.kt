package com.zhongjh.app.phone.splash

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zhongjh.mvvmibatis.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 *
 * @author zhongjh
 * @date 2022/5/18
 */
class AdvertisingModel constructor(application: Application) : BaseViewModel(application) {

    /**
     * 同意协议
     */
    private val _uiAgreement = MutableStateFlow<Boolean>(false)
    val uiAgreement: Flow<Boolean> = _uiAgreement

    fun startCountdown() {
        viewModelScope.launch {

        }
    }


}