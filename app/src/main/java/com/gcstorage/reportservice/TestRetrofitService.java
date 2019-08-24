package com.gcstorage.reportservice;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface TestRetrofitService {

    @POST("")
    Observable<TestBaseBean<Object>> getRealTimeCsqMsgList(@Body Map<String, Object> map);

    @Multipart
    @POST("invitation/upload")
    Observable<TestBaseBean<Object>> upLoad(@Part List<MultipartBody.Part> list);

//lowable<BaseResponse<UploadApplyInfo>> uploadImages(@Part List<MultipartBody.Part> list);


    /*l
@QueryMap
@FieldMap


     @Expose
    private String picUrl;


     //上新
    @FormUrlEncoded
    @POST(URL.addGoods)  有json  new Gson().toJson(List)
    Observable<BaseBean<GoodsId>> addGoods(@FieldMap Map<String, String> map);

    l*/


}
