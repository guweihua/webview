package com.example.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.external.ExternalAdaptManager;


/**
 *
 */
public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {
//public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {


    public static Boolean refresh = false;
    public boolean webSocketFail = false;

    //    public boolean chosesuspend = true;//判断悬浮窗是否开启，true是没打开悬浮窗
    public boolean voiceFloatingWindow = false;//是否有语音悬浮窗


    private ArrayList<Activity> activities = new ArrayList<>();
    private static BaseApp instance;


    public static BaseApp getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        /** 说明:
         *KLog 配置
         */
        if (shouldInit()) {
            instance = this;
            /** 说明:
             *测试初始化,正式发布的时候注视掉
             */
//            Stetho.initialize(
//                    Stetho.newInitializerBuilder(this)
//                            .enableDumpapp(
//                                    Stetho.defaultDumperPluginsProvider(this))
//                            .enableWebKitInspector(
//                                    Stetho.defaultInspectorModulesProvider(this))
//                            .build());


            initApp();
        }

        AutoSize.initCompatMultiProcess(this);

//        customAdaptForExternal();

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);



    }

    private void customAdaptForExternal() {
        /**
         * {@link ExternalAdaptManager} 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 {@link ExternalAdaptManager} 的类注释
         */
//        AutoSizeConfig.getInstance().getExternalAdaptManager()

                //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
                //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
//                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

                //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
                //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
                //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
                //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
                //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
                //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
                //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
                //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
                //经过测试 DefaultErrorActivity 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的

//                .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo(true, 400));
    }


    /**
     * 初始化工作
     */
    protected void initApp() {

    }

    public abstract void initWebsocket();

    protected BaseApp getContext() {
        return this;
    }


    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        System.exit(0);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        activities.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void killApp() {
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }


    public void CloseApp() {
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
    }


    public ArrayList<Activity> getActivity() {
        if (activities != null && activities.size() > 0) {
            return activities;
        }

        return null;
    }


    /**
     * 判断是否是主进程
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();

        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
