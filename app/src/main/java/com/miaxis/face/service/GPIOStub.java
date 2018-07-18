package com.miaxis.face.service;

import android.app.smdt.SmdtManager;
import android.content.Context;
import android.os.RemoteException;

import com.miaxis.gpioaidl.IGPIOControl;

public class GPIOStub extends IGPIOControl.Stub{

    private Context context;
    private SmdtManager smdtManager;
    public UnsupportedOperationException mException = new UnsupportedOperationException("");

    GPIOStub(Context context) {
        this.context = context;
        smdtManager = new SmdtManager(context);
    }

    @Override
    public int getGpio(int io) throws RemoteException {
        // TODO Auto-generated method stub
        try {
            return smdtManager.smdtReadGpioValue(io);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw mException;
        }
    }

    @Override
    public int setGpio(int io, boolean isTrue) throws RemoteException {
        // TODO Auto-generated method stub
        try {
            return smdtManager.smdtSetGpioValue(io,isTrue);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw mException;
        }
    }
}
