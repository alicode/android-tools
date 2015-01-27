package com.madding.android.dl.acitivy.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

import com.madding.android.dl.DLClassLoader;
import com.madding.android.dl.acitivy.plugin.DLPluginActivity;
import com.madding.android.dl.constant.DLConstant;

public class DLProxyActivity extends Activity {

    private static final String TAG = "DLProxyActivity";

    private String              mClass;
    private String              mDexPath;

    private Theme               mTheme;
    private Resources           mResources;
    private AssetManager        mAssetManager;
    private ActivityInfo        mActivityInfo;

    protected DLPluginActivity  mPluginActivity;        // java虚拟机中的activity类，不包含android的环境信息

    protected void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mDexPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    private void initializeActivityInfo() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mDexPath, 1);
        if ((packageInfo.activities != null) && (packageInfo.activities.length > 0)) {
            if (mClass == null) {
                mClass = packageInfo.activities[0].name;
            }
            for (ActivityInfo a : packageInfo.activities) {
                if (a.name.equals(mClass)) {
                    mActivityInfo = a;
                }
            }
        }
    }

    private void handleActivityInfo() {
        Log.d(TAG, "handleActivityInfo, theme=" + mActivityInfo.theme);
        if (mActivityInfo.theme > 0) {
            setTheme(mActivityInfo.theme);
        }
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDexPath = getIntent().getStringExtra(DLConstant.EXTRA_DEX_PATH);
        mClass = getIntent().getStringExtra(DLConstant.EXTRA_CLASS);
        Log.d(TAG, "mClass=" + mClass + " mDexPath=" + mDexPath);

        loadResources();
        initializeActivityInfo();
        handleActivityInfo();
        launchTargetActivity(mClass);
    }

    protected void launchTargetActivity() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mDexPath, 1);
        if ((packageInfo.activities != null) && (packageInfo.activities.length > 0)) {
            String activityName = packageInfo.activities[0].name;
            mClass = activityName;
            launchTargetActivity(mClass);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void launchTargetActivity(final String className) {
        Log.d(TAG, "start launchTargetActivity, className=" + className);
        try {
            Class<?> localClass = getClassLoader().loadClass(className);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
            Object instance = localConstructor.newInstance(new Object[] {});

            Log.d(TAG, "instance = " + instance);
            setPluginActivity((DLPluginActivity) instance);
            mPluginActivity.setProxy(this, mDexPath);

            Bundle bundle = new Bundle();
            bundle.putInt(DLConstant.EXTRA_FROM, DLConstant.EXTRA_FROM_EXTERNAL);
            mPluginActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setPluginActivity(DLPluginActivity activity) {
        mPluginActivity = activity;
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }

    @Override
    public ClassLoader getClassLoader() {
        return DLClassLoader.getClassLoader(mDexPath, getApplicationContext(), super.getClassLoader());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPluginActivity.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        mPluginActivity.onStart();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        mPluginActivity.onRestart();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mPluginActivity.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPluginActivity.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPluginActivity.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPluginActivity.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPluginActivity.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mPluginActivity.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mPluginActivity.onNewIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        mPluginActivity.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return mPluginActivity.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        return mPluginActivity.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowAttributesChanged(LayoutParams params) {
        mPluginActivity.onWindowAttributesChanged(params);
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mPluginActivity.onWindowFocusChanged(hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

}
