package com.zhongjh.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.zhongjh.app.data.http.retrofit.RetrofitClient
import com.zhongjh.app.data.http.service.BannerApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

/**
 * 用于捕获Android退出
 * @author zhongjh
 * @date 2022/6/13
 */
class KeepAliveService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        send()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        send()
        super.onTaskRemoved(rootIntent)
    }

    private fun send() {
        GlobalScope.launch {
            flow {
                emit(RetrofitClient.get().create(BannerApi::class.java).json())
            }.collect {
                Log.d("TAG",it.errorCode + " 成功")
            }
        }


    }

}