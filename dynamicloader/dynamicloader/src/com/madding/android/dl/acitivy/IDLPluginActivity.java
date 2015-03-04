package com.madding.android.dl.acitivy;

import android.app.Activity;

/**
 * 代理Activity组件的接口类
 * 
 * @author madding.lip
 */
public interface IDLPluginActivity {

    /**
     * 设置pluginActivity对应的代理者和获取路径
     * 
     * @param proxyActivity代理的activity
     * @param dexPath 插件的dex路径
     */
    public void setProxy(Activity proxyActivity, String dexPath);
}
