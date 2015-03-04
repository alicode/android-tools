package com.madding.android.dl.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * looper线程，
 * 
 * @author madding.lip
 */
class LooperThread extends Thread {

    public Handler mHandler;

    public LooperThread(Handler handler){
        this.mHandler = handler;
    }

    public void run() {
        Looper.prepare();
        Message message = Message.obtain();
        message.what = MessageConstant.OP_1;
        mHandler.sendMessage(message);
        Looper.loop();
    }
}
