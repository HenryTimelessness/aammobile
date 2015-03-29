package com.henrychua.mydailyassessment.helpers;

import android.app.Application;
import android.content.Context;

import com.orm.SugarApp;

import java.net.CookieHandler;
import java.net.CookieManager;


/**
 * MyApplication class for getting the app context from anywhere, this is especially useful if you
 * want to get the appcontext from your own non-activity class that you created
 * Have to declare the  name in <application></application> in AndroidManifest.xml
 * i.e <application android:name="com.xyz.MyApplication"></application>
 * call MyApplication.getAppContext() to get your application context statically.
 * Created by henrychua on 07/11/2014.
 */
public class MyApplication extends SugarApp {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    /**
     * static method to get the App context
     * @return
     */
    public static Context getAppContext() {
        return MyApplication.context;
    }
}