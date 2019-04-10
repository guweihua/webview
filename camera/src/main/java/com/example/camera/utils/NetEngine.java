package com.example.camera.utils;

/**
 * Created by xianguangjin on 15/12/14.
 */

public class NetEngine {
    private static ApiService apiService;

    public static ApiService getService() {
        if (apiService == null) {
//            apiService = com.ysnows.base.utils.NetEngine.initRetrofit().create(ApiService.class);
        }
        return apiService;
    }
}
