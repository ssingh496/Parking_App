package com.azhar.android.parking_application;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by 212614814 on 09/01/2018.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
