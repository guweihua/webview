package com.example.weihuagu.myprogram.utils;

import com.example.weihuagu.myprogram.ui.mvp.modul.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("User/User")
    public Observable<UserInfo> getUserInfo();//我的页面使用--保存在本地的

}
