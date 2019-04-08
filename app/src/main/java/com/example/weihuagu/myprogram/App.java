package com.example.weihuagu.myprogram;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.example.base.CP;
import com.example.base.utils.SPUtil;
import com.example.base.utils.Toasts;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //开启debug模式  会在Logcat中输出一些CC框架内部的执行日志
        CC.enableDebug(true);
        //开启CC调用日志跟踪   会在Logcat中输出CC调用的详细流程
        CC.enableVerboseLog(true);
        //开启跨app组件调用
        CC.enableRemoteCC(true);

        //初始化Bugly
        initBugly();

    }

    private void initBugly() {
        /**
         * 自定义初始化开关
         */
        Beta.autoInit = true;
        /**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = false;
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */

        Beta.enableNotification = false;

        Beta.showInterruptedStrategy = true;
        Beta.initDelay = 1 * 500;//延迟初始化
        Beta.canShowUpgradeActs.add(MainActivity.class);//添加可显示弹窗的Activity
        Beta.autoDownloadOnWifi = true;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.strUpgradeDialogUpgradeBtn = this.getString(R.string.immediately_update);
        SPUtil.netInstance(getApplicationContext().getApplicationContext()).putBoolean("isNotified", false);


        /**
         * @param isManual  用户手动点击检查，非用户点击操作请传false
         * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
         */
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int i, UpgradeInfo upgradeInfo, boolean isManual, boolean isSilence) {

                Log.e("-onUpgrade--", "b=: " + isManual + "//b1=" + isSilence);

                if (upgradeInfo != null) {
                    if (!isSilence) {//显示弹框
                        Log.e("bugly", "需要更新,存在更新策略");
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                CC.obtainBuilder(CP.CP_UPDATE)
                                        .setActionName(CP.ACTION_UPDATE)
                                        .setContext(getApplicationContext())
                                        .build()
                                        .call();


                            }
                        }, 1000);
                    } else {//不显示弹框

                        toUpdate();

                    }

                } else {

                    Toasts.toast(getApplicationContext(),"不需要更新,没有更新策略");
//                    Log.e("bugly", "不需要更新,没有更新策略");

                }


            }
        };

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {

            @Override
            public void onUpgradeFailed(boolean b) {

            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {

            }

            @Override
            public void onUpgrading(boolean b) {

            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };
        Bugly.init(this, "e185b6ca20", false);

    }

    public void toUpdate() {

//        //先判断是不是强制升级
//        if(Beta.getUpgradeInfo()!=null&&Beta.getUpgradeInfo().upgradeType==2) {//强制
//
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    Intent i = new Intent();
//                    i.setClass(getApplicationContext(), UpgradeActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                }
//            }, 500);
//
//            return;
//        }

        Beta.cancelDownload();
        Beta.unregisterDownloadListener();
        //先获取下载的安装包的状态

        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {

                // tv.setText(task.getSavedLength() + "");
                Log.e("-shy--", "onReceiveApp=" + task.getSavedLength());

            }

            @Override
            public void onCompleted(DownloadTask task) {
                Beta.cancelDownload();
                Beta.unregisterDownloadListener();
                Log.e("-shy--", "onCompletedApp=" + "下载完成");
                // tv.setText(task.getSavedLength() + "");
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                // tv.setText("failed");
                Log.e("-shy--", "onFailedApp=" + "下载失败");

            }
        });


        if (Beta.getStrategyTask() == null) {
            Beta.startDownload();
        } else if (updateBtn(Beta.getStrategyTask()) == 0) {//未下载或者暂停
            Log.e("-shy--", "后台开始下载22" + "//Beta.getStrategyTask()=" + Beta.getStrategyTask());
            Beta.startDownload();
        } else if (updateBtn(Beta.getStrategyTask()) == 1) {
            Beta.checkUpgrade(true, false);
        }
    }

    public int updateBtn(DownloadTask task) {

        if (task == null) {
            return 0;
        }
        int downLoad = 0;
        /*根据下载任务状态设置按钮*/
        switch (task.getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED: {
                downLoad = 0;//一点没有下载
            }

            break;
            case DownloadTask.COMPLETE: {
                downLoad = 1;//下载完成

            }
            break;
            case DownloadTask.DOWNLOADING: {

                downLoad = 0;//下载暂停了

            }
            break;
            case DownloadTask.PAUSED: {

                downLoad = 0;//正在下载

            }
            break;
        }

        return downLoad;
    }


}
