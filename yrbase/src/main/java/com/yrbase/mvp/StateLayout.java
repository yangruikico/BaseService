package com.yrbase.mvp;

/**
 * Created by EA on 2018/3/16
 */

public interface StateLayout {
    void showLoadingInterface();

    void showContentViewInterface();

    void showErrorInterface();

    void showNODataInterface();
}