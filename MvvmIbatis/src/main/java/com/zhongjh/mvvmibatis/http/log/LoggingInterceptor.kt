package com.zhongjh.mvvmibatis.http.log

import android.text.TextUtils
import com.zhongjh.mvvmibatis.entity.LogEntity
import com.zhongjh.mvvmibatis.http.log.Printer.Companion.getJsonString
import com.zhongjh.mvvmibatis.http.log.Printer.Companion.printFileRequest
import com.zhongjh.mvvmibatis.http.log.Printer.Companion.printFileResponse
import com.zhongjh.mvvmibatis.http.log.Printer.Companion.printJsonRequest
import com.zhongjh.mvvmibatis.http.log.Printer.Companion.printJsonResponse
import okhttp3.*
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.Companion.INFO
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by zhongjh on 2021/3/25.
 */
class LoggingInterceptor private constructor(private val builder: Builder) : Interceptor {

    private val isDebug: Boolean

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (builder.headers.size > 0) {
            val headers = request.headers
            val names = headers.names()
            val iterator = names.iterator()
            val requestBuilder: Request.Builder = request.newBuilder()
            requestBuilder.headers(builder.headers)
            while (iterator.hasNext()) {
                val name = iterator.next()
                headers[name]?.let {
                    requestBuilder.addHeader(name, it)
                }
            }
            request = requestBuilder.build()
        }
        if (!isDebug || builder.level === Level.NONE) {
            return chain.proceed(request)
        }
        val requestBody = request.body
        var rContentType: MediaType? = null
        if (requestBody != null) {
            rContentType = request.body!!.contentType()
        }
        var rSubtype: String? = null
        if (rContentType != null) {
            rSubtype = rContentType.subtype
        }
        if (rSubtype != null && (rSubtype.contains("json")
                    || rSubtype.contains("xml")
                    || rSubtype.contains("plain")
                    || rSubtype.contains("html"))
        ) {
            printJsonRequest(builder, request)
        } else {
            printFileRequest(builder, request)
        }
        val st = System.nanoTime()
        val response: Response = chain.proceed(request)
        val segmentList = request.url.encodedPathSegments
        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val header = response.headers.toString()
        val code = response.code
        val isSuccessful = response.isSuccessful
        val responseBody = response.body
        val contentType = responseBody!!.contentType()
        var subtype: String? = null
        val body: ResponseBody
        if (contentType != null) {
            subtype = contentType.subtype
        }
        if (subtype != null && (subtype.contains("json")
                    || subtype.contains("xml")
                    || subtype.contains("plain")
                    || subtype.contains("html"))
        ) {
            val bodyString = responseBody.string()
            val bodyJson = getJsonString(bodyString)
            printJsonResponse(builder, chainMs, isSuccessful, code, header, bodyJson, segmentList)
            body = ResponseBody.create(contentType, bodyString)
        } else {
            val logEntity = LogEntity()
            logEntity.builder = builder
            logEntity.chainMs = chainMs
            logEntity.isSuccessful = isSuccessful
            logEntity.code = code
            logEntity.headers = header
            logEntity.segments = segmentList
            printFileResponse(logEntity)
            return response
        }
        return response.newBuilder().body(body).build()
    }

    class Builder {
        internal var isDebug = false
        var type = INFO
            private set
        private var requestTag: String? = null
        private var responseTag: String? = null
        var level = Level.BASIC
            private set
        private val builder: Headers.Builder = Headers.Builder()
        var logger: Logger? = null
            private set
        val headers: Headers
            get() = builder.build()

        fun getTag(isRequest: Boolean): String {
            return if (isRequest) {
                if (TextUtils.isEmpty(requestTag)) TAG else requestTag!!
            } else {
                if (TextUtils.isEmpty(responseTag)) TAG else responseTag!!
            }
        }

        /**
         * @param name  Filed
         * @param value Value
         * @return Builder
         * Add a field with the specified value
         */
        fun addHeader(name: String, value: String): Builder {
            builder[name] = value
            return this
        }

        /**
         * @param level set log level
         * @return Builder
         * @see Level
         */
        fun setLevel(level: Level): Builder {
            this.level = level
            return this
        }

        /**
         * Set request and response each log tag
         *
         * @param tag general log tag
         * @return Builder
         */
        fun tag(tag: String): Builder {
            TAG = tag
            return this
        }

        /**
         * Set request log tag
         *
         * @param tag request log tag
         * @return Builder
         */
        fun request(tag: String?): Builder {
            requestTag = tag
            return this
        }

        /**
         * Set response log tag
         *
         * @param tag response log tag
         * @return Builder
         */
        fun response(tag: String?): Builder {
            responseTag = tag
            return this
        }

        /**
         * @param isDebug set can sending log output
         * @return Builder
         */
        fun loggable(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        /**
         * @param type set sending log output type
         * @return Builder
         * @see Platform
         */
        fun log(type: Int): Builder {
            this.type = type
            return this
        }

        /**
         * @param logger manuel logging interface
         * @return Builder
         * @see Logger
         */
        fun logger(logger: Logger?): Builder {
            this.logger = logger
            return this
        }

        fun build(): LoggingInterceptor {
            return LoggingInterceptor(this)
        }

        companion object {
            private var TAG = "LoggingI"
        }

    }

    init {
        isDebug = builder.isDebug
    }
}