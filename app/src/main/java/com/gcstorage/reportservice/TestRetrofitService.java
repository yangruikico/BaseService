package com.gcstorage.reportservice;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface TestRetrofitService {
    @Multipart
    @POST("Falcon/2.0/tools/uploadfile_more")
    Observable<TestBaseBean<List<UrlBean>>> upLoad(@Part List<MultipartBody.Part> list);



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
