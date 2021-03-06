package com.zhongjh.app.entity

/**
 * 商城首页,汇和几个实体数据
 * @author zhongjh
 * @date 2022/3/31
 */
class ShopHome {
    var banners: List<Banner>? = null
    var banners2: List<Banner>? = null
    var productsIn: MutableList<Product>? = null

    /**
     * 分页数据
     */
    var products: PageEntity<Product>? = null
}