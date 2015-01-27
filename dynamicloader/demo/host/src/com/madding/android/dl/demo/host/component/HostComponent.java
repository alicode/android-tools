package com.madding.android.dl.demo.host.component;

import android.content.Context;
import android.widget.Toast;

public class HostComponent {

    public void doSomething(Context context) {
        Toast.makeText(context, "I'm a component in host. I invoke HostComponent.doSomething()", Toast.LENGTH_SHORT).show();
    }
}
