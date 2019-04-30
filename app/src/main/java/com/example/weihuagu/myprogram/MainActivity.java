package com.example.weihuagu.myprogram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.example.base.common.BaseView;
import com.example.base.common.inter.OnAcceptDataListener;
import com.example.base.common.inter.Res;
import com.example.base.utils.Toasts;
import com.example.weihuagu.myprogram.base.BaseActivity;
import com.example.weihuagu.myprogram.utils.NetEngine;
import com.example.weihuagu.myprogram.widget.MainMenu;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.example.base.utils.NetUtils.isNetAvailable;


public class MainActivity extends BaseActivity {


    String url = "http://720yun.com/t/aog8omq6xqsr9oayij&uid=9&PHPSESSID=9hg80t1monauljfo72tfcvjf72";

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.main_menu)
    MainMenu mainMenu;
    @BindView(R.id.img_splash)
    FrameLayout imgSplash;

    private AlphaAnimation mHideAnimation;


    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {


        getUserData();


        mainMenu.enterHomeFragment();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setHideAnimation(imgSplash, 2000);
            }
        }, 500);

    }

    private void getUserData() {


        NetEngine.getService().getUserInfo();


        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        })

                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                        Res res = (Res) o;

                        if (res.isOk(getApplicationContext())) {

                        } else {

                            if (res.getStatus() == 0) {


                            } else if (res.getStatus() == 10) {

                            } else {

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (!isNetAvailable(getApplicationContext())) {
                        } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                        } else {
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }


    public void setHideAnimation(final View view, int duration) {


        // 监听动画结束的操作
        AlphaAnimation mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


                view.setVisibility(View.GONE);


            }
        });
        view.startAnimation(mHideAnimation);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 188) {


        }
    }
}
