package com.example.weihuagu.myprogram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.billy.cc.core.component.CC;
import com.example.base.CP;
import com.example.base.common.BaseActivity;
import com.example.weihuagu.myprogram.widget.MainMenu;
import com.tencent.bugly.beta.Beta;


public class MainActivity extends BaseActivity {


    String url = "http://720yun.com/t/aog8omq6xqsr9oayij&uid=9&PHPSESSID=9hg80t1monauljfo72tfcvjf72";


    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {

        MainMenu mainMenu = findViewById(R.id.main_menu);
        mainMenu.enterHomeFragment();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }




}
