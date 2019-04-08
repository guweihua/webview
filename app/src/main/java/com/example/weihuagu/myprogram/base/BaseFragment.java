package com.example.weihuagu.myprogram.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder unBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(provideContentViewId(), container, false);

        initButterKnife(view);
        initThings(view, savedInstanceState);
        initListeners();


        return view;
    }

    protected void initButterKnife(View view) {
        unBinder = ButterKnife.bind(this, view);
    }


    protected void destroyButterKnife() {
        if (unBinder != null) {
            unBinder.unbind();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyButterKnife();
    }

    /**
     * 提供布局ID
     *
     * @return
     */
    protected abstract int provideContentViewId();

    /**
     * 初始化事件监听者
     */
    abstract public void initListeners();

    /**
     * 初始化一些事情
     */
    protected abstract void initThings(View view, Bundle savedInstanceState);

    protected void addViewClickEvent(int id, View.OnClickListener mOnClickListener, View view) {
        View view1 = view.findViewById(id);
        if (view1 != null) {
            view1.setOnClickListener(mOnClickListener);
        }
    }

}
