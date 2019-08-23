package com.yrbase.soulpermission.callbcak;

import android.content.Intent;

public interface GoAppDetailCallBack {

    /**
     * 从App详情页回来
     *
     * @param data from onActivityResult
     */
    void onBackFromAppDetail(Intent data);
}
