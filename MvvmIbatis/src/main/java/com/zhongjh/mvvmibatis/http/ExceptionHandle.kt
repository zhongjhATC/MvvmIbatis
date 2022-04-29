package com.zhongjh.mvvmibatis.http

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * 处理 HttpException 的异常
 * 解析出中文封装进HttpException实体
 *
 * @author zhongjh
 * @date 2022/3/30
 */
object ExceptionHandle {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val SERVICE_UNAVAILABLE = 503

    /**
     * @param e Throwable异常类
     * @return 返回解析出中文后的异常类
     */
    @JvmStatic
    fun handleException(e: Throwable?): ResponseException {
        val ex: ResponseException
        return if (e is HttpException) {
            ex = ResponseException(e, Error.HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED -> ex.message = "操作未授权"
                FORBIDDEN -> ex.message = "请求被拒绝"
                NOT_FOUND -> ex.message = "资源不存在"
                REQUEST_TIMEOUT -> ex.message = "服务器执行超时"
                INTERNAL_SERVER_ERROR -> ex.message = "服务器内部错误"
                SERVICE_UNAVAILABLE -> ex.message = "服务器不可用"
                else -> ex.message = "网络错误"
            }
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {
            ex = ResponseException(e, Error.PARSE_ERROR)
            ex.message = "解析错误"
            ex
        } else if (e is ConnectException) {
            ex = ResponseException(e, Error.NET_WORD_ERROR)
            ex.message = "连接失败"
            ex
        } else if (e is SSLException) {
            ex = ResponseException(e, Error.SSL_ERROR)
            ex.message = "证书验证失败"
            ex
        } else if (e is ConnectTimeoutException) {
            ex = ResponseException(e, Error.TIMEOUT_ERROR)
            ex.message = "连接超时"
            ex
        } else if (e is SocketTimeoutException) {
            ex = ResponseException(e, Error.TIMEOUT_ERROR)
            ex.message = "连接超时"
            ex
        } else if (e is UnknownHostException) {
            ex = ResponseException(e, Error.TIMEOUT_ERROR)
            ex.message = "主机地址未知"
            ex
        } else {
            ex = ResponseException(e!!, Error.UNKNOWN)
            ex.message = "未知错误"
            ex
        }
    }

    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    internal object Error {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NET_WORD_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1006
    }
}