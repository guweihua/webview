package com.example.base.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.base.utils.MyStatusBarUtil;
import com.githang.statusbar.StatusBarCompat;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        setContentView(provideContentViewId());

        setStatusBar();

        initThings(savedInstanceState);
        initListeners();
    }

    protected void setStatusBar() {
        MyStatusBarUtil.myStatusBar(this);
    }


    /**
     * 初始化事件监听者
     */
    public abstract void initListeners();

    /**
     * @param savedInstanceState 缓存数据
     * <p>
     * 初始化一些事情
     */
    protected abstract void initThings(Bundle savedInstanceState);


    /**
     * @return 提供LayoutId
     */
    abstract protected int provideContentViewId();


    @Override
    public void onBackPressed() {
        finish();

    }


    @Override
    public void finish() {
        super.finish();
    }

    protected void addViewClickEvent(int id, View.OnClickListener mOnClickListener) {
        View view = findViewById(id);
        if (view != null) {
            view.setOnClickListener(mOnClickListener);
        }
    }


}
