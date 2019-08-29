package com.gcstorage.reportservice;


import com.yrbase.utils.NetworkUtil;
import com.yrbase.utils.ViewUtil;

import rx.Subscriber;

/**
 * Created by kico 2018/5/7on weiniu.
 */

public class TestSubscriber<T> extends Subscriber<TestBaseBean<T>> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtil.isNetworkAvailable()) {
            ViewUtil.Toast("请检查网络");
            onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    public static final int successStatus = 200;
    public static final int loginOutStatus = 409;

    @Override
    public void onNext(TestBaseBean<T> mBaseBean) {
        if (mBaseBean.status == successStatus) {
            if (mBaseBean.success) {
                onSuccess(mBaseBean.data);
            } else {
                //ViewUtil.Toast(mBaseBean.errMsg);
                onError(mBaseBean.errMsg);
            }
        } else if (mBaseBean.status == loginOutStatus) {

            //  ViewUtil.startActivity(LoginOutActivity.class);

        } else {
            onError(mBaseBean.errMsg);
        }
    }

    public void onSuccess(T data) {

    }

    public void onError(String str) {

    }

}
