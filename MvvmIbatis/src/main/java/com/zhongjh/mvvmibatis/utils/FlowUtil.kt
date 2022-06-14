package com.zhongjh.mvvmibatis.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * 利用Flow处理一些稍微复杂的事情，比如倒计时
 * @author zhongjh
 * @date 2022/6/10
 */
object FlowUtil {

    /**
     * 倒计时
     * @param total 总共时间，单位秒
     * @param scope Flow范围
     * @param onTick 时间每到1秒时触发事件
     * @param onStart 正式开始触发事件
     * @param onFinish 倒计时完全结束后触发事件
     */
    fun countDownCoroutines(
        total: Int,
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
    ): Job {
        return flow {
            for (i in total downTo 0) {
                emit(i)
                delay(1000)
            }
        }.flowOn(Dispatchers.Main)
            .onStart { onStart?.invoke() }
            .onEach {
                if (it == 0) {
                    onFinish?.invoke()
                } else {
                    onTick.invoke(it)
                }
            }
            .launchIn(scope)
    }


}