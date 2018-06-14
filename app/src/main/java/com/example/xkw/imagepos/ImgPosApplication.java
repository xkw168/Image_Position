package com.example.xkw.imagepos;

import android.app.Application;
import android.content.Context;

/**
 * Created by xkw on 2018/6/14.
 */

public class ImgPosApplication extends Application {
    private static Context myContext;

    @Override
    public void onCreate() {
        myContext = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return myContext;
    }
}
