package com.yrbase.soulpermission.request.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yrbase.soulpermission.bean.Special;
import com.yrbase.soulpermission.callbcak.GoAppDetailCallBack;
import com.yrbase.soulpermission.callbcak.RequestPermissionListener;
import com.yrbase.soulpermission.callbcak.SpecialPermissionListener;
import com.yrbase.soulpermission.debug.PermissionDebug;
import com.yrbase.soulpermission.request.IPermissionActions;


/**
 * @author cd5160866
 */
public class FragmentProxy implements IPermissionActions {

    private static final String TAG = FragmentProxy.class.getSimpleName();

    private IPermissionActions fragmentImp;

    public FragmentProxy(IPermissionActions fragmentImp) {
        this.fragmentImp = fragmentImp;
    }

    @Override
    public void requestPermissions(@NonNull String[] permissions, RequestPermissionListener listener) {
        this.fragmentImp.requestPermissions(permissions, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " request:" + hashCode());
    }

    @Override
    public void requestSpecialPermission(Special permission, SpecialPermissionListener listener) {
        this.fragmentImp.requestSpecialPermission(permission, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " requestSpecial:" + hashCode());
    }

    @Override
    public void goAppDetail(@Nullable GoAppDetailCallBack callBack) {
        this.fragmentImp.goAppDetail(callBack);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " goAppDetail:" + hashCode());
    }

}
