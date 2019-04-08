package com.example.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


import com.example.base.R;
import com.example.base.cons.Config;


/**
 * Created by xianguangjin on 16/6/25.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class UiSwitch {
    /**
     * 进入动画
     */
    public static final int enterAnim = R.anim.activity_show_in_amination;
    /**
     * 退出动画
     */
    public static final int exitAnim = R.anim.acitivity_show_out_amination;

    public static void single(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static void bundle(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }

    }

    public static void bundle(Activity context, Class cls, Bundle bundle, View v) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        context.overridePendingTransition(enterAnim, exitAnim);


    }

    public static void singleRes(Activity context, Class cls, int requestCode) {
        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
        if (context instanceof Activity) {
            context.overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }

    public static void singleRes(Fragment context, Class cls, int requestCode) {
        Intent intent = new Intent(context.getContext(), cls);
        context.startActivityForResult(intent, requestCode);

    }

    public static void bundleRes(Activity context, Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (context instanceof Activity) {
            context.overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }

    public static void bundleRes(Fragment context, Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context.getContext(), cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);

    }


}
