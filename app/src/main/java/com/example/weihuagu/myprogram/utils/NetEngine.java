package com.example.weihuagu.myprogram.utils;

import okhttp3.OkHttpClient;

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

}
