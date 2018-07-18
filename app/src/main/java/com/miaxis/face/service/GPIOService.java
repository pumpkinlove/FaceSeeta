package com.miaxis.face.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class GPIOService extends Service {

    public final String TAG = this.getClass().getSimpleName();
    public IBinder mSuite = null;

    public GPIOService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mSuite == null) {
            mSuite = new GPIOStub(this);
        }
//      ServiceManager.addService("demo", mSuite, false);
//        try {
//            @SuppressWarnings("rawtypes")
//            Class localClass = Class.forName("android.os.ServiceManager");
//            Method addService = localClass.getMethod("addService", new Class[]{String.class, IBinder.class, boolean.class});
//            if (addService != null) {
//                @SuppressWarnings("unused")
//                Object vendorBinder = addService.invoke(localClass, new Object[]{"demo", mSuite, false});
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mSuite;
    }
}
