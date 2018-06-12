package cn.v1.unionc_user.Rong;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.rong.imlib.RongIMClient;

/**
 * 融云相关监听 事件集合类
 * Created by AMing on 16/1/7.
 * Company RongCloud
 */
public class SealAppContext {

    public static  RongIMClient.ConnectCallback getConnectCallback(final Context mContext) {
        RongIMClient.ConnectCallback connectCallback = new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.d("linshi", "ConnectCallback connect onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.d("linshi", "ConnectCallback connect onSuccess");
                SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit().putString(SealConst.SEALTALK_LOGIN_ID, s).commit();
            }

            @Override
            public void onError(final RongIMClient.ErrorCode e) {
                Log.d("linshi", "ConnectCallback connect onError-ErrorCode=" + e);
            }
        };
        return connectCallback;
    }
}
