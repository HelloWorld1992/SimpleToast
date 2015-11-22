package com.belief.simpletoast.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by wh on 2015/11/22.
 */
public class BaseApplication extends Application{

    public static Context context ;

    public BaseApplication(){
        super();
        context = BaseApplication.this;
    }
}
