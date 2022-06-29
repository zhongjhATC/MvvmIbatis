package com.zhongjh.mvvmibatis.entity

import com.zhongjh.mvvmibatis.http.log.LoggingInterceptor

/**
 * 日志实体
 * @author zhongjh
 * @date 2022/6/29
 */
class LogEntity {
    lateinit var builder: LoggingInterceptor.Builder
    var chainMs: Long = 0
    var isSuccessful: Boolean = false
    var code: Int = 0
    lateinit var headers: String
    lateinit var bodyString: String
    lateinit var segments: List<String>
}