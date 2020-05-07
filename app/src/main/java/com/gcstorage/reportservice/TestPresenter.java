package com.gcstorage.reportservice;


import com.yrbase.mvp.BasePresenter;
import com.yrbase.response.HashMapUtil;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by EA on 2018/4/18
 */

public class TestPresenter {


    public interface View {
        void onSaveSuccess(List<UrlBean> data);
        void onError(String str);
    }


    public static class Presenter extends BasePresenter<View> {
        public void getStoreAuto() {
            HashMapUtil hashMapUtil = new HashMapUtil();

            TestSubscriber<Object> subscriber = new TestSubscriber<Object>() {

                @Override
                public void onSuccess(Object data) {
                    super.onSuccess(data);
                }

                @Override
                public void onError(String str) {
                    super.onError(str);
                }
            };


            //TestLoadUtils.getInstance().observe(TestLoadUtils.mRetrofitService.getRealTimeCsqMsgList(hashMapUtil)).subscribe(subscriber);

        }





        public void uoLoadImage(List<File> datas) {
            TestSubscriber<List<UrlBean>> subscriber = new TestSubscriber<List<UrlBean>>() {

                @Override
                public void onSuccess(List<UrlBean> data) {
                    super.onSuccess(data);
                    mView.onSaveSuccess(data);
                }

                @Override
                public void onError(String str) {
                    super.onError(str);
                    mView.onError(str);
                }
            };

           /* Map<String, RequestBody> bodyMap = new HashMap<>();
            for (int i = 0; i < datas.size(); i++) {

                String name = datas.get(i).getName();

                LogUtil.yangRui().e(name);

                bodyMap.put("fileName"+(i+1)+"\"; filename=\""+ name, RequestBody.create(MediaType.parse("image/png"),datas.get(i)));//"multipart/form-data"
            }
            */


            List<MultipartBody.Part> parts = filesToMultipartBodyPart(datas);


            TestLoadUtils.getInstance().observe(TestLoadUtils.mRetrofitService.upLoad(parts)).subscribe(subscriber);


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
