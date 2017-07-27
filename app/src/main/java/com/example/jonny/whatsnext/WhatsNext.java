package com.example.jonny.whatsnext;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jonny on 7/15/2017.
 */

public class WhatsNext extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        WhatsNext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return WhatsNext.context;
    }
}