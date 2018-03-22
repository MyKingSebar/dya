package cn.v1.unionc_user.ui.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

public class ServerCenterActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_online)
    TextView tvOnline;
    @Bind(R.id.tv_call)
    TextView tvCall;


    private String servicePhone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_servercenter);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_online, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_online:
                break;
            case R.id.tv_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + servicePhone);
                intent.setData(data);
                startActivity(intent);
                break;

        }
    }


    private void initView() {
        tvTitle.setText("客服中心");
        servicePhone="400-625-8851";
    }

}
