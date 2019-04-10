package com.example.choosepicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
    //是否圆形
    private Boolean isCrop = false;
    //最多几张
    private int maxNum;
    //1  全部  2 图片  3 视频   4 音频
    private String type = "1";

    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            call_id = extras.getString("call_id");
            isCrop = extras.getBoolean("isCrop");
            maxNum = extras.getInt("num");
            type = extras.getString("type");


        }

        if (type.equals("1")) {
            ImagePictureSelectorUtils.multiSelect(SelectPicActivity.this, maxNum);

        } else if (type.equals("2")) {
            ImagePictureSelectorUtils.selectPic(SelectPicActivity.this, maxNum, isCrop);

        } else if (type.equals("3")) {
            ImagePictureSelectorUtils.choseVideo(SelectPicActivity.this);
        } else if (type.equals("4")) {
            ImagePictureSelectorUtils.choseSound(SelectPicActivity.this);
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


            if (selectList.size() > 0) {
                for (LocalMedia entity : selectList) {
                    pathList.add(entity.getPath());
                }
            }


            Map<String, Object> listMap = new HashMap<>();

            listMap.put("url", pathList);

            CC.sendCCResult(call_id, CCResult.success(listMap));

            finish();

        }
    }
}
