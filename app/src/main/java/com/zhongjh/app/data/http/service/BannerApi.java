package com.zhongjh.app.data.http.service;

import com.zhongjh.app.entity.ApiEntity;
import com.zhongjh.app.entity.Banner;

import java.util.List;

import retrofit2.http.GET;

/**
 * @author zhongjh
 * @date 2022/3/30
 * 面板api
 */
public interface BannerApi {

    @GET("banner/json")
    ApiEntity<List<Banner>> json();

}
