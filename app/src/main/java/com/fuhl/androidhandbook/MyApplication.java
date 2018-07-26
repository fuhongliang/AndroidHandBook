package com.fuhl.androidhandbook;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author tony
 * @date 2018/7/17
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    /**
     * 获取application实例
     * @return application实例
     */
    public static Context getApplication() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
