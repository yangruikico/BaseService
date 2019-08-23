package com.yrbase.soulpermission.request;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;

import com.yrbase.soulpermission.bean.Special;
import com.yrbase.soulpermission.callbcak.GoAppDetailCallBack;
import com.yrbase.soulpermission.callbcak.RequestPermissionListener;
import com.yrbase.soulpermission.callbcak.SpecialPermissionListener;

import static android.os.Build.VERSION_CODES.M;

/**
 * @author cd5160866
 */
public interface IPermissionActions {

    /**
     * 请求权限
     *
     * @param permissions 权限
     * @param listener    回调
     */
    @TargetApi(M)
    void requestPermissions(String[] permissions, RequestPermissionListener listener);


    /**
     * 请求特殊权限
     *
     * @param permission 特殊权限
     * @param listener   回调
     */
    void requestSpecialPermission(Special permission, SpecialPermissionListener listener);

    /**
     * 去应用详情页
     *
     * @param callBack 回调
     */
    void goAppDetail(@Nullable GoAppDetailCallBack callBack);

}
