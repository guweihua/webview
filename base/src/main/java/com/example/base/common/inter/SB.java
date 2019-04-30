package com.example.base.common.inter;

import org.reactivestreams.Subscriber;


/**
 * Subscriber的封装类，打印一些信息等。。
 * Created by xianguangjin on 15/12/15.
 */
public abstract class SB<T>  {




    public void onNext(T t) {
        next(t);
    }


    public void onError(Throwable e) {
        error(e);
    }




    public void completed() {

    }

    public void error(Throwable e) {

//        if (e is SocketTimeoutException || e is SocketException) {
//            view?.getContext()?.toast(R.string.time_out)
//        }

    }

    public abstract void next(T res);


}

