package com.example.update.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.base.common.BaseActivity;
import com.example.base.cons.MSG;
import com.example.base.utils.Msg;
import com.example.base.utils.SPUtil;
import com.example.base.utils.Toasts;
import com.example.update.R;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.base.utils.RxBus.getDefault;

public class UpgradeActivity extends BaseActivity {


    private TextView tvVersion;
    private TextView tvFillsieze;
    private TextView tvUpdatetime;
    private TextView tvUpdatecontent;
    private LinearLayout llMorecontent;
    private TextView tvDetal;
    private LinearLayout llProblem;
    private LinearLayout buttonLeft;
    private LinearLayout buttonRight;
    private TextView tvLeft, tvRigh;


    private int updateType = 0;//升级策略 1建议 2强制 3手工


    @Override
    public void initListeners() {

        addViewClickEvent(R.id.button_left, v -> {
            Beta.cancelDownload();
            Beta.unregisterDownloadListener();

            getDefault().post(new Msg(MSG.UPDATABUTTON, "1"));
            finish();
        });

        addViewClickEvent(R.id.button_right, v -> {
            Beta.enableNotification = true;

            //升级
            getDefault().post(new Msg(MSG.UPDATABUTTON, "2"));

            DownloadTask task = Beta.startDownload();
            updateBtn(task);
            if (task.getStatus() == DownloadTask.DOWNLOADING) {
                finish();
            }
        });
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {

        tvVersion = findViewById(R.id.tv_version);
        tvFillsieze = findViewById(R.id.tv_fillsieze);
        tvUpdatetime = findViewById(R.id.tv_updatetime);
        tvUpdatecontent = findViewById(R.id.tv_updatecontent);
        llMorecontent = findViewById(R.id.ll_morecontent);
        llProblem = findViewById(R.id.ll_problem);
        buttonLeft = findViewById(R.id.button_left);
        buttonRight = findViewById(R.id.button_right);
        tvLeft = findViewById(R.id.tv_left);
        tvRigh = findViewById(R.id.tv_righ);
        tvDetal = findViewById(R.id.tv_detal);


        if (Beta.getStrategyTask() == null) {

            Log.e("-shy--", "一次没有下载过 ");
        } else {
            Log.e("-shy--", "一下载过 ");

            Beta.cancelDownload();//取消下载
            Beta.unregisterDownloadListener();
        }


        /*获取下载任务，初始化界面信息*/
        updateBtn(Beta.getStrategyTask());
        /*注册下载监听，监听下载事件*/
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                updateBtn(task);
                // tv.setText(task.getSavedLength() + "");
                boolean isNotified = SPUtil.getBoolean("isNotified");
                if (task.getStatus() == DownloadTask.DOWNLOADING && !isNotified) {
                    Toasts.toast(UpgradeActivity.this, getString(R.string.background_download));
                    SPUtil.netInstance().putBoolean("isNotified", true);
                }


                Log.e("-shy--", "onReceive=" + task.getSavedLength());

            }

            @Override
            public void onCompleted(DownloadTask task) {
                updateBtn(task);


                Beta.startDownload();


                Log.e("-shy--", "onCompleted=" + "下载完成");
                // tv.setText(task.getSavedLength() + "");
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                updateBtn(task);
                // tv.setText("failed");

                Log.e("-shy--", "onFailed=" + "下载失败");

            }
        });

        initData();


    }

    public void updateBtn(DownloadTask task) {

        /*根据下载任务状态设置按钮*/


        switch (task.getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED: {
                tvRigh.setText("立即升级");
            }
            break;
            case DownloadTask.COMPLETE: {
                tvRigh.setText("安装");
            }
            break;
            case DownloadTask.DOWNLOADING: {
                tvRigh.setText("暂停");
            }
            break;
            case DownloadTask.PAUSED: {
                tvRigh.setText("继续下载");
            }
            break;
        }
    }

    private void initData() {
        try {
            Log.e("bugly", "Beta.getUpgradeInfo().updateType=" + Beta.getUpgradeInfo().upgradeType);
            if (Beta.getUpgradeInfo().upgradeType == 2) {//强制
                updateType = 2;
                buttonLeft.setVisibility(View.GONE);
            }

            tvVersion.setText("我的" + Beta.getUpgradeInfo().versionName + "版本上线");
            tvFillsieze.setText("包大小:" + getM(Beta.getUpgradeInfo().fileSize) + "M");
            tvUpdatetime.setText("更新时间:" + times(String.valueOf(Beta.getUpgradeInfo().publishTime)));
            tvUpdatecontent.setText(Beta.getUpgradeInfo().newFeature);//升级说明


        } catch (Exception e) {

        }


    }

    public String times(String time) {

        try {
            String ss;
            if (time.length() > 10) {
                ss = time.substring(0, 10);
            } else {
                ss = time;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(ss);
            //int i = Integer.parseInt(time);
            String times = sdf.format(new Date(lcc * 1000L));
            return times;
        } catch (Exception e) {

        }
        return time;


    }


    public String getM(double k) { // 该参数表示kb的值

        try {
            double m;
            m = k / 1024.0 / 1024.0;
            DecimalFormat dfs = new DecimalFormat("0.0");

            String jingdu = dfs.format(m);

            return jingdu; //返回kb转换之后的M值
        } catch (Exception e) {

        }


        return String.valueOf(k / 1024.0 / 1024.0);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (updateType == 2) {//强制升级
                return false;
            } else {


                Beta.cancelDownload();
                Beta.unregisterDownloadListener();
                getDefault().post(new Msg(MSG.UPDATABUTTON, "1"));

                finish();
                return false;
            }
        }
        return false;
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.up_activity_up_grade;
    }


}
