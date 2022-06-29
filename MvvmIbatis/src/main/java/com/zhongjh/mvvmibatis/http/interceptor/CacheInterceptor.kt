package com.zhongjh.mvvmibatis.http.interceptor

import android.app.Application
import com.zhongjh.mvvmibatis.utils.NetworkUtil.isNetworkAvailable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 无网络状态下智能读取缓存的拦截器
 *
 * @author zhongjh
 * @date 2022/3/30
 */
class CacheInterceptor(private val application: Application) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        return if (isNetworkAvailable(application)) {
            val response: Response = chain.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            // 读取缓存信息
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
            val response: Response = chain.proceed(request)
            // set cache times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}