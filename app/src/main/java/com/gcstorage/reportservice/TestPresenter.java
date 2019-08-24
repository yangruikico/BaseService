package com.gcstorage.reportservice;


import com.yrbase.custom.MySubscriber;
import com.yrbase.mvp.BasePresenter;
import com.yrbase.response.HashMapUtil;

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

            MySubscriber<Object> subscriber = new MySubscriber<Object>() {

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

    }

}
