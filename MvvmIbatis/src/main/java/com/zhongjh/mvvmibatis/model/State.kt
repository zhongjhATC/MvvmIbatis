package com.zhongjh.mvvmibatis.model

/**
 * 每次读取网络后会根据情况封装出以下类，根据不同情况返回
 */
sealed class State<T> {
    class Success<T>(val data: T) : State<T>()
    class Failed<T>(val errorEntity: ErrorEntity) : State<T>()
    class Empty<T>(val message: String) : State<T>()
    class Error<T>(val throwable: Throwable) : State<T>()
    class Loading<T> : State<T>()
}
