package com.zhongjh.mvvmibatis.extend

import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.entity.State
import kotlinx.coroutines.flow.*

/**
 * 主要是为了服务ApiEntity转化成State的过程
 * 处理flow的onStart和判断是否逻辑错误的过程，然后返回相关状态实体
 * @param requestBlock 一个时间，返回IApiEntity
 * T 传递进来的是该类型 ApiEntity<List<Banner>>
 * emit后的类型是State<List<Banner>>
 */
suspend fun <T, A : IApiEntity<T>> launchApiFlow(
    stateFlow: MutableStateFlow<State<T>>,
    requestBlock: suspend () -> A
): Flow<State<T>> {
    val flow = flow {
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
    flow.collect {
        stateFlow.value = it
    }
    return flow
}

suspend fun <T> launchFlow(
    stateFlow: MutableStateFlow<State<T>>,
    requestBlock: suspend () -> T
): Flow<State<T>> {
    val flow = flow<State<T>> {
        val value = requestBlock()
        emit(State.Success(value))
    }.onStart {
        emit(State.Loading())
    }.catch {
        emit(State.Error(it))
    }
    flow.collect {
        stateFlow.value = it
    }
    return flow
}
