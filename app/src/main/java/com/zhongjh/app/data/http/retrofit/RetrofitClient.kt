package com.zhongjh.app.data.http.retrofit

import com.zhongjh.mvvmibatis.http.retrofit.BaseRetrofitClient

/**
 *
 * @author zhongjh
 * @date 2022/3/30
 *
 * 实现网络请求
 */
class RetrofitClient : BaseRetrofitClient() {

    companion object {
        private var instance: RetrofitClient? = null

        fun get(): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    if (instance == null) {
                        instance = RetrofitClient()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 服务端根路径
     */
    override fun getBaseUrl(): String {
        return "https://gitee.com/zhongjh/MvvmIbatis/"
    }

    /**
     * 请求头
     */
    override fun getHeaders(): Map<String, String>? {
        return null
    }
}