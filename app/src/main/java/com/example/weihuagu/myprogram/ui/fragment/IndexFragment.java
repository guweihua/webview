package com.example.weihuagu.myprogram.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.base.common.inter.OnAcceptDataListener;
import com.example.base.common.inter.OnAcceptResListener;
import com.example.base.common.inter.OnBindLocalListener;
import com.example.weihuagu.myprogram.R;
import com.example.weihuagu.myprogram.base.BaseFragment;
import com.example.weihuagu.myprogram.inter.AndroidScheduler;
import com.example.weihuagu.myprogram.inter.Res;
import com.example.weihuagu.myprogram.inter.SB;
import com.example.weihuagu.myprogram.ui.mvp.modul.UserInfo;
import com.example.weihuagu.myprogram.utils.NetEngine;
import com.example.weihuagu.myprogram.widget.dialog.choosePicDialog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;

public class IndexFragment extends BaseFragment {
    String TAG = "IndexFragment";


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_choose_pic)
    TextView tvChoosePic;


    private Subscription mSubscription; // 用于保存Subscription对象


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    public void initListeners() {


        tvTitle.setOnClickListener(v -> {
            mSubscription.request(2);
        });


        tvChoosePic.setOnClickListener(v -> {
            new choosePicDialog(getContext()).show();
        });

    }

    @Override
    protected void initThings(View view, Bundle savedInstanceState) {
//        normalFlowable();


        //没封装前
        myrequest();


    }


    /**
     * @param observable
     * @param onAcceptDataListener
     * @param onAcceptResListener
     * @param onBindLocalListener
     * @param isNeedProgress
     * @param strResId
     */
    private void myRequest(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, OnAcceptResListener onAcceptResListener, OnBindLocalListener onBindLocalListener, boolean isNeedProgress, int strResId) {




    }

    private void myrequest() {

        NetEngine.getService().getUserInfo()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new DisposableObserver<UserInfo>() {


                    @Override
                    public void onNext(UserInfo userInfo) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    /**
     * 正常的求情
     */
    private void normalFlowable() {


        // 步骤1：创建被观察者 =  Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送完成");
                emitter.onComplete();

            }
        }, BackpressureStrategy.ERROR)
                // 设置被观察者在io线程中进行
                .subscribeOn(Schedulers.io())
                // 设置观察者在主线程中进行
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {

                    // 步骤2：创建观察者 =  Subscriber & 建立订阅关系
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                        // 相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                        // 不同点：Subscription增加了void request(long n)


                        // 保存Subscription对象，等待点击按钮时（调用request(2)）观察者再接收事件
//                        mSubscription = s;

                        Log.d(TAG, "onSubscribe");
                        s.request(3);


                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");

                    }
                });


    }


}
