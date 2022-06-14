package com.zhongjh.app.phone.classify

import androidx.lifecycle.ViewModel
import com.zhongjh.app.data.http.service.ClassifyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *
 * @author zhongjh
 * @date 2022/6/14
 */
@HiltViewModel
class ClassifyModel @Inject constructor(private val classifyApi: ClassifyApi) : ViewModel() {


}