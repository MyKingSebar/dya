package cn.v1.unionc_user;

import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.smtt.sdk.QbSdk;

import cn.jpush.android.api.JPushInterface;
import cn.v1.unionc_user.tecent_qcloud.InitSDK;
import cn.v1.unionc_user.tecent_qcloud.UserConfig;
import cn.v1.unionc_user.tecent_qcloud.tim_util.Foreground;

/**
 * Created by qy on 2018/2/1.
 */

public class UnioncApp extends MultiDexApplication {

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
        initX5();
        initLog();
        initAndroidN();
        initJiGuang();
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
}
