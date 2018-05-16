package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.MobileConfigUtil;

public class AboutYIBASHIActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yibashi);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    private void initView() {
        tvTitle.setText("关于医巴士");
        tvVersion.setText("医巴士"+ MobileConfigUtil.getVersionName());
//        tvVersion.setText("医巴士"+ MobileConfigUtil.getVersionName());
        Log.d("linshi","医巴士:"+MobileConfigUtil.getVersionName());
    }

}
