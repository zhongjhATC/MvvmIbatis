package com.zhongjh.mvvmibatis.http.cookie.store

import okhttp3.Cookie
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * 序列化的一个Cookie实体类
 *
 * @author zhongjh
 * @date 2022/3/30
 */
class SerializableHttpCookie(@field:Transient private val cookie: Cookie) : Serializable {

    @Transient
    private var clientCookie: Cookie? = null

    fun getCookie(): Cookie {
        var bestCookie = cookie
        if (clientCookie != null) {
            bestCookie = clientCookie as Cookie
        }
        return bestCookie
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie.name)
        out.writeObject(cookie.value)
        out.writeLong(cookie.expiresAt)
        out.writeObject(cookie.domain)
        out.writeObject(cookie.path)
        out.writeBoolean(cookie.secure)
        out.writeBoolean(cookie.httpOnly)
        out.writeBoolean(cookie.hostOnly)
        out.writeBoolean(cookie.persistent)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(objectInputStream: ObjectInputStream) {
        val name = objectInputStream.readObject() as String
        val value = objectInputStream.readObject() as String
        val expiresAt = objectInputStream.readLong()
        val domain = objectInputStream.readObject() as String
        val path = objectInputStream.readObject() as String
        val secure = objectInputStream.readBoolean()
        val httpOnly = objectInputStream.readBoolean()
        val hostOnly = objectInputStream.readBoolean()
        var builder = Cookie.Builder()
        builder = builder.name(name)
        builder = builder.value(value)
        builder = builder.expiresAt(expiresAt)
        builder = if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        builder = builder.path(path)
        builder = if (secure) builder.secure() else builder
        builder = if (httpOnly) builder.httpOnly() else builder
        clientCookie = builder.build()
    }

    companion object {
        private const val serialVersionUID = 6374381323722046732L
    }
}