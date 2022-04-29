package com.zhongjh.mvvmibatis.http.log

/**
 * 打印接口
 *
 * @author zhongjh
 * @date 2022/3/30
 */
interface Logger {
    /**
     * 打印日志
     *
     * @param level type
     * @param tag   标签
     * @param msg   信息
     */
    fun log(level: Int, tag: String?, msg: String?)
}