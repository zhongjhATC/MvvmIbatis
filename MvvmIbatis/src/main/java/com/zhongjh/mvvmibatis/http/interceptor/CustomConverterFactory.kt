package com.zhongjh.mvvmibatis.http.interceptor

import com.google.gson.Gson
import retrofit2.Retrofit
import okhttp3.ResponseBody
import okhttp3.RequestBody
import com.google.gson.reflect.TypeToken
import kotlin.jvm.JvmOverloads
import retrofit2.Converter
import java.lang.NullPointerException
import java.lang.reflect.Type

/**
 * 重写父类的发送数据、返回数据等格式
 * @author zhongjh
 * @date 2022/3/30
 */
class CustomConverterFactory private constructor(gson: Gson?) : Converter.Factory() {
    private val gson: Gson

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return CustomResponseBodyConverter()
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): CustomConverterFactory {
            return CustomConverterFactory(gson)
        }
    }

    init {
        if (gson == null) {
            throw NullPointerException("gson == null")
        }
        this.gson = gson
    }
}