package com.example.base.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.base.R;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by xianguangjin on 16/7/5.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */


public class PackageUtils {

    /**
     * App installation location flags of android system
     */
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final String BDMAP = "com.baidu.BaiduMap";
    public static final String WX = "com.tencent.mm";

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 调用系统安装应用
     */
    public static boolean install(Context context, File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);


        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri uri = FileProvider.getUriForFile(context, "com.muyoudaoli.seller.fileprovider", file);
            Uri uri = FileProvider.getUriForFile(context, "com.muxi.ant.fileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        return true;
    }

    /**
     * 调用系统卸载应用
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 打开已安装应用的详情
     */
    public static void goToInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO ? "pkg"
                    : "com.android.settings.ApplicationPkgName"), packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 获取指定程序信息
     */
    public static ActivityManager.RunningTaskInfo getTopRunningTask(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            // 得到当前正在运行的任务栈
            List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
            // 得到前台显示的任务栈
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
            return runningTaskInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public static String getAppVersionName(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    /**
     * 获取当前系统安装应用的默认位置
     *
     * @return APP_INSTALL_AUTO or APP_INSTALL_INTERNAL or APP_INSTALL_EXTERNAL.
     */
//    public static int getInstallLocation() {
//        ShellUtil.CommandResult commandResult = ShellUtil.execCommand(
//                "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm netInstance-install-location", false, true);
//        if (commandResult.result == 0 && commandResult.responseMsg != null && commandResult.responseMsg.length() > 0) {
//            try {
//                return Integer.parseInt(commandResult.responseMsg.substring(0, 1));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        return APP_INSTALL_AUTO;
//    }


    /**
     * netInstance app package info
     */
    public static PackageInfo getAppPackageInfo(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    return pm.getPackageInfo(context.getPackageName(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * whether context is system application
     */
    public static boolean isSystemApplication(Context context) {
        if (context == null) {
            return false;
        }
        return isSystemApplication(context, context.getPackageName());
    }

    /**
     * whether packageName is system application
     */
    public static boolean isSystemApplication(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取已安装的全部应用信息
     */
    public static List<PackageInfo> getInsatalledPackages(Context context) {
        return context.getPackageManager().getInstalledPackages(0);
    }

    /**
     * 获取已安装的全部应用信息
     */
//    public static boolean isInsatalled(Context context, String pkg) {
//        if (!Check.isEmpty(pkg)) {
//            List<PackageInfo> list = getInsatalledPackages(context);
//            if (!Check.isEmpty(list)) {
//                for (PackageInfo pi : list) {
//                    if (pkg.equalsIgnoreCase(pi.packageName)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 获取指定程序信息
     */
    public static ApplicationInfo getApplicationInfo(Context context, String pkg) {
        try {
            return context.getPackageManager().getApplicationInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 启动应用
     */
    public static boolean startAppByPackageName(Context context, String packageName) {
        return startAppByPackageName(context, packageName, null);
    }

    /**
     * 启动应用
     */
    public static boolean startAppByPackageName(Context context, String packageName, Map<String, String> param) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                resolveIntent.setPackage(pi.packageName);
            }

            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packageName1 = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName1, className);

                intent.setComponent(cn);
                if (param != null) {
                    for (Map.Entry<String, String> en : param.entrySet()) {
                        intent.putExtra(en.getKey(), en.getValue());
                    }
                }
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "启动失败",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
