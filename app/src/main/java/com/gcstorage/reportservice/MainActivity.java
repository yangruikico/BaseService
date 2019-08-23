package com.gcstorage.reportservice;

import android.os.Bundle;

import com.yrbase.baseactivity.BaseActivity;
import com.yrbase.utils.ViewUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolBarX().setCenterText(R.string.app_name);

        ViewUtil.Toast(R.string.app_name);




    }
}
