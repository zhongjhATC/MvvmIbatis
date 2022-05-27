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
    private fun simulationProducts1(): MutableList<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "雷神"
        leiShen.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-1.jpg"
        leiShen.price = 648F
        val hutao = Product()
        hutao.name = "胡桃"
        hutao.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-2.jpg"
        hutao.price = 648F
        val shenli = Product()
        shenli.name = "神里"
        shenli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-3.jpg"
        shenli.price = 648F
        val jiutun = Product()
        jiutun.name = "酒吞"
        jiutun.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-4.jpg"
        jiutun.price = 648F
        val dishitian = Product()
        dishitian.name = "帝释天"
        dishitian.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-5.jpg"
        dishitian.price = 648F
        val axiuluo = Product()
        axiuluo.name = "阿修罗"
        axiuluo.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/-6.jpg"
        axiuluo.price = 648F
        val yidou = Product()
        yidou.name = "一斗"
        yidou.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/1.jpg"
        yidou.price = 648F
        val zhongli = Product()
        zhongli.name = "钟离"
        zhongli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/2.jpg"
        zhongli.price = 648F
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

    /**
     * 模拟第二页的数据
     */
    private fun simulationProducts2(): MutableList<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "琴"
        leiShen.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/3.jpg"
        leiShen.price = 648F
        val hutao = Product()
        hutao.name = "宵宫"
        hutao.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/4.jpg"
        hutao.price = 648F
        val shenli = Product()
        shenli.name = "砂糖"
        shenli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/5.jpg"
        shenli.price = 648F
        val jiutun = Product()
        jiutun.name = "皇女"
        jiutun.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/6.jpg"
        jiutun.price = 648F
        val dishitian = Product()
        dishitian.name = "阿贝多"
        dishitian.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/7.jpg"
        dishitian.price = 648F
        val axiuluo = Product()
        axiuluo.name = "五郎"
        axiuluo.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/8.jpg"
        axiuluo.price = 648F
        val yidou = Product()
        yidou.name = "心海"
        yidou.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/9.jpg"
        yidou.price = 648F
        val zhongli = Product()
        zhongli.name = "行秋"
        zhongli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/10.jpg"
        zhongli.price = 648F
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

    /**
     * 模拟第二页的数据
     */
    private fun simulationProducts3(): MutableList<Product> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "可莉"
        leiShen.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/11.jpg"
        leiShen.price = 648F
        val hutao = Product()
        hutao.name = "万叶"
        hutao.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/12.jpg"
        hutao.price = 648F
        val shenli = Product()
        shenli.name = "申鹤"
        shenli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/13.jpg"
        shenli.price = 648F
        val jiutun = Product()
        jiutun.name = "夜兰"
        jiutun.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/14.jpg"
        jiutun.price = 648F
        val dishitian = Product()
        dishitian.name = "香菱"
        dishitian.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/15.jpg"
        dishitian.price = 648F
        val axiuluo = Product()
        axiuluo.name = "云堇"
        axiuluo.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/16.jpg"
        axiuluo.price = 648F
        val yidou = Product()
        yidou.name = "埃洛伊"
        yidou.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/17.jpg"
        yidou.price = 648F
        val zhongli = Product()
        zhongli.name = "安柏"
        zhongli.image =
            "https://portrait.gitee.com/zhongjh/mvidemo/raw/master/server/images/18.jpg"
        zhongli.price = 648F
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        return products
    }

}

