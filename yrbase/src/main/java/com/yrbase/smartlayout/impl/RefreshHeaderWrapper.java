package com.yrbase.smartlayout.impl;


import android.annotation.SuppressLint;
import android.view.View;

import com.yrbase.smartlayout.api.RefreshHeader;
import com.yrbase.smartlayout.internal.InternalAbstract;


/**
 * 刷新头部包装
 * Created by SCWANG on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader/*, InvocationHandler*/ {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
