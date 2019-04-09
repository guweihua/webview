package com.example.weihuagu.myprogram.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.base.utils.Toasts;
import com.example.weihuagu.myprogram.R;
import com.example.weihuagu.myprogram.base.BaseFragment;
import com.example.weihuagu.myprogram.widget.dialog.choosePicDialog;

import butterknife.BindView;

public class IndexFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_choose_pic)
    TextView tvChoosePic;


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    public void initListeners() {


        tvTitle.setOnClickListener(v -> {
            Toasts.toast(getContext(), "点点点");
        });


        tvChoosePic.setOnClickListener(v -> {
            new choosePicDialog(getContext()).show();
        });

    }

    @Override
    protected void initThings(View view, Bundle savedInstanceState) {
    }


}
