package com.example.weihuagu.myprogram.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.base.common.inter.OnFailListener;

/**
 * Created by xianguangjin on 16/6/3.
 */

public class RES<T> implements MyRes<T> {

    @Nullable
    public T datas;

    @Nullable
    public int status;
    public String url;
    public String info;
    public int count;
    public int total;
    public int add_point;
    public String login_status;//登录状态
    public String android_url;


    @Override
    public T getData() {
        return datas;
    }

    @Override
    public String getMsg() {
        return info;
    }

    @Override
    public int getPoint() {
        return add_point;
    }

    @Override
    public boolean isOk() {
        return this.status == 1;
    }

    @Override
    public boolean isOk(Context context, OnFailListener onFailListener) {
        return false;
    }

    @Override
    public boolean isOk(Context context) {
        return isOk(context, null);
    }





    /**
     * @param page 是否有更多
     *
     * @return
     */
    @Override
    public boolean isHasMore(int page) {
        return page * count < total;
    }

    @Override
    public boolean isHasMoreListId(int page) {
        return total>0;
    }


    @Override
    public boolean isHasMore() {
        return false;
    }

    @Override
    public int getStatus() {

        return status;
    }

    @Override
    public String geturl() {
        return url;
    }

    @Override
    public String getLoginStatus() {
        return login_status;
    }

    @Override
    public int getToTal() {
        return total;
    }

    public String getAndroidurl(){
        return android_url;
    }


}
