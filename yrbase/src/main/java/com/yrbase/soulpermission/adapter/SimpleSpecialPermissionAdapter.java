package com.yrbase.soulpermission.adapter;


import com.yrbase.soulpermission.bean.Special;
import com.yrbase.soulpermission.callbcak.SpecialPermissionListener;

/**
 * @author cd5160866
 */
public abstract class SimpleSpecialPermissionAdapter implements SpecialPermissionListener {

    @Override
    public void onDenied(Special permission) {

    }

    @Override
    public void onGranted(Special permission) {

    }
}
