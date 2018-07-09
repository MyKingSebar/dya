package cn.v1.unionc_user.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserStatusListener;

import cn.v1.unionc_user.Rong.listener.MyConnectionStatusListener;
import cn.v1.unionc_user.Rong.listener.MyReceiveMessageListener;
import cn.v1.unionc_user.Rong.listener.MySendMessageListener;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.UserConfig;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.me.RealNameAuthActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by qy on 2018/2/1.
 */

public class BaseActivity extends FragmentActivity {
    protected Context context;
    private ProgressDialog progressDialog;

    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
    }

    /**
     * TIMUserConfig初始化
     * 监听TIM用户状态
     */
    protected void initTIMUserConfig() {
        new UserConfig().setOnUserStatusChangeListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {

            }

            @Override
            public void onUserSigExpired() {

            }
        });
    }

    /**
     * tusi
     *
     * @param message
     */
    protected void showTost(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    /**
     * 登录
     *
     * @param token
     */
    protected void login(String token) {
        SPUtil.put(context, Common.USER_TOKEN, (String) token);
    }
    /**
     * Rong登录
     *
     * @param token
     */
    protected void ronglogin(String token) {
        SPUtil.put(context, Common.USER_RONGTOKEN, (String) token);
    }

    /**
     * 是否登录
     */
    protected boolean isLogin() {
        return SPUtil.contains(context, Common.USER_TOKEN);
    }
    /**
     * getToken
     */
    protected String getToken() {
        return  (String) SPUtil.get(context, Common.USER_TOKEN, "");
    }

    /**
     * 退出
     */
    protected void logout() {
        SPUtil.remove(context, Common.USER_TOKEN);
        SPUtil.remove(context, Common.USER_ADD);
        SPUtil.remove(context, Common.USER_RONGTOKEN);
        RongIM.getInstance().logout();
        //登出
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {

                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Logger.e("logout failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                //登出成功
                Logger.d("logout success");
            }
        });
    }

    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Common.DATA, bundle);
        startActivity(intent);
    }

    /**
     * 展示加载框
     *
     * @param message 需要提示的信息
     */
    protected void showDialog(String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void closeDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected Bundle outState;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        this.outState = outState;
        super.onSaveInstanceState(outState);
    }

    /**
     * 实名认证提示框
     */
    protected void gotoAuthDialog() {
        PromptDialog signDoctor = new PromptDialog(context);
        signDoctor.show();
        signDoctor.setTitle("实名认证");
        signDoctor.setMessage("你还没有实名认证，先去实名认证?");
        signDoctor.setTvCancel("确定");
        signDoctor.setTvConfirm("取消");
        signDoctor.setOnButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onCancelClick() {
                goNewActivity(RealNameAuthActivity.class);
            }
        });
    }

    /**
     * 修改用户地址：
     *
     */
    protected void updateAdd( String token, String add,String la,String lo) {
        showDialog("请稍侯...");
        ConnectHttp.connect(UnionAPIPackage.updateAdd(token, add,la,lo), new BaseObserver<BaseData>(context) {

            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    Log.d("linshi","修改用户地址成功");
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                showTost("修改用户地址失败");
            }
        });
    }
    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    public void connect(String token) {
final String Rongtoken=token;
        if (getApplicationInfo().packageName.equals(UnioncApp.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.e("linshi", "onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    ronglogin(Rongtoken);
                    Log.d("linshi", "--onSuccess" + userid);
                    if(BaseActivity.this instanceof LoginActivity){
                        finish();
                    }
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("linshi", "onError"+errorCode);

                }
            });
            RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
            RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
            RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        }
    }
}
