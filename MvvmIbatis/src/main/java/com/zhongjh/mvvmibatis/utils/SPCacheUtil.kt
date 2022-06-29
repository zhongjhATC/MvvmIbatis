package com.zhongjh.mvvmibatis.utils

import com.blankj.utilcode.util.SPStaticUtils
import com.tencent.mmkv.MMKV

/**
 * 缓存工具
 * @author zhongjh
 * @date 2022/3/28
 */
object SPCacheUtil {

    fun getString(key: String): String? {
        return MMKV.defaultMMKV()?.decodeString(key) ?: SPStaticUtils.getString(key)
    }

    fun put(key: String, value: String?) {
        MMKV.defaultMMKV()?.encode(key, value) ?: SPStaticUtils.put(key, value)
    }

    fun getInt(key: String, defValue: Int): Int {
        return MMKV.defaultMMKV()?.decodeInt(key, defValue) ?: SPStaticUtils.getInt(key, defValue)
    }

    fun put(key: String, value: Int) {
        MMKV.defaultMMKV()?.encode(key, value) ?: SPStaticUtils.put(key, value)
    }

    fun getLong(key: String, defValue: Long): Long {
        return MMKV.defaultMMKV()?.decodeLong(key, defValue) ?: SPStaticUtils.getLong(key, defValue)
    }

    fun put(key: String, value: Long) {
        MMKV.defaultMMKV()?.encode(key, value) ?: SPStaticUtils.put(key, value)
    }

    fun getFloat(key: String, defValue: Float): Float {
        return MMKV.defaultMMKV()?.decodeFloat(key, defValue) ?: SPStaticUtils.getFloat(
            key,
            defValue
        )
    }

    fun putFloat(key: String, value: Float) {
        MMKV.defaultMMKV()?.encode(key, value) ?: SPStaticUtils.put(key, value)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return MMKV.defaultMMKV()?.decodeBool(key, defValue) ?: SPStaticUtils.getBoolean(
            key,
            defValue
        )
    }

    fun put(key: String, value: Boolean) {
        MMKV.defaultMMKV()?.encode(key, value) ?: SPStaticUtils.put(key, value)
    }
}