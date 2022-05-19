package com.zhongjh.mvvmibatis.extend

import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * 处理flow的onStart和判断是否逻辑错误的过程，然后返回相关状态实体
 * @param requestBlock 一个时间，返回IApiEntity
 * T 传递进来的是该类型 ApiEntity<List<Banner>>
 * emit后的类型是State<List<Banner>>
 */
fun <T, A : IApiEntity<T>> launchFlow(
    requestBlock: suspend () -> A
): Flow<State<T>> {
    return flow {
        val iApiEntity = requestBlock()
        if (iApiEntity.getErrorEntity() == null) {
            emit(State.Success(iApiEntity.getDataEntity()))
        } else {
            emit(State.Failed(iApiEntity.getErrorEntity()!!))
        }
    }.onStart {
        emit(State.Loading())
    }.catch {
        emit(State.Error(it))
    }
}