package com.zhongjh.mvvmibatis.http.cookie

import com.zhongjh.mvvmibatis.http.cookie.store.CookieStore
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * 调用Cookie方法
 *
 * @author zhongjh
 * @date 2022/3/30
 */
class CookieJarImpl(cookieStore: CookieStore?) : CookieJar {
    private val cookieStore: CookieStore
    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore.saveCookie(url, cookies)
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore.loadCookie(url)
    }

    init {
        requireNotNull(cookieStore) { "cookieStore can not be null!" }
        this.cookieStore = cookieStore
    }
}