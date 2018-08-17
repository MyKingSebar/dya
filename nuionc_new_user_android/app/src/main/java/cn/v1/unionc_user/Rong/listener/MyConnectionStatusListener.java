package cn.v1.unionc_user.Rong.listener;

import android.util.Log;

import io.rong.imlib.RongIMClient;

public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus){

            case CONNECTED://连接成功。
                Log.d("linshi","CONNECTED");
                break;
            case DISCONNECTED://断开连接。
                Log.d("linshi","DISCONNECTED");
                break;
            case CONNECTING://连接中。
                Log.d("linshi","CONNECTING");
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                Log.d("linshi","NETWORK_UNAVAILABLE");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                Log.d("linshi","KICKED_OFFLINE_BY_OTHER_CLIENT");
                break;
        }
    }
}
