package com.zhongjh.mvvmibatis.http.cookie.store

import okhttp3.Cookie
import okhttp3.HttpUrl

/**
 * Cookie 相关接口
 *
 * @author zhongjh
 * @date 2022/3/30
 */
interface CookieStore {
    /**
     * 保存url对应所有cookie
     * @param url url
     * @param cookies cookies集合
     */
    fun saveCookie(url: HttpUrl, cookies: List<Cookie>)

    /**
     * 保存url对应所有cookie
     * @param url url
     * @param cookie cookie
     */
    fun saveCookie(url: HttpUrl, cookie: Cookie)

    /**
     * 加载url所有的cookie
     * @param url url
     * @return cookies集合
     */
    fun loadCookie(url: HttpUrl): List<Cookie>

    /**
     * 获取当前所有保存的cookie
     * @return cookies集合
     */
    val allCookie: List<Cookie>

    /**
     * 获取当前url对应的所有的cookie
     * @param url url
     * @return cookie
     */
    fun getCookie(url: HttpUrl): List<Cookie>

    /**
     * 根据url和cookie移除对应的cookie
     * @param url url
     * @param cookie cookie
     * @return 是否删除成功
     */
    fun removeCookie(url: HttpUrl, cookie: Cookie): Boolean

    /**
     * 根据url移除所有的cookie
     * @param url url
     * @return 是否删除成功
     */
    fun removeCookie(url: HttpUrl): Boolean

    /**
     * 移除所有的cookie
     * @return 是否删除成功
     */
    fun removeAllCookie(): Boolean
}