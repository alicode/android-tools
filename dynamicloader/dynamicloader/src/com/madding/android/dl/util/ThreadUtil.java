package com.madding.android.dl.util;

import android.os.Looper;
import android.view.View;

/**
 * 线程操作工具类
 * 
 * @author madding.lip
 */
public class ThreadUtil {

    /**
     * 检查是否在主线程
     * 
     * @return 是否在主线程
     */
    public static boolean checkMainThread() {
        return Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean checkMainThread2() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    public void enableView(final View view, final boolean enabled) {
        if (view == null) return;
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            view.setEnabled(enabled);
        } else {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    enableView(view, enabled);
                }
            });
        }
    }

    private void runOnUiThread(Runnable runnable) {
        // TODO Auto-generated method stub

    }

}
