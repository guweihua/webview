package com.example.base.common;


import android.util.Log;


import com.example.base.R;
import com.example.base.common.inter.OnAcceptDataListener;
import com.example.base.common.inter.OnAcceptResListener;
import com.example.base.common.inter.OnBindLocalListener;
import com.example.base.common.inter.Res;
import com.example.base.utils.Toasts;
import com.example.base.widget.dialog.Dialog;

import org.reactivestreams.Subscription;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.example.base.utils.NetUtils.isNetAvailable;


/**
 * Created by xianguangjin on 15/12/14.
 */
public abstract class BasePresenter<V extends BaseView> {


    public V view;

    public void attachView(V view) {
        this.view = view;
    }

    public BasePresenter build(V view) {
        this.view = view;
        return this;
    }

    public void detachView() {
        this.view = null;

    }

    public void addSubscription(Subscription s) {


    }


    /**
     * 请求列表数据,带有页面加载图
     *
     * @param observable
     * @param onAcceptDataListener
     */

    public void requestPageNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {


        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (isNetAvailable(view.getContext())) {
                    view.onError(BaseView.ERROR_LOADING, null);
                }
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

                Res res = (Res) o;

                if (res.isOk(view.getContext())) {
                    view.onError(BaseView.ERROR_NORMAL, null);
                    if (onAcceptDataListener != null) {
                        onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());

                    }
                } else {

                    if (res.getStatus() == 0) {
                        if (onAcceptDataListener != null) {
                            onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());
                        }

                    } else if (res.getStatus() == 10) {
                        Toasts.toast(view.getContext(), res.getMsg());
                        view.onError(10, "", "", null, "", null);
                    } else {
                        // view.onErrorFail(ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (view == null) {
                    return;
                }
                if (!isNetAvailable(view.getContext())) {
                    view.onErrorFail(BaseView.ERROR_NO_NET, v -> requestPageNormalData(observable, onAcceptDataListener));
                } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                    view.toast(R.string.time_out);
                    view.onErrorFail(BaseView.ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));
                } else {
                    view.onErrorFail(BaseView.ERROR_FAIL, v -> requestPageNormalData(observable, onAcceptDataListener));
                }
            }

            @Override
            public void onComplete() {

            }
        });


    }


    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {
        requestNormalData(observable, onAcceptDataListener, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener) {
        requestNormalData(observable, null, onAcceptResListener, null, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnBindLocalListener onBindLocalListener) {
        requestNormalData(observable, null, null, onBindLocalListener, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable) {
        requestNormalData(observable, null, null, null, false, -1);
    }


    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isNeedProgress) {
        requestNormalData(observable, onAcceptDataListener, isNeedProgress, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isNeedProgress, int strResId) {
        requestNormalData(observable, onAcceptDataListener, null, null, isNeedProgress, strResId);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener, boolean isNeedProgress, int strResId) {
        requestNormalData(observable, null, onAcceptResListener, null, isNeedProgress, strResId);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener, boolean isNeedProgress) {
        requestNormalData(observable, null, onAcceptResListener, null, isNeedProgress, R.string.loading);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, OnAcceptResListener onAcceptResListener, OnBindLocalListener onBindLocalListener, boolean isNeedProgress, int strResId) {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (isNetAvailable(view.getContext())) {
                    if (isNeedProgress) {
                        Dialog.showProgressingDialog(view.getContext(), strResId == -1 ? view.getContext().getString(R.string.loading) : view.getContext().getString(strResId));
                    }
                }
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

                Res res = (Res) o;

                if (isNeedProgress) {
                    Dialog.dismissProgressDialog();
                }

                /** 说明:
                 *如果要自己处理,就不执行以下操作了
                 */
                if (onAcceptResListener != null) {
                    Log.d("BasePresenter", "onAcceptResListener is null");


                    //登录状态  出现问题  被举报
                    if (res.getStatus() == 0) {

                        onAcceptResListener.onResAccept(res);

                    } else if (res.getStatus() == 10) {
                        Toasts.toast(view.getContext(), res.getMsg());
                        view.onError(10, "", "", null, "", null);
                    } else {


                        onAcceptResListener.onResAccept(res);
                    }


                } else {
                    if (res.isOk(view.getContext())) {
                        Log.d("BasePresenter", "onAcceptDataListener is  ok");

                        if (onAcceptDataListener != null) {
                            onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());

                        }
                        if (onBindLocalListener != null) {
                            onBindLocalListener.onSaveLocal(res.getData());
                        }
                    } else {
                        Log.d("BasePresenter", "onAcceptDataListener is not ok");
                        if (res.getStatus() == 10) {
                            Toasts.toast(view.getContext(), res.getMsg());
                            view.onError(10, "", "", null, "", null);
                        }

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (view == null) {
                    return;
                }
                if (isNeedProgress) {
                    Dialog.dismissProgressDialog();
                }


                if (!isNetAvailable(view.getContext())) {
                    view.onErrorFail(BaseView.ERROR_NO_NET, v -> requestPageNormalData(observable, onAcceptDataListener));
                } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                    view.toast(R.string.time_out);
                    view.onErrorFail(BaseView.ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));
                } else {
                    view.onErrorFail(BaseView.ERROR_FAIL, v -> requestPageNormalData(observable, onAcceptDataListener));
                }
            }

            @Override
            public void onComplete() {

            }
        });





    }


}
