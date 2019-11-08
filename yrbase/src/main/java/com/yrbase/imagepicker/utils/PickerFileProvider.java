package com.yrbase.imagepicker.utils;


import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.yrbase.soulpermission.SoulPermission;
import com.yrbase.soulpermission.debug.PermissionDebug;

/**
 * Time: 2019/7/24 15:43
 * Author:ypx
 * Description:
 */
public class PickerFileProvider extends FileProvider {

    @Override
    public boolean onCreate() {

        PermissionDebug.d(PickerFileProvider.class.getSimpleName(), "auto init");
        SoulPermission.getInstance().autoInit((Application) getContext());


        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
