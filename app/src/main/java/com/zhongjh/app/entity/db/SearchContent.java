package com.zhongjh.app.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 搜索历史记录
 *
 * @author zhongjh
 * @date 2022/4/14
 */
@Entity
public class SearchContent {

    @Id(autoincrement = true)
    private Long id;

    @Index
    private String content;

    @Generated(hash = 812299228)
    public SearchContent(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 206336392)
    public SearchContent() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
