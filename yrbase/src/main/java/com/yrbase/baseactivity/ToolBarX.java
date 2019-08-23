package com.yrbase.baseactivity;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yrbase.R;


/**
 * Created by kico 2017/6/9on weiniu.
 */

public class ToolBarX {
    private TextView right_title_tv;
    private RelativeLayout rl_title_right;
    private TextView middle_title_tv;
    private RelativeLayout rl_title_center;
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;

    public ToolBarX(final AppCompatActivity activity, Toolbar toolbar) {
        this.mToolbar = toolbar;
        this.mActivity = activity;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        rl_title_center = mToolbar.findViewById(R.id.rl_title_center);
        middle_title_tv = mToolbar.findViewById(R.id.middle_title_tv);
        rl_title_right = mToolbar.findViewById(R.id.rl_title_right);
        right_title_tv = mToolbar.findViewById(R.id.right_title_tv);
        mActionBar.setTitle("");
        if (null != mActionBar) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        mToolbar.setNavigationIcon(R.mipmap.back_white);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    /************************************* 中间标题文本处理 *****************************************/
    public ToolBarX setCustomView(View view) {
        rl_title_center.removeAllViews();
        rl_title_center.addView(view);
        return this;
    }

    public ToolBarX setCenterText(int resID) {
        if (null != middle_title_tv) {
            middle_title_tv.setText(resID);
        }
        return this;
    }


    public ToolBarX setCenterText(String string) {
        if (null != middle_title_tv && !TextUtils.isEmpty(string)) {
            middle_title_tv.setText(string);
        }
        return this;
    }

    public ToolBarX setCenterTextOnClickListener(View.OnClickListener listener) {
        middle_title_tv.setOnClickListener(listener);
        return this;
    }

    public ToolBarX setCenterTextDrawRight(int resID) {
        Drawable drawable = middle_title_tv.getContext().getResources().getDrawable(resID);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        middle_title_tv.setCompoundDrawables(null, null, drawable, null);
        return this;
    }

    public ToolBarX setCenterTextColor(int resID) {
        if (null != middle_title_tv) {
            middle_title_tv.setTextColor(ContextCompat.getColor(middle_title_tv.getContext(), resID));
        }
        return this;
    }

    /************************************* 右侧文本处理起始 *****************************************/

    public ToolBarX setRightView(View view) {
        rl_title_right.removeAllViews();
        rl_title_right.addView(view);
        return this;
    }

    public ToolBarX setRightText(int resID) {
        if (null != right_title_tv) {
            right_title_tv.setText(resID);
        }
        return this;
    }


    public ToolBarX setRightDrawableID(int DrawableID) {
        if (null != right_title_tv) {
            Drawable drawable = right_title_tv.getContext().getResources().getDrawable(DrawableID);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            right_title_tv.setCompoundDrawables(null, null, drawable, null);
        }
        return this;
    }

    public ToolBarX setRightText(String string) {
        if (null != right_title_tv) {
            right_title_tv.setText(string);
        }
        return this;
    }

    public ToolBarX setRightTextColor(int resID) {
        if (null != right_title_tv) {
            right_title_tv.setTextColor(ContextCompat.getColor(right_title_tv.getContext(), resID));
        }
        return this;
    }

    /************************************* 右侧文本处理结束 *****************************************/

    public ToolBarX setNavigationIcon(int resID) {
        mToolbar.setNavigationIcon(resID);
        return this;
    }

    public ToolBarX setDisplayHomeAsUpEnabled(boolean flag) {
        mActionBar.setDisplayHomeAsUpEnabled(flag);
        return this;
    }

    public ToolBarX setTitle(String text) {
        if (text != null) {
            mActionBar.setTitle(text);
        }
        return this;
    }


    public ToolBarX setSubTitle(String text) {
        if (text != null) {
            mActionBar.setSubtitle(text);
        }
        return this;
    }

    public ToolBarX setTitle(int resID) {
        mActionBar.setTitle(resID);
        return this;
    }

    public ToolBarX setSubTitle(int resID) {
        mActionBar.setSubtitle(resID);
        return this;
    }

    public ToolBarX setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    public ToolBarX setRightTextOnClickListener(View.OnClickListener listener) {
        rl_title_right.setOnClickListener(listener);
        return this;
    }


    public RelativeLayout getRightView() {
        return rl_title_right;
    }

    public ToolBarX setRightTextOnLongClickListener(View.OnLongClickListener listener) {
        rl_title_right.setOnLongClickListener(listener);
        return this;
    }

    public void setVisible(boolean visible) {
        mToolbar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightVisible(int visibility) {
        rl_title_right.setVisibility(visibility);
    }

    public void setVisible(int visibility) {
        mToolbar.setVisibility(visibility);
    }
}
