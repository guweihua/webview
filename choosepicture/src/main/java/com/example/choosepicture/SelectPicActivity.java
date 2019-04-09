package com.example.choosepicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.base.common.BaseActivity;
import com.example.choosepicture.utils.ImagePictureSelectorUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPicActivity extends BaseActivity {


    private String call_id;

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {

        ImagePictureSelectorUtils.multiSelect(SelectPicActivity.this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            call_id = extras.getString("call_id");

            Log.e("Afas", "----" + call_id);


        }


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_select_pic;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {

            ArrayList<String> pathList = new ArrayList<>();

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);


            if (selectList.size() > 1) {
                for (LocalMedia entity : selectList) {
                    pathList.add(entity.getPath());
                }
            }


            Log.e("Asfafa", "----" + pathList);


            Map<String, Object> listMap = new HashMap<>();

            listMap.put("url", pathList);

            CC.sendCCResult(call_id, CCResult.success(listMap));

            finish();

        }
    }
}
