package com.zhongjh.app.entity

/**
 *
 * @author zhongjh
 * @date 2022/4/14
 *
 * 封装接口的返回分页实体类
 */
class PageEntity<T> {

    var data: MutableList<T>? = null

    /**
     * 总数，用于参考是否还能请求下一页
     */
    var total: Int = 0

    /**
     * 每页多少条
     */
    var size: Int = 0

    /**
     * 当前第几页
     */
    var current = 0

    /**
     * 总共几页
     */
    var pages: Int = 0

}