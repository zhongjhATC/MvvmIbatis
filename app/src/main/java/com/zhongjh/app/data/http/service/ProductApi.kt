package com.zhongjh.app.data.http.service

import com.zhongjh.app.entity.ApiEntity
import com.zhongjh.app.entity.PageEntity
import com.zhongjh.app.entity.Product

/**
 * 产品的有关接口
 */
object ProductApi {

    /**
     * 获取模拟的商品
     * @param page 获取第几页的数据
     */
    fun getProducts(page: Int): ApiEntity<PageEntity<Product>> =
        simulationProducts(page)

    /**
     * 模拟商品数据
     * @param page 获取第几页的数据
     */
    private fun simulationProducts(page: Int): ApiEntity<PageEntity<Product>> {
        // 封装PageEntity
        val pageProduct = PageEntity<Product>()
        pageProduct.pages = 3
        val products = when (page) {
            0 -> {
                pageProduct.current = 0
                simulationProducts1()
            }
            1 -> {
                pageProduct.current = 1
                simulationProducts2()
            }
            2 -> {
                pageProduct.current = 2
                simulationProducts3()
            }
            else -> {
                pageProduct.current = 0
                simulationProducts1()
            }
        }
        pageProduct.data = products
        pageProduct.total = 3 * products.size
        pageProduct.size = products.size
        // 封装ApiEntity
        val apiProduct = ApiEntity<PageEntity<Product>>()
        apiProduct.errorCode = "0"
        apiProduct.data = pageProduct
        return apiProduct
    }

    /**
     * 模拟第一页的数据
     */
    private fun simulationProducts1(): List<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "雷神"
        leiShen.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-1.jpg"
        leiShen.price = "648"
        val hutao = Product()
        hutao.name = "胡桃"
        hutao.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-2.jpg"
        hutao.price = "648"
        val shenli = Product()
        shenli.name = "神里"
        shenli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-3.jpg"
        shenli.price = "648"
        val jiutun = Product()
        jiutun.name = "酒吞"
        jiutun.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-4.jpg"
        jiutun.price = "648"
        val dishitian = Product()
        dishitian.name = "帝释天"
        dishitian.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-5.jpg"
        dishitian.price = "648"
        val axiuluo = Product()
        axiuluo.name = "阿修罗"
        axiuluo.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-6.jpg"
        axiuluo.price = "648"
        val yidou = Product()
        yidou.name = "一斗"
        yidou.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/1.jpg"
        yidou.price = "648"
        val zhongli = Product()
        zhongli.name = "钟离"
        zhongli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/2.jpg"
        zhongli.price = "648"
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

    /**
     * 模拟第二页的数据
     */
    private fun simulationProducts2(): List<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "琴"
        leiShen.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/3.jpg"
        leiShen.price = "648"
        val hutao = Product()
        hutao.name = "宵宫"
        hutao.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/4.jpg"
        hutao.price = "648"
        val shenli = Product()
        shenli.name = "砂糖"
        shenli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/5.jpg"
        shenli.price = "648"
        val jiutun = Product()
        jiutun.name = "皇女"
        jiutun.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/6.jpg"
        jiutun.price = "648"
        val dishitian = Product()
        dishitian.name = "阿贝多"
        dishitian.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/7.jpg"
        dishitian.price = "648"
        val axiuluo = Product()
        axiuluo.name = "五郎"
        axiuluo.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/8.jpg"
        axiuluo.price = "648"
        val yidou = Product()
        yidou.name = "心海"
        yidou.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/9.jpg"
        yidou.price = "648"
        val zhongli = Product()
        zhongli.name = "行秋"
        zhongli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/10.jpg"
        zhongli.price = "648"
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

    /**
     * 模拟第二页的数据
     */
    private fun simulationProducts3(): List<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "可莉"
        leiShen.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/11.jpg"
        leiShen.price = "648"
        val hutao = Product()
        hutao.name = "万叶"
        hutao.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/12.jpg"
        hutao.price = "648"
        val shenli = Product()
        shenli.name = "申鹤"
        shenli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/13.jpg"
        shenli.price = "648"
        val jiutun = Product()
        jiutun.name = "夜兰"
        jiutun.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/14.jpg"
        jiutun.price = "648"
        val dishitian = Product()
        dishitian.name = "香菱"
        dishitian.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/15.jpg"
        dishitian.price = "648"
        val axiuluo = Product()
        axiuluo.name = "云堇"
        axiuluo.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/16.jpg"
        axiuluo.price = "648"
        val yidou = Product()
        yidou.name = "埃洛伊"
        yidou.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/17.jpg"
        yidou.price = "648"
        val zhongli = Product()
        zhongli.name = "安柏"
        zhongli.pictureUrl =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/18.jpg"
        zhongli.price = "648"
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

}

