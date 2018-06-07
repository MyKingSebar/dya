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

import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.UserConfig;
import cn.v1.unionc_user.ui.me.RealNameAuthActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

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
     * 是否登录
     */
    protected boolean isLogin() {
        return SPUtil.contains(context, Common.USER_TOKEN);
    }

    /**
     * 退出
     */
    protected void logout() {
        SPUtil.remove(context, Common.USER_TOKEN);
        SPUtil.remove(context, Common.USER_ADD);
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



}
