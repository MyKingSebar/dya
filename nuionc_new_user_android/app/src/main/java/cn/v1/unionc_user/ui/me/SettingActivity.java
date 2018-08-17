package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.LogOutEventData;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.LoginAgreementActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_service_agreement)
    TextView tvServiceAgreement;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_service_agreement, R.id.tv_about_us, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_service_agreement:
                goNewActivity(LoginAgreementActivity.class);
                break;
            case R.id.tv_about_us:
                goNewActivity(AboutYIBASHIActivity.class);
                break;
            case R.id.tv_logout:
                PromptDialog logoutDialog = new PromptDialog(context);
                logoutDialog.show();
                logoutDialog.setTitle("退出登录");
                logoutDialog.setMessage("确认退出登录？");
                logoutDialog.setTvConfirm("确定");
                logoutDialog.setTvCancel("取消");
                logoutDialog.setOnButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onConfirmClick() {
                        logout();
                        goNewActivity(LoginActivity.class);
                        finish();
                        Log.d("linshi","LoginActivity:Setting.logout");
                        LogOutEventData eventData = new LogOutEventData();
                        BusProvider.getInstance().post(eventData);
                    }

                    @Override
                    public void onCancelClick() {
                    }
                });
                break;
        }
    }


    private void initView() {
        tvTitle.setText("设置");
    }

}
