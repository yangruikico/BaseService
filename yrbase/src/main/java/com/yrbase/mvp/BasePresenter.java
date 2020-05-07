package com.yrbase.mvp;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * des:基类presenter
 */
public abstract class BasePresenter<T> {

    public String pageTime, lastTime;

    public T mView;

    public StateLayout stateLayout;

    private CompositeSubscription sCompositeSubscription;

    public void setView(T v) {
        this.mView = v;
        this.onStart();
    }

    public void setStateLayout(StateLayout stateLayout) {
        this.stateLayout = stateLayout;
    }

    public void showLoading() {
        if (stateLayout != null) {
            stateLayout.showLoadingInterface();
        }
    }

    public void showContentView() {
        if (stateLayout != null) {
            stateLayout.showContentViewInterface();
        }
    }

    public void showError() {
        if (stateLayout != null) {
            stateLayout.showErrorInterface();
        }
    }

    public void showNOData() {
        if (stateLayout != null) {
            stateLayout.showNODataInterface();
        }
    }


    public void onStart() {
        if (sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()) {
            sCompositeSubscription = new CompositeSubscription();
        }
    }

    /**
     * 添加Subscription
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        if (sCompositeSubscription != null) {
            sCompositeSubscription.add(subscription);
        } else {
            onStart();
            sCompositeSubscription.add(subscription);
        }
    }

    public void onDestroy() {
        if (sCompositeSubscription != null) {
            sCompositeSubscription.unsubscribe();
        }
        mView = null;
    }

    /**
     * 将文件集合 转换为 MultipartBody.Part 集合
     * @param files
     * @param <T>
     * @return
     */
    public static <T> List<MultipartBody.Part> filesToMultipartBodyPart(List<T> files) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        File file;
        for (int i = 0; i < files.size(); i++) {
            T t = files.get(i);
            if (t instanceof File) file = (File) t;
            else if (t instanceof String)
                file = new File((String) t);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
            else break;

            String path = file.getPath();
            String fileStr = path.substring(path.lastIndexOf(".") + 1);

            //RequestBody requestBody = RequestBody.create(MediaType.parse("image/" + fileStr), file);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/" + fileStr), file);

            MultipartBody.Part part = MultipartBody.Part.createFormData("fileName"+(i+1), file.getName(), requestBody);
            parts.add(part);


        }

        return parts;
    }




}
