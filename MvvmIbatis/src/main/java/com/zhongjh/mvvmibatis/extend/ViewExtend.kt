package com.zhongjh.mvvmibatis.extend

import android.view.View
import com.zhongjh.mvvmibatis.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import kotlin.collections.HashMap


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


/***
 * 带延迟过滤的点击事件View扩展
 * @param time Long 延迟时间，默认800毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.onClick(time: Long = 1000, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTime) != null) getTag(R.id.triggerLastTime) as Long else 0
    set(value) {
        setTag(R.id.triggerLastTime, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelay) != null) getTag(R.id.triggerDelay) as Long else -1
    set(value) {
        setTag(R.id.triggerDelay, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    if (flag) {
        triggerLastTime = currentClickTime
    }
    return flag
}



