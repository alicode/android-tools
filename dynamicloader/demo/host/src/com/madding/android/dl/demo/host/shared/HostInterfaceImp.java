package com.madding.android.dl.demo.host.shared;

import android.content.Context;

import com.madding.android.dl.demo.host.component.HostComponent;
import com.madding.android.dl.demo.shared.api.SharedInterface;

public class HostInterfaceImp implements SharedInterface {

    @Override
    public void hostMethod(Context context) {
        new HostComponent().doSomething(context);
    }
}
