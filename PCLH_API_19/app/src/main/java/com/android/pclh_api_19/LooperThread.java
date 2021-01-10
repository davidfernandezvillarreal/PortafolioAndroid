package com.android.pclh_api_19;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class LooperThread extends Thread {
    public Handler mHandler;
    Looper looper;

    public void run() {
        looper.prepare();

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // process incoming messages here

            }
        };

        looper.loop();
        looper.quit();
    }
}
