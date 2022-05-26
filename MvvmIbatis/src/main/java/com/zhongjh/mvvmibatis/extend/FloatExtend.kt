package com.zhongjh.mvvmibatis.extend

import com.zhongjh.mvvmibatis.base.IApiEntity
import com.zhongjh.mvvmibatis.model.State
import kotlinx.coroutines.flow.*

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

/**
 * 处理stateFlow的onStart和判断是否逻辑错误的过程，然后返回相关状态实体
 * @param requestBlock 一个时间，返回IApiEntity
 * T 传递进来的是该类型 ApiEntity<List<Banner>>
 * emit后的类型是State<List<Banner>>
 */
suspend fun <T, A : IApiEntity<T>> launchFlow2(
    stateFlow: MutableStateFlow<State<T>>,
    requestBlock: suspend () -> A
): MutableStateFlow<State<T>> {
    flow {
        val iApiEntity = requestBlock()
        if (iApiEntity.getErrorEntity() == null) {
            emit(State.Success(iApiEntity.getDataEntity()))
        } else {
            emit(State.Failed(iApiEntity.getErrorEntity()!!))
        }
    }.onStart {
        stateFlow.value = State.Loading()
    }.catch {
        stateFlow.value = State.Error(it)
    }


    stateFlow.onStart {
        stateFlow.value = State.Loading()
    }.catch {
        stateFlow.value = State.Error(it)
    }
        .collect {
            val iApiEntity = requestBlock()
            if (iApiEntity.getErrorEntity() == null) {
                stateFlow.value = State.Success(iApiEntity.getDataEntity())
            } else {
                stateFlow.value = State.Failed(iApiEntity.getErrorEntity()!!)
            }
        }
    return stateFlow
}