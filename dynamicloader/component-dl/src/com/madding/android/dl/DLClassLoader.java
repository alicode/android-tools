package com.madding.android.dl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class DLClassLoader extends DexClassLoader {

    private static final String                     TAG                 = DLClassLoader.class.getSimpleName();

    private static final Map<String, DLClassLoader> mPluginClassLoaders = new ConcurrentHashMap<String, DLClassLoader>();

    protected DLClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent){
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    public static DLClassLoader getClassLoader(String dexPath, Context context, ClassLoader parentLoader) {
        Log.d(TAG, "DLClassLoader.getClassLoader(), dexPath=" + dexPath);
        DLClassLoader dLClassLoader = mPluginClassLoaders.get(dexPath);
        if (dLClassLoader != null) return dLClassLoader;

        File dexOutputDir = context.getDir("dex", Context.MODE_PRIVATE);
        if (dexOutputDir == null || !dexOutputDir.exists()) {
            return null;
        }
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        dLClassLoader = new DLClassLoader(dexPath, dexOutputPath, null, parentLoader);
        mPluginClassLoaders.put(dexPath, dLClassLoader);

        return dLClassLoader;
    }
}
