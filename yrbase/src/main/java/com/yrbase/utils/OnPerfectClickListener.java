package com.yrbase.utils;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * 避免在1秒内出发多次点击
 * Created by yangcai on 2016/1/15.
 */
public abstract class OnPerfectClickListener implements OnClickListener {
    private int MIN_CLICK_DELAY_TIME = 1500;
    private long lastClickTime = 0;
    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = v.getId();
        if (id != mId) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);

    public OnPerfectClickListener(int MIN_CLICK_DELAY_TIME) {
        this.MIN_CLICK_DELAY_TIME = MIN_CLICK_DELAY_TIME;
    }

    public OnPerfectClickListener() {
    }
}
