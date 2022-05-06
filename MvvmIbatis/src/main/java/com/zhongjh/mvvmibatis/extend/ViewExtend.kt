package com.zhongjh.mvvmibatis.extend

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow


/**
 * 什么是 callbackFlow？官方的答案是：将基于回调的 API 转换为数据流
 *
 */
fun View.clickFlow() = callbackFlow {
    // trySend用于上游发射数据
    setOnClickListener { this.trySend(Unit).isSuccess }
    // awaitClose：写在最后，这是一个挂起函数, 当 flow 被关闭的时候 block 中的代码会被执行 可以在这里取消接口的注册等
    awaitClose { setOnClickListener(null) }
}

/**
 * 防抖动点击
 */
fun <T> Flow<T>.throttleFirst(): Flow<T> = flow {
    var lastTime = 0L // 上次发射数据的时间
    // 收集数据
    collect { upstream ->
        // 当前时间
        val currentTime = System.currentTimeMillis()
        // 时间差超过阈值则发送数据并记录时间
        if (currentTime - lastTime > 1000) {
            lastTime = currentTime
            emit(upstream)
        }
    }
}

