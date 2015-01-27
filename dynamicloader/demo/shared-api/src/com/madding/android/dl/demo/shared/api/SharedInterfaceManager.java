package com.madding.android.dl.demo.shared.api;

public class SharedInterfaceManager {

    /**
     * this is just a sample, try not to use static field to avoid memory leak
     */
    private static SharedInterface sharedInterface;

    public static SharedInterface getSharedInterface() {
        return sharedInterface;
    }

    public static void setHostInterface(SharedInterface sharedInterface) {
        SharedInterfaceManager.sharedInterface = sharedInterface;
    }
}
