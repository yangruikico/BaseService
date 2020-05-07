package com.gcstorage.reportservice;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ninetripods.aopermission.permissionlib.annotation.NeedPermission;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionCanceled;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionDenied;
import com.ninetripods.aopermission.permissionlib.bean.CancelBean;
import com.ninetripods.aopermission.permissionlib.bean.DenyBean;
import com.yrbase.baseactivity.BaseActivity;
import com.yrbase.soulpermission.SoulPermission;
import com.yrbase.soulpermission.bean.Permission;
import com.yrbase.soulpermission.bean.Permissions;
import com.yrbase.soulpermission.callbcak.CheckRequestPermissionListener;
import com.yrbase.soulpermission.callbcak.CheckRequestPermissionsListener;
import com.yrbase.utils.LogUtil;
import com.yrbase.utils.OnPerfectClickListener;
import com.yrbase.utils.ShapeBuilder;
import com.yrbase.utils.SpanUtils;
import com.yrbase.utils.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends BaseActivity<TestPresenter.Presenter> implements TestPresenter.View {


    private String strt = "1.接到报警后，立即携带单警装备及必要的警械、武器，迅速出警。\r\n2.及时开启执法记录仪，到达现场后，了解基本情况（发案时间、地点、作案方式、财物损失情况）并向指挥中心报告。\n3.犯罪嫌疑人还在现场的，立即组织抓捕。检查人身、随身物品，扣押涉案财物。\n4.根据现场情况合理划定保护区域，防止无关人员进入，通知技术人员勘查现场。\n5.开展现场调查，详细询问报案人、被害人、证人，查明案发时间、被盗物品价值、现场结构、现场有无被破坏等。调取查看监控录像，发现犯罪嫌疑人线索，提取相关证据。\n6.及时向指挥中心反馈处警情况。\n7.填写、存储接处警记录；处警结果需要制作法律文书的，按有关规定办理。\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getToolBarX().setCenterText(R.string.app_name).setRightText("图片选择").setRightTextOnClickListener(new OnPerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ViewUtil.startActivity(ImagePickerActivity.class);
                //ViewUtil.Toast("图片选择");

                //主线程中调用：
                //mHandler.postDelayed(runnable, 1000);//延时100毫秒
                callMap();

            }
        });

        //ViewUtil.Toast(R.string.app_name);
        mPresenter.getStoreAuto();

        //testPermission();


        TextView viewById = findViewById(R.id.tv);

        //ViewUtil.setText(viewById, strt);

       /* ShapeBuilder.create().radiusDp(2).solid(R.color.colorAccent)
                .stroke(ViewUtil.dp2px(2),R.color.colorPrimary)
                .setBackBg(viewById);*/

        ShapeBuilder.create().radiusDp(2).solid(R.color.colorAccent)
                .stroke(ViewUtil.dp2px(2),R.color.colorPrimary)
                .setBackBg(viewById);


        SpanUtils.Builder builder = SpanUtils.getBuilder("前置文字:")
                .setFgColor(R.color.colorAccent).setTextDpSize(R.dimen.txt_small_much)
                .append("   后置文字", null);

        viewById.setText(builder.create());
    }


    /**
     * 权限被拒绝
     *
     * @param bean DenyBean
     */
    @PermissionDenied
    public void dealPermission(DenyBean bean) {//不在询问的拒绝
        LogUtil.yangRui().e("权限被拒绝");

        Toast.makeText(this,
                "requestCode:权限被拒绝" + bean.getRequestCode() + ",Permissions: " + Arrays.toString(bean.getDenyList().toArray()), Toast.LENGTH_SHORT).show();

    }

    /**
     * 权限被取消
     *
     * @param bean CancelBean
     */
    @PermissionCanceled
    public void dealCancelPermission(CancelBean bean) {//拒绝
        LogUtil.yangRui().e("权限被取消");
        Toast.makeText(this, "requestCode:权限被取消" + bean.getRequestCode(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void initPresenter() {
        mPresenter.setView(this);
    }

    @Override
    public void onSaveSuccess(List<UrlBean> data) {

    }

    MyHandler mHandler = new MyHandler(this);



    /**
     * 声明一个静态的Handler内部类，并持有外部类的弱引用
     */
    private static class MyHandler extends Handler{

        private final WeakReference<TestActivity> mActivity;

        private MyHandler(TestActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TestActivity activity = mActivity.get();
            if (activity != null){
                // ....
                activity.callMap();

            }
        }
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            mHandler.postDelayed(this, 1000);
            EventBus.getDefault().postSticky(new UrlBean("111", "2222"));

        }
    };

    /**
     * 申请权限
     */
    @NeedPermission(value = {Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 10)
    private void callMap() {
        Toast.makeText(this, "定位权限申请通过", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String str) {

    }

    public void testPermission() {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        ViewUtil.Toast(permission.toString() + "\n is ok , you can do your operations");
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        ViewUtil.Toast(permission.toString() + "\n is refused you can not do next things");
                    }
                });

        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        ViewUtil.Toast(allPermissions.length + "permissions is ok" +
                                " \n  you can do your operations");

                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        ViewUtil.Toast(refusedPermissions[0].toString() +
                                " \n is refused you can not do next things");
                    }
                });

    }

}
