package com.example.weihuagu.myprogram.utils;

import android.content.Context;

import com.example.base.common.inter.OnFailListener;

public interface MyRes<T> {


    /**
     * 获取数据
     *
     * @return
     */
    T getData();

    /**
     * 获取说明信息
     *
     * @return
     */
    String getMsg();


    /**
     * 获取积分
     *
     * @return
     */
    int getPoint();


    /**
     * 获取 是否成功
     *
     * @param context
     *
     * @return
     */
    boolean isOk(Context context);



    boolean isOk();

    /**
     * 获取 是否成功
     *
     * @param context
     *
     * @return
     */
    boolean isOk(Context context, OnFailListener onFailListener);

    /**
     * 是否有更多
     *
     * @param page
     *
     * @return
     */
    boolean isHasMore(int page);


    boolean isHasMoreListId(int page);


    /**
     * 是否有更多
     *
     * @return
     */
    boolean isHasMore();

    int getStatus();

    String geturl();

    String getLoginStatus();

    int getToTal();


    String getAndroidurl();

}
