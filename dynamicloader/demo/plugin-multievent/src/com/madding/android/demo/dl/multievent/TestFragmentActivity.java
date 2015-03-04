package com.madding.android.demo.dl.multievent;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.madding.android.dl.acitivy.plugin.DLPluginFragmentActivity;
import com.madding.android.dl.constant.DLConstant;

public class TestFragmentActivity extends DLPluginFragmentActivity implements OnClickListener {

    private static final String TAG = TestFragmentActivity.class.getSimpleName();

    private EditText            mEditText;
    private ImageView           mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        mEditText = (EditText) findViewById(R.id.etDisplay);
        mEditText.setText(R.string.etDisplay);

        mImageView = (ImageView) findViewById(R.id.ivDisplay);
        mImageView.setImageResource(R.drawable.display);

        TestButton bReturnBack = (TestButton) findViewById(R.id.bReturnback);
        bReturnBack.setText(that.getResources().getString(R.string.bReturnback));
        bReturnBack.setOnClickListener(this);

        Button bShowFragment = (Button) findViewById(R.id.bShowFragment);
        bShowFragment.setOnClickListener(this);

        Button bDeleteFragment = (Button) findViewById(R.id.bDeleteFragment);
        bDeleteFragment.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        if (mFrom == DLConstant.EXTRA_FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bShowFragment:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, new TestFragment());
                transaction.addToBackStack("TestFragment#1");
                transaction.commit();
                Toast.makeText(that, "add fragment success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bReturnback:
                Toast.makeText(that, "quit : " + mDexPath, Toast.LENGTH_SHORT).show();
                that.setResult(RESULT_FIRST_USER);
                that.finish();
                break;
            case R.id.bDeleteFragment:
                this.getSupportFragmentManager().popBackStack();
                Toast.makeText(that, "remove fragment success", Toast.LENGTH_SHORT).show();
            default:
                break;
        }

    }

}
