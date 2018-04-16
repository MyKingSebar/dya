package cn.v1.unionc_user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import cn.v1.unionc_user.tecent_qcloud.InitSDK;
import cn.v1.unionc_user.tecent_qcloud.UserConfig;
import cn.v1.unionc_user.tecent_qcloud.tim_util.Foreground;
import cn.v1.unionc_user.ui.home.health.LogUtils;

/**
 * Created by qy on 2018/2/1.
 */

public class UnioncApp extends MultiDexApplication {

    private SDKReceiver mReceiver;
    public DisplayMetrics displayMetrics;

    private static UnioncApp app;

    public static UnioncApp getInstance() {
        if (app == null)
            throw new NullPointerException("app not create or be terminated!");
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
        app = this;
        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher2);
                    }
                }
            });
        }
        displayMetrics = getResources().getDisplayMetrics();
        initX5();
        initLog();
        initAndroidN();
        initJiGuang();

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        //蓝牙心电记录仪SDK的初始化
        initmHelath365SDK();
    }

    private void initJiGuang() {
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    private void initX5(){
    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("app", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
        }
    };
    //x5内核初始化接口
    QbSdk.initX5Environment(getApplicationContext(),  cb);
}
    private void initAndroidN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    /**
     * 初始化Logger
     */
    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }


    //蓝牙心电记录仪SDK的初始化
    //************************************************************************************心电图***********************************************************************
    EcgOpenApiCallback.OsdkCallback displayMessage;
    public void setOsdkCallback(EcgOpenApiCallback.OsdkCallback osdkCallback) {
        displayMessage = osdkCallback;
    }
    EcgOpenApiCallback.OsdkCallback mOsdkCallback = new EcgOpenApiCallback.OsdkCallback() {

        @Override
        public void deviceSocketLost() {
            if (displayMessage != null)
                displayMessage.deviceSocketLost();
        }
        @Override
        public void deviceSocketConnect() {
            if (displayMessage != null)
                displayMessage.deviceSocketConnect();
        }
        @Override
        public void devicePlugOut() {
            if (displayMessage != null)
                displayMessage.devicePlugOut();
        }
        @Override
        public void devicePlugIn() {
            if (displayMessage != null)
                displayMessage.devicePlugIn();
        }
        @Override
        public void deviceReady(int sample) {
            if (displayMessage != null)
                displayMessage.deviceReady(sample);
        }
        @Override
        public void deviceNotReady(int msg) {
            if (displayMessage != null)
                displayMessage.deviceNotReady(msg);
        }
    };
    private void initmHelath365SDK() {
        EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
        String thirdPartyId = "9002e85acfc29e3687c849a88e46c40b";
        String appId = "5d8cef316b20774d99ef76484b620005";
        String appSecret = "";
        String UserOrgName = "医巴士";
        String pakageName = "cn.v1.unionc_user";
        LogUtils.LOGD("心电thirdPartyId",thirdPartyId);
        LogUtils.LOGD("心电appId",appId);
        LogUtils.LOGD("心电UserOrgName",UserOrgName);
        LogUtils.LOGD("心电pakageName",pakageName);
        try {
            mHelper.initOsdk(getApplicationContext(), thirdPartyId, appId, appSecret, UserOrgName, mOsdkCallback, pakageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void finishSdk() throws IOException {
        EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
        mHelper.finishSdk();
    }
    //蓝牙心电记录仪SDK的初始化完毕
    //************************************************************************************心电图完毕***********************************************************************
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            Log.d("SDKReceiver", "action: " + s);
            //Toast.makeText(CaiboApp.getContext(),s,Toast.LENGTH_LONG).show();
        }
    }

    public static Context getContext() {
        return getInstance().getBaseContext();
    }
}
