package com.example.weihuagu.myprogram.utils;

import android.util.Log;

import com.example.weihuagu.myprogram.inter.AndroidScheduler;
import com.example.weihuagu.myprogram.inter.Res;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import rx.functions.Func1;

public class NetEngine {

    private static ApiService apiService;


    public static ApiService getService() {
        if (apiService == null) {
            apiService = com.example.base.utils.NetEngine.initRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    public static void getService(String url) {


        com.example.base.utils.NetEngine.changeApiBaseUrl(url);


    }


    public static OkHttpClient getClient() {
        return com.example.base.utils.NetEngine.getClient();
    }


    protected  <T> Observable<T> observe(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread());
    }




}
