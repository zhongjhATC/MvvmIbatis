package com.zhongjh.app.phone

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zhongjh.app.BuildConfig.DEBUG
import com.zhongjh.app.R
import com.zhongjh.app.data.db.MySQLiteOpenHelper
import com.zhongjh.app.data.db.dao.DaoMaster
import com.zhongjh.app.data.db.dao.DaoSession
import com.zhongjh.app.phone.main.MainActivity
import com.zhongjh.mvvmibatis.base.BaseApplication
import com.zhongjh.mvvmibatis.utils.DynamicTimeFormat
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @author zhongjh
 * @date 2022/4/29
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {

    /**
     * 数据库初始化
     */
    val daoSession: DaoSession by lazy {
        if (DEBUG) {
            val helper = MySQLiteOpenHelper(
                this, "mvi-db",
                null
            )
            DaoMaster(helper.writableDb).newSession()
        } else {
            val helper = MySQLiteOpenHelper(
                this, "mvi-db",
                null
            )
            DaoMaster(helper.getEncryptedWritableDb("databasePasswordKey")).newSession()
        }
    }

    companion object {
        /**
         * 必须添加这个，延迟初始化
         */
        val instance by lazy {
            BaseApplication.instance as MyApplication
        }
    }

    /**
     * 全局初始化
     *
     * 同意隐私协议后再调用该方法
     */
    override fun init() {
        super.init()
        initSmartRefresh()
    }

    /**
     * 捕获异常后显示的错误图标
     */
    override fun getLauncher(): Int {
        return R.mipmap.ic_launcher
    }

    override fun getSplashActivity(): Class<out Activity> {
        return MainActivity::class.java
    }

    private fun initSmartRefresh() {
        // 启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        // 设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { _: Context?, layout: RefreshLayout ->
            // 全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
            layout.setEnableScrollContentWhenRefreshed(true)
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.black)
            layout.setFooterMaxDragRate(4.0f)
            layout.setFooterHeight(45f)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout ->
            // 全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setEnableHeaderTranslationContent(true)
            ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, _: RefreshLayout? ->
            ClassicsFooter(
                context
            )
        }
    }
}