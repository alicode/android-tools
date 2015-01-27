package com.madding.android.dl.acitivy.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.madding.android.dl.acitivy.IDLPluginActivity;
import com.madding.android.dl.constant.DLConstant;
import com.madding.android.dl.util.DLUtil;

public class DLPluginActivity extends Activity implements IDLPluginActivity {

    private static final String TAG   = DLPluginActivity.class.getSimpleName();

    protected Activity          that;
    protected int               mFrom = DLConstant.EXTRA_FROM_INTERNAL;
    protected String            mDexPath;

    public void setProxy(Activity proxyActivity, String dexPath) {
        Log.d(TAG, "setProxy: proxyActivity= " + proxyActivity + ", dexPath= " + dexPath);
        that = proxyActivity;
        mDexPath = dexPath;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt(DLConstant.EXTRA_FROM, DLConstant.EXTRA_FROM_INTERNAL);
        }
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            that = this;
        }

        Log.d(TAG, "onCreate: from= "
                   + (mFrom == DLConstant.EXTRA_FROM_INTERNAL ? "DLConstants.EXTRA_FROM_INTERNAL" : "FROM_EXTERNAL"));
    }

    protected void startActivityByProxy(Class<?> cls) {
        startActivityByProxy(cls.getName());
    }

    protected void startActivityForResultByProxy(Class<?> cls, int requestCode) {
        startActivityForResultByProxy(cls.getName(), requestCode);
    }

    protected void startActivityByProxy(String className) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            Intent intent = new Intent();
            intent.setClassName(this, className);
            that.startActivity(intent);
        } else {
            Intent intent = new Intent(DLUtil.getProxyViewAction(className, getClassLoader()));
            intent.putExtra(DLConstant.EXTRA_DEX_PATH, mDexPath);
            intent.putExtra(DLConstant.EXTRA_CLASS, className);
            that.startActivity(intent);
        }
    }

    public void startActivityForResultByProxy(String className, int requestCode) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            Intent intent = new Intent();
            intent.setClassName(this, className);
            that.startActivityForResult(intent, requestCode);
        } else {
            Intent intent = new Intent(DLUtil.getProxyViewAction(className, getClassLoader()));
            intent.putExtra(DLConstant.EXTRA_DEX_PATH, mDexPath);
            intent.putExtra(DLConstant.EXTRA_CLASS, className);
            that.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void setContentView(View view) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.setContentView(view);
        } else {
            that.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.setContentView(view, params);
        } else {
            that.setContentView(view, params);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            that.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, LayoutParams params) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.addContentView(view, params);
        } else {
            that.addContentView(view, params);
        }
    }

    @Override
    public View findViewById(int id) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.findViewById(id);
        } else {
            return that.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getIntent();
        } else {
            return that.getIntent();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getClassLoader();
        } else {
            return that.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getResources();
        } else {
            return that.getResources();
        }
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getLayoutInflater();
        } else {
            return that.getLayoutInflater();
        }
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getSharedPreferences(name, mode);
        } else {
            return that.getSharedPreferences(name, mode);
        }
    }

    @Override
    public Context getApplicationContext() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getApplicationContext();
        } else {
            return that.getApplicationContext();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getWindowManager();
        } else {
            return that.getWindowManager();
        }
    }

    @Override
    public Window getWindow() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getWindow();
        } else {
            return that.getWindow();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.getSystemService(name);
        } else {
            return that.getSystemService(name);
        }
    }

    @Override
    public void finish() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.finish();
        } else {
            that.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onSaveInstanceState(outState);
        }
    }

    public void onNewIntent(Intent intent) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onResume() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onDestroy();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            return super.onKeyUp(keyCode, event);
        }
        return false;
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onWindowAttributesChanged(params);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onWindowFocusChanged(hasFocus);
        }
    }
}
