package com.gcstorage.reportservice;

import android.os.Bundle;

import com.yrbase.baseactivity.BaseActivity;
import com.yrbase.utils.ViewUtil;

import java.util.List;

public class TestActivity extends BaseActivity<TestPresenter.Presenter> implements TestPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getToolBarX().setCenterText(R.string.app_name);

        ViewUtil.Toast(R.string.app_name);
        mPresenter.getStoreAuto();

    }

    @Override
    public void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void onSaveSuccess(List<UrlBean> data) {

    }

    @Override
    public void onError(String str) {

    }
}
