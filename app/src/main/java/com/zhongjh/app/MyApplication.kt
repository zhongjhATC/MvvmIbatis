package com.zhongjh.app

import android.app.Activity
import com.zhongjh.mvvmibatis.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @author zhongjh
 * @date 2022/4/29
 */

@HiltAndroidApp
class MyApplication : BaseApplication() {

    companion object {
        /**
         * 必须添加这个，延迟初始化
         */
        val instance by lazy {
            BaseApplication.instance as MyApplication
        }
    }

    override fun getLauncher(): Int {
        return R.mipmap.ic_launcher
    }

    override fun getSplashActivity(): Class<out Activity> {
        return MainActivity::class.java
    }
}