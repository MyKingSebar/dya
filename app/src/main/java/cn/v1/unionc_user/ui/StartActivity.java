package cn.v1.unionc_user.ui;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.tecent_qcloud.InitSDK;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class StartActivity extends BaseActivity {

    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private SplashPresenter presenter;
    private Handler handler = new Handler();

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
        clearNotification();
        setContentView(R.layout.activity_start);
        unbinder= ButterKnife.bind(this);
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /**
             *
             */
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED))
//                permissionsList.add(Manifest.permission.READ_CONTACTS);
//            if ((checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED))
//                permissionsList.add(Manifest.permission.WRITE_CONTACTS);
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CAMERA);
            if (permissionsList.size() == 0) {
                init();
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        } else {
            init();
        }
    }


    private void init() {
        /**
         * 腾讯云初始化
         */
        InitSDK.init(context);
        if (!isLogin()) {
            gotoMain();
        } else {
            initTIMUserConfig();
            String identifier = (String) SPUtil.get(context, Common.IDENTIFIER, "");
            String timSig = (String) SPUtil.get(context, Common.TIM_SIG, "");
            TIMManager.getInstance().login(identifier, timSig, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    //错误码code和错误描述desc，可用于定位请求失败原因
                    //错误码code列表请参见错误码表
                    Logger.e("login failed. code: " + code + " errmsg: " + desc);
                    //showTost(desc + "");
                    Intent intent = new Intent(context,LoginActivity.class);
                    Log.d("linshi","LoginActivity:Start");
                    intent.putExtra("from","start");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onSuccess() {
                    Logger.d("login succ");
                    gotoMain();
                }
            });
        }

    }

    private void gotoMain() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //      根据版本号判断是不是第一次使用
                PackageInfo info=null;
                try {
                    info=getPackageManager().getPackageInfo(getPackageName(),0);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                int currentVersion = info.versionCode;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

                int lastVersion = sp.getInt("VERSION_KEY", 0);


                if (currentVersion>lastVersion){
//            第一次启动将当前版本进行存储
                    sp.edit().putInt("VERSION_KEY",currentVersion).commit();
                    goNewActivity(cn.v1.unionc_user.ui.welcome.StartActivity.class);
                }else {
//            非第一次启动直接跳转
                    goNewActivity(MainActivity.class);
                }
//                goNewActivity(cn.v1.unionc_user.ui.welcome.StartActivity.class);
                finish();
            }
        },
//                2000
        0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    showTost("您需要开启权限,并重启应用");
                    finish();
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
