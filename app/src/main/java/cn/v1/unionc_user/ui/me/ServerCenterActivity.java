package cn.v1.unionc_user.ui.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ServerCenterActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_online)
    TextView tvOnline;
    @BindView(R.id.tv_call)
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
        servicePhone="4000123789";
    }

}
