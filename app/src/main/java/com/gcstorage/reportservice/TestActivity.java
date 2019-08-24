package com.gcstorage.reportservice;

import android.os.Bundle;

import com.yrbase.baseactivity.BaseActivity;
import com.yrbase.utils.ViewUtil;

public class TestActivity extends BaseActivity<TestPresenter.Presenter> implements TestPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getToolBarX().setCenterText(R.string.app_name);

        ViewUtil.Toast(R.string.app_name);



       /*


       Map<String, String> commonParams = new HashMap<>();
        commonParams.put("moboile", "18871853529");
        RetrofitServiceManager.ENTERPORT = "http://baidu.com";
        RetrofitServiceManager.commonParams = commonParams;


        */



        mPresenter.getStoreAuto();

    }

    @Override
    public void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void onSaveSuccess() {

    }
}
