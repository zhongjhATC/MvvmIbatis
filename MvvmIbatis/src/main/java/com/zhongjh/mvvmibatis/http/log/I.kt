package com.zhongjh.mvvmibatis.http.log

import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level
import java.util.logging.Logger

/**
 * 所有网络日志打印方式以 INFO 和 WARNING方式打印
 *
 * @author zhongjh
 * @date 2022/3/30
 */
internal class I private constructor() {

    companion object {
        fun log(type: Int, tag: String, msg: String) {
            val logger = Logger.getLogger(tag)
            if (type == INFO) {
                logger.log(Level.INFO, msg)
            } else {
                logger.log(Level.WARNING, msg)
            }
        }
    }

    init {
        throw UnsupportedOperationException()
    }
}