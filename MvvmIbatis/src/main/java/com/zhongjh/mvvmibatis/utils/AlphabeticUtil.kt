package com.zhongjh.mvvmibatis.utils

import java.io.UnsupportedEncodingException

/**
 * 拼音工具，获取一个文字的首字母
 * @author zhongjh
 * @date 2022/7/4
 */
object AlphabeticUtil {
    /**
     * 存放国标一级汉字不同读音的起始区位码
     */
    private val secPosValueList = intArrayOf(
        1601, 1637, 1833, 2078, 2274, 2302,
        2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
        4086, 4390, 4558, 4684, 4925, 5249, 5600
    )

    /**
     * 存放国标一级汉字不同读音的起始区位码对应读音
     */
    private val firstLetter = charArrayOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
        'y', 'z'
    )

    /**
     * 获取一个汉字的首字母
     */
    fun getFirstLetter(ch: Char): Char {
        val uniCode: ByteArray? = try {
            ch.toString().toByteArray(charset("GBK"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return ch
        }
        uniCode?.let {
            return if (it[0] in 1..127) {
                // 非汉字
                return ch
            } else {
                convert(it)
            }
        }
        return ch
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    private fun convert(bytes: ByteArray): Char {
        var result = '-'
        var i = 0
        while (i < bytes.size) {
            bytes[i] = (bytes[i] - 160).toByte()
            i++
        }
        val secPosValue: Int = bytes[0] * 100 + bytes[1]
        i = 0
        while (i < 23) {
            if (secPosValue >= secPosValueList[i] &&
                secPosValue < secPosValueList[i + 1]
            ) {
                result = firstLetter[i]
                break
            }
            i++
        }
        return result
    }
}