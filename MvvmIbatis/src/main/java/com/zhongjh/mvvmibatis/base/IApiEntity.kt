package com.zhongjh.mvvmibatis.base

import com.zhongjh.mvvmibatis.entity.ErrorEntity

/**
 *
 * @author zhongjh
 * @date 2022/5/19
 */
interface IApiEntity<T> {

    /**
     * 用于判断该类是否逻辑失败的
     * @return 如果是逻辑失败就返回ErrorEntity，否则返回null
     */
    fun getErrorEntity(): ErrorEntity?

    /**
     * 用于传递实体数据给MvvmIbatis框架使用,当使用这个方法的时候，是确定一定不会为null的
     */
    fun getDataEntity() : T

}