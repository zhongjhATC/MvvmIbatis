package com.zhongjh.app.entity

import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.entity.ErrorEntity

/**
 *
 * @author zhongjh
 * @date 2022/3/30
 *
 * 封装接口的返回实体类
 */
class ApiEntity<T> : IApiEntity<T> {

    var errorCode = "0"
    var errorMsg: String? = null
    var data: T? = null

    /**
     * 用于判断该类是否逻辑失败的
     * 如果失败就返回相关实体，成功则返回null
     * wanAndroid 的api就是errorCode=0就是正常的，其他失败
     */
    override fun getErrorEntity(): ErrorEntity? {
        if (errorCode == "0") {
            return null
        }
        return ErrorEntity(errorCode, errorMsg)
    }

    /**
     * 用于传递实体数据给MvvmIbatis框架使用,当使用这个方法的时候，是确定一定不会为null的
     */
    override fun getDataEntity(): T {
        return data!!
    }


}