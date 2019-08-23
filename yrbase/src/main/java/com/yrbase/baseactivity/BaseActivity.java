package com.yrbase.baseactivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yrbase.R;
import com.yrbase.baseactivity.utils.AppManager;


/**
 * Created by zjs on 2018/3/30.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private FrameLayout rlContent;
    private Toolbar toolbar;
    private ToolBarX mToolBarX;


    public ToolBarX getToolBarX() {
        if (mToolBarX == null) {
            mToolBarX = new ToolBarX(this, toolbar);
        }
        mToolBarX.setVisible(View.VISIBLE);
        return mToolBarX;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initContentView();
        mToolBarX = new ToolBarX(this, toolbar);

        AppManager.getAppManager().addActivity(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, rlContent, true);
    }

    private void initContentView() {//公共的头
        ViewGroup content = findViewById(android.R.id.content);
        content.removeAllViews();
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(contentLayout);
        LayoutInflater.from(this).inflate(R.layout.activity_base, contentLayout, true);
        rlContent = contentLayout.findViewById(R.id.rlContent);
        toolbar = findViewById(R.id.tool_bar);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
