package com.example.weihuagu.myprogram.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends com.example.base.common.BaseActivity {

    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(provideContentViewId());
        unBinder = ButterKnife.bind(this);


        initThings(savedInstanceState);
        initListeners();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
    }
}
