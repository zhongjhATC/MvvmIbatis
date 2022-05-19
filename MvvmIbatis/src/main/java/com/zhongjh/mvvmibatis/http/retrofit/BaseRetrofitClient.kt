package com.zhongjh.mvvmibatis.http.retrofit

import com.blankj.utilcode.util.Utils
import com.zhongjh.mvvmibatis.BuildConfig
import com.zhongjh.mvvmibatis.http.HttpsUtils
import com.zhongjh.mvvmibatis.http.cookie.CookieJarImpl
import com.zhongjh.mvvmibatis.http.cookie.store.PersistentCookieStore
import com.zhongjh.mvvmibatis.http.interceptor.BaseInterceptor
import com.zhongjh.mvvmibatis.http.interceptor.CacheInterceptor
import com.zhongjh.mvvmibatis.http.log.Level
import com.zhongjh.mvvmibatis.http.log.LoggingInterceptor
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * retrofit的基类，子类只要关心提供相关接口即可
 * @author zhongjh
 * @date 2022/5/18
 */
abstract class BaseRetrofitClient {

    /**
     * retrofit本身
     */
    private val retrofit: Retrofit

    /**
     * 超时时间
     */
    private val defaultTimeout = 20

    /**
     * 缓存
     */
    private var httpCacheDirectory: File = File(Utils.getApp().cacheDir, "http_cache")

    abstract fun getBaseUrl() : String

    abstract fun getHeaders(): Map<String, String>?

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }

    init {
        // 设置缓存 10M
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(httpCacheDirectory, cacheSize)

        val okHttpClientBuild: OkHttpClient.Builder = OkHttpClient.Builder()
            .cache(cache)
            .cookieJar(CookieJarImpl(PersistentCookieStore(Utils.getApp())))
            .addInterceptor(BaseInterceptor(getHeaders()))
            .addInterceptor(CacheInterceptor(Utils.getApp()))
            .addInterceptor(
                LoggingInterceptor.Builder()
                    // 是否开启日志打印
                    .loggable(BuildConfig.DEBUG)
                    // 打印的等级
                    .setLevel(Level.BASIC)
                    // 打印类型
                    .log(Platform.INFO)
                    // request的Tag
                    .request("Request")
                    // Response的Tag
                    .response("Response")
                    // 添加打印头, 注意 key 和 value 都不能是中文
                    .addHeader("log-header", "I am the log request header.")
                    .build()
            )
            .retryOnConnectionFailure(false)
            .connectTimeout(defaultTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout.toLong(), TimeUnit.SECONDS)
            // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
            .connectionPool(
                ConnectionPool(
                    8,
                    15,
                    TimeUnit.SECONDS
                )
            )
        val sslParams = HttpsUtils.sslSocketFactory
        if (sslParams.trustManager != null) {
            okHttpClientBuild.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager!!)
        }
        retrofit = Retrofit.Builder()
            .client(okHttpClientBuild.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getBaseUrl())
            .build()
    }

}