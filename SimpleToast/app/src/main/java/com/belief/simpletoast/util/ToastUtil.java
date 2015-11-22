package com.belief.simpletoast.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.belief.simpletoast.R;
import com.belief.simpletoast.base.BaseApplication;

/**
 * Android系统自带的Toast是通过系统NotificationManager管理的，这样会有一个问题：
 * 有的机型例如魅族，如果用户把通知栏消息权限关掉后，Toast会无法显示。
 * 下面这个自定义的Toast是不依赖NotificationManager的。
 * Created by wh on 2015/11/22.
 */
public class ToastUtil {
    public static final int LENGTH_LONG = 3500; //3.5 秒
    public static final int LENGTH_SHORT = 2000; //2秒

    private static View mToastView;
    private static int mGravity, mX, mY;
    private static final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private static WindowManager mWindowManager;
    private static Handler mHandler = new Handler();

    private static void init(){
        mY = BaseApplication.context.getResources().getDimensionPixelSize(R.dimen.toast_height);
        mGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        LayoutInflater inflater = (LayoutInflater) BaseApplication.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //使用自定义的布局
        mToastView = inflater.inflate(R.layout.toast_layout, null);

        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.ToastAnimation;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.packageName = BaseApplication.context.getPackageName();
        mParams.gravity = mGravity;
        if((mGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL){
            mParams.horizontalWeight = 1.0f;
        }
        if((mGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL){
            mParams.verticalWeight = 1.0f;
        }
        mParams.x = mX;
        mParams.y = mY;
        mWindowManager = (WindowManager)BaseApplication.context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static void show(final CharSequence text, int duration){
        if(mWindowManager == null || mToastView == null){
            init();
        }
        mHandler.removeCallbacks(cancelRunnable);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TextView messageTv = (TextView)mToastView.findViewById(R.id.message);
                messageTv.setText(text);
                if(mToastView.getParent() != null){
                    mWindowManager.removeViewImmediate(mToastView);
                }
                mWindowManager.addView(mToastView, mParams);
            }
        });
        mHandler.postDelayed(cancelRunnable, duration);
    }

    public static void showLong(String tips){
        show(tips, LENGTH_LONG);
    }

    public static void showShort(String tips){
        show(tips, LENGTH_SHORT);
    }

    private static Runnable cancelRunnable = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

    public static void cancel(){
        if(mToastView != null && mToastView.getParent() != null){
            mWindowManager.removeViewImmediate(mToastView);
        }
    }
}
