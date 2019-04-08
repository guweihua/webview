package com.example.weihuagu.myprogram.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.weihuagu.myprogram.R;
import com.example.weihuagu.myprogram.base.BaseFragment;
import com.tencent.bugly.beta.Beta;

public class SettingFragment extends BaseFragment {


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(View view, Bundle savedInstanceState) {
        addViewClickEvent(R.id.update, v -> {

            Beta.checkUpgrade(true, false);

        },view);



    }
}
