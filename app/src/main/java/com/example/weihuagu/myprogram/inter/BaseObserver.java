package com.example.weihuagu.myprogram.inter;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.example.base.widget.dialog.Dialog;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<Res<T>> {

    protected Context mContext;
    private String labelTxt;
    private android.app.Dialog dialog;

    public BaseObserver(Context cxt, String text) {
        this.mContext = cxt;
        this.labelTxt = text;
        dialog = new Dialog().showProgressingDialog(mContext, labelTxt);
    }

    public BaseObserver(Context cxt) {
        this.mContext = cxt;

    }


    //开始
    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    //获取数据
    @Override
    public void onNext(Res<T> tBaseEntity) {
        try {
            onSuccees(tBaseEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //失败
    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);  //网络错误
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //结束
    @Override
    public void onComplete() {
        onRequestEnd();//请求结束
    }

    /**
     * 返回成功
     *
     * @param t
     */
    protected abstract void onSuccees(Res<T> t) throws Exception;


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {

    }

    protected void onRequestEnd() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }



}
