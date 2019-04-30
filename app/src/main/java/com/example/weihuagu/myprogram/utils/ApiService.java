package com.example.weihuagu.myprogram.utils;

import com.example.weihuagu.myprogram.ui.mvp.modul.UserInfo;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("User/User")
    public Observable<RES<UserInfo>> getUserInfo();//我的页面使用--保存在本地的

}
