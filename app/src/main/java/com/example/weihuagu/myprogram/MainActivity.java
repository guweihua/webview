package com.example.weihuagu.myprogram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.example.weihuagu.myprogram.base.BaseActivity;
import com.example.weihuagu.myprogram.widget.MainMenu;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


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




        if (mainMenu != null) {
            mainMenu.enterHomeFragment();

        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setHideAnimation(imgSplash, 2000);
            }
        }, 500);

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
