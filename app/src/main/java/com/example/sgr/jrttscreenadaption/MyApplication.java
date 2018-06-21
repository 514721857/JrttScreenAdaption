package com.example.sgr.jrttscreenadaption;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by toperc on 2018/5/20.
 */

public class MyApplication extends Application {
    public static int screenHeight;
    public static int screenWidth;

    private static MyApplication myApplication = null;

    public static MyApplication getApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        getHeightAndWidth();
    }

    private void getHeightAndWidth(){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        screenWidth= metric.widthPixels; // 屏幕宽度（像素）
        screenHeight = metric.heightPixels; // 屏幕宽度（像素）
    }


}
