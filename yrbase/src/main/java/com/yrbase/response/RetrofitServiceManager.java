package com.yrbase.response;


import android.util.Log;

import com.yrbase.BuildConfig;
import com.yrbase.utils.SystemUtil;
import com.yrbase.utils.ViewUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by zhouwei on 16/11/9
 * /**
 * Created by kico 2017/1/14on weiniu..doOnNext()
 * .flatMap()
 */
public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 100;
    private Retrofit mRetrofit;


    public static String ENTERPORT="http://" + "20.51.3.43" + ":" + "8768" ;

    public static Map<String, String> commonParams;


    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间


        String deviceBrand = SystemUtil.getDeviceBrand();
        String systemModel = SystemUtil.getSystemModel();//
        String systemVersion = SystemUtil.getSystemVersion();

        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("mobile-type", "android")
                .addHeaderParams("user-agent", deviceBrand + " : " + systemModel + " android :" + systemVersion)
                .addHeaderParams("app-version", String.valueOf(BuildConfig.VERSION_CODE))
                .build();
        builder.addInterceptor(commonInterceptor);
        builder.addInterceptor(new BodyInterceptor());
        //builder.addInterceptor(new LoggingInterceptor());
        // 创建Retrofit


        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENTERPORT)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }


    public static class BodyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();

            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());


            if (commonParams != null && commonParams.size() > 0) {
                for (String key : commonParams.keySet()) {
                    String value = commonParams.get(key);
                    if (!ViewUtil.isEmpty(key) && !ViewUtil.isEmpty(value)) {
                        authorizedUrlBuilder.addQueryParameter(key, value);
                    }
                }
            }

          /*  String mobile = MyApplication.getUser().getMobile();
            String token = MyApplication.getUser().getToken();
            String storeId = MyApplication.getUser().getStoreId();

            if (!ViewUtil.isEmpty(mobile))
                authorizedUrlBuilder.addQueryParameter(FlagUtil.MOBILE, mobile);
            if (!ViewUtil.isEmpty(token))
                authorizedUrlBuilder.addQueryParameter(FlagUtil.TOKEN, token);
            if (!ViewUtil.isEmpty(storeId))
                authorizedUrlBuilder.addQueryParameter(FlagUtil.STORE_ID, storeId);*/

            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .url(authorizedUrlBuilder.build())
                    .build();
            Response response = chain.proceed(newRequest);
           /* if (response.code() != 200) {//这个地方可以根据返回码做一些事情。通过sendBroadcast发出去。
                ViewUtil.Toast("服务器异常,请稍后重试");
            }*/
            String format = String.format("发送请求 %s on %s%n%s", newRequest.url(), chain.connection(), newRequest.headers());
            Log.e("yangrui","http:"+format);
            return response;
        }
    }

   /* public SSLContext getSSLContext() {
        try {
            // 生成SSLContext对象
            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 从assets中加载证书
            InputStream inStream = MyApplication.getAssets().open("srca.cer");

            // 证书工厂
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
            Certificate cer = cerFactory.generateCertificate(inStream);

            // 密钥库
            KeyStore kStore = KeyStore.getInstance("PKCS12");
            kStore.load(null, null);
            kStore.setCertificateEntry("trust", cer);// 加载证书到密钥库中

            // 密钥管理器
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyFactory.init(kStore, null);// 加载密钥库到管理器

            // 信任管理器
            TrustManagerFactory tFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tFactory.init(kStore);// 加载密钥库到信任管理器

            // 初始化
            sslContext.init(keyFactory.getKeyManagers(), tFactory.getTrustManagers(), new SecureRandom());
            return sslContext;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}
