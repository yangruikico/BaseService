package com.gcstorage.reportservice;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.yrbase.baseactivity.BaseActivity;
import com.yrbase.soulpermission.SoulPermission;
import com.yrbase.soulpermission.bean.Permission;
import com.yrbase.soulpermission.callbcak.CheckRequestPermissionListener;
import com.yrbase.utils.OnPerfectClickListener;
import com.yrbase.utils.ViewUtil;

import java.util.List;

public class TestActivity extends BaseActivity<TestPresenter.Presenter> implements TestPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getToolBarX().setCenterText(R.string.app_name).setRightText("图片选择").setRightTextOnClickListener(new OnPerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ViewUtil.startActivity(ImagePickerActivity.class);
                ViewUtil.Toast("图片选择");
            }
        });

        //ViewUtil.Toast(R.string.app_name);
        mPresenter.getStoreAuto();

        testPermission();
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

    public void testPermission() {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        ViewUtil.Toast(permission.toString() + "\n is ok , you can do your operations");
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        ViewUtil.Toast(permission.toString() + "\n is refused you can not do next things");
                    }
                });
    }

}
