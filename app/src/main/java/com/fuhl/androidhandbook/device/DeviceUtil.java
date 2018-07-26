package com.fuhl.androidhandbook.device;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.fuhl.androidhandbook.MyApplication;
import java.util.UUID;

/**
 * @author tony
 * @date 2018/7/17
 */
public class DeviceUtil {
    public static final int MAXRETRY = 3;

    /**
     * 获取设备ID
     *
     * @return 设备ID
     */
    public static String getDeviceId() {
        return UUID.randomUUID().toString();
    }

    public static String getUserAgent() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Build.MODEL);
        stringBuffer.append(";");
        stringBuffer.append(Build.VERSION.SDK_INT);
        stringBuffer.append(";");
        NetworkInfo localNetworkInfo = ((ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo != null) {
            stringBuffer.append(localNetworkInfo.getTypeName());
            stringBuffer.append(";");
        }
        stringBuffer.append(getChannelValue());
        stringBuffer.append(";");
        stringBuffer.append(getAppVersion(MyApplication.getApplication()));

        return stringBuffer.toString();
    }

    /**
     * 获取渠道名称
     *
     * @return 渠道名称
     */
    public static String getChannelValue() {
        try {
            ApplicationInfo appInfo = MyApplication.getApplication().getPackageManager().getApplicationInfo(MyApplication.getApplication().getPackageName(),
                    PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString("APP_CHANNEL");
            return value;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取版本名称
     * @param ctx 上下文
     * @return 版本名称
     */
    public static String getAppVersion(Context ctx) {
        int mRetryCount = 0;
        String versionName = "";
        PackageInfo pi;
        while (TextUtils.isEmpty(versionName) && mRetryCount < MAXRETRY) {
            try {
                pi = ctx.getPackageManager().getPackageInfo(
                        ctx.getPackageName(), 0);
                versionName = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mRetryCount++;
        }

        return versionName;
    }
}
