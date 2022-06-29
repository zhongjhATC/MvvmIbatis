package com.zhongjh.app.entity

/**
 * 搜索的类型
 * @author zhongjh
 * @date 2022/4/8
 */
enum class SearchType(var key: Int, var value: String) {

    YuanShen(0, "原神"),
    Product(1, "商品"),
    Auction(2, "拍卖"),
    Invitation(3, "帖子"),
    Consult(4, "咨询"),
    User(5, "用户"),
    Bar(5, "酒吧");

    companion object {
        fun match(key: Int): SearchType? {
            var result: SearchType? = null
            for (s in values()) {
                if (s.key == key) {
                    result = s
                    break
                }
            }
            return result
        }

        fun catchMessage(msg: String): SearchType? {
            var result: SearchType? = null
            for (s in values()) {
                if (s.value == msg) {
                    result = s
                    break
                }
            }
            return result
        }
    }
}