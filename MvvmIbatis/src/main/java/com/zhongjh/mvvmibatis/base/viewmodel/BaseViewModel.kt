package com.zhongjh.mvvmibatis.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * 一个基类ViewModel，但是因为使用的协程等等工具都已经自动帮你清除内存泄漏，基本不用做什么封装了
 * @author zhongjh
 * @date 2022/4/29
 */
open class BaseViewModel constructor(application: Application) : AndroidViewModel(application)