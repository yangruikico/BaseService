package com.yrbase.soulpermission.adapter;


import com.yrbase.soulpermission.bean.Permission;
import com.yrbase.soulpermission.callbcak.CheckRequestPermissionsListener;

/**
 * @author cd5160866
 */
public abstract class SimplePermissionsAdapter implements CheckRequestPermissionsListener {
    @Override
    public void onAllPermissionOk(Permission[] allPermissions) {

    }

    @Override
    public void onPermissionDenied(Permission[] refusedPermissions) {

    }
}
