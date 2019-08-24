package com.gcstorage.reportservice;


import com.yrbase.mvp.BasePresenter;
import com.yrbase.response.HashMapUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by EA on 2018/4/18
 */

public class TestPresenter {


    public interface View {
        void onSaveSuccess();
    }


    public static class Presenter extends BasePresenter<View> {
        public void getStoreAuto() {
            HashMapUtil hashMapUtil = new HashMapUtil();

            TestSubscriber<Object> subscriber = new TestSubscriber<Object>() {

                @Override
                public void onSuccess(Object data) {
                    super.onSuccess(data);
                    mView.onSaveSuccess();
                }

                @Override
                public void onError(String str) {
                    super.onError(str);
                }
            };


            TestLoadUtils.getInstance().observe(TestLoadUtils.mRetrofitService.getRealTimeCsqMsgList(hashMapUtil)).subscribe(subscriber);

        }





    public void uoLoadImage() {
        TestSubscriber<Object> subscriber = new TestSubscriber<Object>() {

            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
                mView.onSaveSuccess();
            }

            @Override
            public void onError(String str) {
                super.onError(str);
            }
        };

        List<MultipartBody.Part> mList = new ArrayList<>();

        File file = new File("");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part.createFormData("file$i", file.getName(), requestFile);

        TestLoadUtils.getInstance().observe(TestLoadUtils.mRetrofitService.upLoad(mList)).subscribe(subscriber);

    }
    }


   /*

//下载图片
    @GET
    Observable<ResponseBody> downloadPicFromNet(@retrofit2.http.Url String fileUrl);


   @POST(URL.getServerPhone)
    Observable<BaseBean<ServerConfigBean>> getServerPhone();


    @POST(URL.getServerPhone)
    Observable<BaseBean<Object>> test();



        Observable.just("")
        .map(new Func1<String, File>() {
        @Override
        public File call(String s) {
            return new File(s);
        }
    }).flatMap(new Func1<File, Observable<BaseBean<ServerConfigBean>>>() {
        @Override
        public Observable<BaseBean<ServerConfigBean>> call(File o) {
            return LoadUtils.getInstance()
                    .observe(LoadUtils.mRetrofitService.getServerPhone());
        }
    })
            .map(new Func1<BaseBean<ServerConfigBean>, String>() {
        @Override
        public String call(BaseBean<ServerConfigBean> serverConfigBeanBaseBean) {


            return "serverConfigBeanBaseBean.geturl";
        }
    })
            .flatMap(new Func1<String, Observable<BaseBean<Object>>>() {
        @Override
        public Observable<BaseBean<Object>> call(String serverConfigBeanBaseBean) {



            return LoadUtils.getInstance()
                    .observe(LoadUtils.mRetrofitService.test());

        }
    })
            .subscribe(new Subscriber<BaseBean<Object>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(BaseBean<Object> objectBaseBean) {

        }
    });


    */
}
