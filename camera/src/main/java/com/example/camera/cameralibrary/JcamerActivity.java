package com.example.camera.cameralibrary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.base.utils.Toasts;
import com.example.camera.R;
import com.example.camera.cameralibrary.listener.ClickListener;
import com.example.camera.cameralibrary.listener.JCameraListener;
import com.example.camera.cameralibrary.ottoutils.SendNews;
import com.squareup.otto.Subscribe;

import java.io.File;

//长按录像短按拍照的


public class JcamerActivity extends AppCompatActivity {


    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码


    private boolean granted = false;
    Bundle extras;
    private String call_id;
    private String type;
    private JCameraView jCameraView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcamer_2);

        jCameraView = findViewById(R.id.jcamera_view);

        getPermissions();
        initThings(savedInstanceState);

    }

    protected void initThings(Bundle savedInstanceState) {
        extras = getIntent().getExtras();

        if (extras != null) {
            call_id = extras.getString("call_id");
            type = extras.getString("type");
            if (!TextUtils.isEmpty(type) && type.equals("1")) {
                //发现-随拍
                if (jCameraView.getmCaptureLayout().getTxt_tip() != null) {
                    jCameraView.getmCaptureLayout().getTxt_tip().setText("长按录像");
                }

            }
        }


    }

    public void initData() {
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        //jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath()); //(0.0.8+)设置手动/自动对焦，默认为自动对焦

        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        //设置视频质量
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(String url) {
                if (url == null) {
                    Toasts.toast(JcamerActivity.this, getString(R.string.cannot_support_shot));
                    finish();
                    return;
                }


                if (!TextUtils.isEmpty(type) && type.equals("1")) {
                    Toasts.toast(JcamerActivity.this, "随拍只支持视频");
                    return;
                }
//
                CC.sendCCResult(call_id, CCResult.success("url", url).addData("videotype", "1"));//拍照
                JcamerActivity.this.finish();

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {

                //录像 ---可以得到路径
                if (url == null) {
                    Toasts.toast(JcamerActivity.this, getString(R.string.cannot_support_shot));
                    finish();
                    return;

                }
                CC.sendCCResult(call_id, CCResult.success("url", url).addData("videotype", "2"));//拍摄

                JcamerActivity.this.finish();
            }

            @Override
            public void quit() {
                JcamerActivity.this.finish();

            }
        });
        //左边按钮点击事件
        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                JcamerActivity.this.finish();
            }
        });


    }


//    public void sendNews(String type, String path) {//发送消息
//        SendChoseType sendtity = new SendChoseType();
//        sendtity.type = type;
//        sendtity.path = path;
//        sendtity.time = null;
//        getDefault().post(new Msg(com.cons.MSG.QA_DETE_video, sendtity));
//
//    }


    /**
     * 获取权限
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //具有权限
                granted = true;
                if (granted) {
                    initData();
                }
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(JcamerActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
                granted = false;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (granted) {
            jCameraView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        AppBus.getInstance().unregister(this);
    }


    @Subscribe
    public void getNews(SendNews data) {
        if (null != data) {

            if (data.getType() != null && data.getType().equals("1")) {//录像

                String url = data.getSs();
                //录像 ---可以得到路径
                if (url == null) {
                    Toasts.toast(JcamerActivity.this, getString(R.string.cannot_support_shot));
                    finish();
                    return;

                }

                CC.sendCCResult(call_id, CCResult.success("url", url).addData("videotype", "2"));//拍摄

                JcamerActivity.this.finish();

            } else if (data.getType() != null && data.getType().equals("2")) {
                String url = data.getSs();
                if (url == null) {
                    Toasts.toast(JcamerActivity.this, getString(R.string.cannot_support_shot));
                    JcamerActivity.this.finish();
                    return;
                }
//
                CC.sendCCResult(call_id, CCResult.success("url", url).addData("videotype", "1"));//拍照

                JcamerActivity.this.finish();


            } else if (data.getType() != null && data.getType().equals("3")) {
                JcamerActivity.this.finish();
            }


        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }


        //注册到bus事件总线中
        AppBus.getInstance().register(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    granted = true;
                    if (granted) {

                        initData();
                        jCameraView.onResume();
                    }


                } else {
                    Toasts.toast(this, "请到设置-权限管理中开启");
                    finish();
                }
            }
        }
    }
}
