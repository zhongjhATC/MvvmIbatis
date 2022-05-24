package com.zhongjh.app.phone.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestRepositoryImpl {

    fun count3Flow(): Flow<Int> = flow {
        (0..3).forEach {
            emit(it)
        }
    }
}