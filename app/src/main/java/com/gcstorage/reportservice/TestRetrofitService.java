package com.gcstorage.reportservice;

import com.yrbase.custom.BaseBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface TestRetrofitService {

    @POST("")
    Observable<BaseBean<Object>> getRealTimeCsqMsgList(@QueryMap Map<String, Object> map);

}
