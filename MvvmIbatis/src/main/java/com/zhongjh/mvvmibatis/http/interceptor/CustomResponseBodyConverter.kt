package com.zhongjh.mvvmibatis.http.interceptor

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * 自定义返回的数据源
 * 更加复杂包括是否成功的可以参考公司项目的此类
 * 该类可以放在app项目上处理，而不是该lib
 * @author zhongjh
 * @date 2022/3/30
 */
class CustomResponseBodyConverter : Converter<ResponseBody, String> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): String {
        return value.string()
    }

}