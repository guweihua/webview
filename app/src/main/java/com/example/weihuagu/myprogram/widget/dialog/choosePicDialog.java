package com.example.weihuagu.myprogram.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.example.base.CP;
import com.example.base.utils.Toasts;
import com.example.weihuagu.myprogram.R;

import java.util.ArrayList;
import java.util.Map;

public class choosePicDialog extends YDialog {


    private TextView tvCamera;
    private TextView tvChoosePic;
    private TextView cancle;


    public choosePicDialog(Context context) {
        super(context);
        setStyle(BOTTOM);
    }

    @Override
    protected void initView(View view) {


        tvCamera = view.findViewById(R.id.tv_camera);
        tvChoosePic = view.findViewById(R.id.tv_choose_pic);
        cancle = view.findViewById(R.id.cancle);


        tvCamera.setOnClickListener(v -> {
            dismiss();
        });

        tvChoosePic.setOnClickListener(v -> {

            dismiss();

            CC.obtainBuilder(CP.CP_CHOOSE_PICTURE)
                    .setActionName(CP.ACTION_CHOOSE_PICTURE)
                    .setContext(getContext())
                    .build()
                    .callAsync(new IComponentCallback() {
                        @Override
                        public void onResult(CC cc, CCResult result) {


                            Map<String, Object> dataMap = result.getDataMap();

                            ArrayList<String> url = (ArrayList<String>) dataMap.get("url");


                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasts.toast(getContext(), "" + url);
                                }
                            });


                        }
                    });


        });

        cancle.setOnClickListener(v -> {
            dismiss();
        });


    }

    @Override
    public int provideLayoutId() {
        return R.layout.dialog_choose_pic;
    }
}
