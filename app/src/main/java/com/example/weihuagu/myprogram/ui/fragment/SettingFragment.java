package com.example.weihuagu.myprogram.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.base.widget.LineEditView;
import com.example.weihuagu.myprogram.R;
import com.example.weihuagu.myprogram.base.BaseFragment;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;

public class SettingFragment extends BaseFragment {


    @BindView(R.id.line_update)
    LineEditView lineUpdate;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initListeners() {
        lineUpdate.setOnClickListener(v -> {
            Beta.checkUpgrade(true, false);
        });
    }

    @Override
    protected void initThings(View view, Bundle savedInstanceState) {


    }


}
