package com.madding.android.dl.acitivy.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

import com.madding.android.dl.DLClassLoader;
import com.madding.android.dl.acitivy.plugin.DLPluginFragmentActivity;
import com.madding.android.dl.constant.DLConstant;

public class DLProxyFragmentActivity extends FragmentActivity {

    private static final String        TAG = "DLProxyFragmentActivity";

    private String                     mClass;
    private String                     mDexPath;

    private Theme                      mTheme;
    private Resources                  mResources;
    private AssetManager               mAssetManager;
    private ActivityInfo               mActivityInfo;

    protected DLPluginFragmentActivity mPluginFragmentActivity;

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
        if (mActivityInfo.theme > 0) {
            setTheme(mActivityInfo.theme);
        }
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());

        // TODO: handle mActivityInfo.launchMode here.
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
            setPluginFragmentActivity((DLPluginFragmentActivity) instance);
            Log.d(TAG, "instance = " + instance);

            mPluginFragmentActivity.setProxy(this, mDexPath);

            Bundle bundle = new Bundle();
            bundle.putInt(DLConstant.EXTRA_FROM, DLConstant.EXTRA_FROM_EXTERNAL);
            mPluginFragmentActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setPluginFragmentActivity(DLPluginFragmentActivity activity) {
        mPluginFragmentActivity = activity;
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
        mPluginFragmentActivity.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        mPluginFragmentActivity.onStart();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        mPluginFragmentActivity.onRestart();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mPluginFragmentActivity.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPluginFragmentActivity.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPluginFragmentActivity.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPluginFragmentActivity.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPluginFragmentActivity.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mPluginFragmentActivity.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mPluginFragmentActivity.onNewIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        mPluginFragmentActivity.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return mPluginFragmentActivity.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        return mPluginFragmentActivity.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowAttributesChanged(LayoutParams params) {
        mPluginFragmentActivity.onWindowAttributesChanged(params);
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mPluginFragmentActivity.onWindowFocusChanged(hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

}
