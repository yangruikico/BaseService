package com.yrbase.soulpermission.adapter;


import com.yrbase.soulpermission.bean.Permission;
import com.yrbase.soulpermission.callbcak.CheckRequestPermissionListener;

/**
 * @author cd5160866
 */
public abstract class SimplePermissionAdapter implements CheckRequestPermissionListener {

    @Override
    public void onPermissionOk(Permission permission) {

    }

    @Override
    public void onPermissionDenied(Permission permission) {

    }
}
