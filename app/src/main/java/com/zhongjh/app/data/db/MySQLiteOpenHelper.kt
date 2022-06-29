package com.zhongjh.app.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.yuweiguocn.library.greendao.MigrationHelper
import com.github.yuweiguocn.library.greendao.MigrationHelper.ReCreateAllTableListener
import com.zhongjh.app.data.db.dao.DaoMaster
import com.zhongjh.app.data.db.dao.DaoMaster.OpenHelper
import com.zhongjh.app.data.db.dao.SearchContentDao
import org.greenrobot.greendao.database.Database

/**
 * 初始化数据库
 * 处理版本更新时，更新数据库表结构
 */
class MySQLiteOpenHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?) :
    OpenHelper(context, name, factory) {

    override fun onUpgrade(db: Database, oldVersion: Int, newVersion: Int) {
        MigrationHelper.migrate(
            db,
            object : ReCreateAllTableListener {
            override fun onCreateAllTables(db: Database, ifNotExists: Boolean) {
                DaoMaster.createAllTables(db, ifNotExists)
            }

            override fun onDropAllTables(db: Database, ifExists: Boolean) {
                DaoMaster.dropAllTables(db, ifExists)
            }
        },
            SearchContentDao::class.java
        )
    }
}