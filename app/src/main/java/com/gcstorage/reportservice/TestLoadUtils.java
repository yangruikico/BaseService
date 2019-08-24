package com.gcstorage.reportservice;


import com.yrbase.response.ObjectLoader;
import com.yrbase.response.RetrofitServiceManager;

/**
 * Created by EA on 2018/4/18
 */

public class TestLoadUtils extends ObjectLoader {

    private static TestLoadUtils mLoadUtils = null;

    public static TestRetrofitService mRetrofitService = RetrofitServiceManager.getInstance().create(TestRetrofitService.class);
    
    private TestLoadUtils() {

    }

    public static TestLoadUtils getInstance() {


        if (mLoadUtils == null) {
            synchronized (TestLoadUtils.class) {
                if (mLoadUtils == null) {
                    mLoadUtils = new TestLoadUtils();
                }
            }
        }
        return mLoadUtils;
    }
}
