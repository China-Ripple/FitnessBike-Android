package com.signalripple.fitnessbike;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void addActionBar() {
        // TODO Auto-generated method stub
        ActionBar actionBar = getActionBar();
//        actionBar.set
    }
}
