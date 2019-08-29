package com.yrbase.mvp;



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


}
