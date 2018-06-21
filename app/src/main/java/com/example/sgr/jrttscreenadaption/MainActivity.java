package com.example.sgr.jrttscreenadaption;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.sgr.jrttscreenadaption.MyApplication;
import com.example.sgr.jrttscreenadaption.R;
import com.example.sgr.jrttscreenadaption.ScreenAdapt;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    TextView text1,text2,text3,text4,text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();       //去掉状态栏

        ScreenAdapt.setCustomDensity(this, MyApplication.getApplication());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        text5=(TextView)findViewById(R.id.text5);
        getDisplayInfomation();
        text3.setText("尺寸"+getScreenInch(this));
        // 通过WindowManager获取
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("Activitydm"+dm.density);
        text4.setText("分辨率width-display :" + dm.widthPixels+"heigth-display :" + dm.heightPixels);
        text1.setText("像素密度 "+Math.sqrt(dm.widthPixels*dm.widthPixels+dm.heightPixels*dm.heightPixels)/getScreenInch(this));

        text5.setText("dp获取宽"+convertPixelToDp( dm.widthPixels)+"高"+convertPixelToDp(dm.heightPixels));

    }

    private int convertPixelToDp(int pixel) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int)(pixel/displayMetrics.density);
    }


    private void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);


//        Log.d(TAG,);
        //获取正确尺寸 （此方法要求最低api为17，即安卓4.2，4.2之前获取请参看以下获取屏幕尺寸的方法）
        getWindowManager().getDefaultDisplay().getRealSize(point);
        text2.setText("the screen real size is "+point.toString());
    }

    private static double mInch = 0;
    /**
     * 获取屏幕尺寸
     * @param context
     * @return
     */
    public static double getScreenInch(Activity context) {
        if (mInch != 0.0d) {
            return mInch;
        }

        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            mInch =formatDouble(Math.sqrt((realWidth/metrics.xdpi) * (realWidth /metrics.xdpi) + (realHeight/metrics.ydpi) * (realHeight / metrics.ydpi)),1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }
    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d,int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
