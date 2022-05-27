package com.zhongjh.app.data.db.business

import android.text.TextUtils
import android.util.Log
import com.zhongjh.app.data.db.dao.SearchContentDao
import com.zhongjh.app.entity.db.SearchContent
import com.zhongjh.app.phone.MyApplication
import org.greenrobot.greendao.query.QueryBuilder
import javax.inject.Inject


/**
 * 搜索内容的相关逻辑
 * @author zhongjh
 * @date 2022/4/15
 */
class SearchContentBusiness @Inject constructor(
    private val searchContentDao: SearchContentDao
)  {

    private val mTag = SearchContentBusiness::class.qualifiedName
    private val maxCount = 12

    /**
     * 获取搜索内容，最高12条数据
     */
    fun getSearchContents(): MutableList<SearchContent> {
        val queryBuilder: QueryBuilder<SearchContent> = searchContentDao.queryBuilder()
        return queryBuilder.limit(maxCount).list()
    }

    /**
     * 添加搜索内容
     * @param searchStr 搜索文本内容
     * @return 添加后的id
     */
    fun addSearchContents(searchStr: String): Long {
        Log.d(mTag, "addSearchContents $searchStr")
        // 如果是空的,不添加直接返回0
        if (TextUtils.isEmpty(searchStr)) {
            return 0
        }
        val searchContents = searchContentDao.loadAll()
        // 判断是否已经有相同的
        for (item in searchContents) {
            if (item.content.equals(searchStr)) {
                return 0
            }
        }
        // 判断数量是否 >= 12个
        return if (searchContentDao.count() >= maxCount) {
            // 开启事务，删除最后一个
            MyApplication.instance.daoSession.callInTx {
                searchContentDao.delete(searchContents[searchContents.size - 1])
                val searchContent = SearchContent()
                searchContent.content = searchStr
                searchContentDao.insert(searchContent)
            }
        } else {
            val searchContent = SearchContent()
            searchContent.content = searchStr
            searchContentDao.insert(searchContent)
        }
    }

}