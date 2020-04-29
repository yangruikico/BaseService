package com.gcstorage.reportservice;

import android.app.Application;

import com.gcstorage.reportservice.imageutil.GlideLoader;
import com.gcstorage.reportservice.imageutil.ImageLoader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setImageLoader();
    }

    //在Application中设置
    private void setImageLoader() {
        ImageLoader.getInstance().setImageLoader(new GlideLoader());
    }
}
