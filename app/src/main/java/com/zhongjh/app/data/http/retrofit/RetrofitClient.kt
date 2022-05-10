package com.zhongjh.app.data.http.retrofit

import com.blankj.utilcode.util.Utils
import com.zhongjh.app.BuildConfig
import com.zhongjh.mvvmibatis.http.HttpsUtils.sslSocketFactory
import com.zhongjh.mvvmibatis.http.cookie.CookieJarImpl
import com.zhongjh.mvvmibatis.http.cookie.store.PersistentCookieStore
import com.zhongjh.mvvmibatis.http.interceptor.BaseInterceptor
import com.zhongjh.mvvmibatis.http.interceptor.CacheInterceptor
import com.zhongjh.mvvmibatis.http.log.Level
import com.zhongjh.mvvmibatis.http.log.LoggingInterceptor
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform.Companion.INFO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @author zhongjh
 * @date 2022/3/30
 *
 * 实现网络请求
 *
 * @param url     服务端根路径
 * @param headers 请求头
 */
class RetrofitClient private constructor(
    url: String = "https://www.wanandroid.com/",
    headers: Map<String, String>? = null
) {
    /**
     * retrofit本身
     */
    private val retrofit: Retrofit

    /**
     * 缓存
     */
    private var httpCacheDirectory: File = File(Utils.getApp().cacheDir, "http_cache")

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

    companion object {
        /**
         * 超时时间
         */
        private const val DEFAULT_TIMEOUT = 20

        private var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }

        fun get(): RetrofitClient {
            return instance!!
        }
    }


    init {
        // 设置缓存 10M
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(httpCacheDirectory, cacheSize)

        val okHttpClientBuild: OkHttpClient.Builder = OkHttpClient.Builder()
            .cache(cache)
            .cookieJar(CookieJarImpl(PersistentCookieStore(Utils.getApp())))
            .addInterceptor(BaseInterceptor(headers))
            .addInterceptor(CacheInterceptor(Utils.getApp()))
            .addInterceptor(
                LoggingInterceptor.Builder()
                    // 是否开启日志打印
                    .loggable(BuildConfig.DEBUG)
                    // 打印的等级
                    .setLevel(Level.BASIC)
                    // 打印类型
                    .log(INFO)
                    // request的Tag
                    .request("Request")
                    // Response的Tag
                    .response("Response")
                    // 添加打印头, 注意 key 和 value 都不能是中文
                    .addHeader("log-header", "I am the log request header.")
                    .build()
            )
            .retryOnConnectionFailure(false)
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
            .connectionPool(
                ConnectionPool(
                    8,
                    15,
                    TimeUnit.SECONDS
                )
            )
        val sslParams = sslSocketFactory
        if (sslParams.trustManager != null) {
            okHttpClientBuild.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager!!)
        }
        retrofit = Retrofit.Builder()
            .client(okHttpClientBuild.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }
}