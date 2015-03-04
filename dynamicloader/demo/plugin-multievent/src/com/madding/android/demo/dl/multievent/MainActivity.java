package com.madding.android.demo.dl.multievent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.madding.android.dl.acitivy.plugin.DLPluginActivity;

public class MainActivity extends DLPluginActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button              invokeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        // that.setContentView(generateContentView(that));
        that.setContentView(R.layout.activity_main);
        invokeButton = (Button) that.findViewById(R.id.invokeButton);
        invokeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(that, "you clicked button", Toast.LENGTH_SHORT).show();
                startActivityForResultByProxy(TestFragmentActivity.class, 0);
            }
        });
    }

//    private View generateContentView(final Context context) {
//        LinearLayout layout = new LinearLayout(context);
//        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        layout.setBackgroundColor(Color.parseColor("#000000"));
//        Button button = new Button(context);
//        button.setText("Start TestActivity");
//        layout.addView(button, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        button.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "you clicked button", Toast.LENGTH_SHORT).show();
//                startActivityForResultByProxy(TestFragmentActivity.class, 0);
//            }
//        });
//        return layout;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult resultCode=" + resultCode);
        if (resultCode == RESULT_FIRST_USER) {
            that.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
