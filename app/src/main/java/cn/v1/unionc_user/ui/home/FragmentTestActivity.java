package cn.v1.unionc_user.ui.home;


import android.content.Intent;
import android.media.DrmInitData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class FragmentTestActivity extends BaseActivity {
    private String id;
    private String type;
    private FragmentManager mFragmentManager;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenttest);
        ButterKnife.bind(this);
        initData();
        initview();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_activity_main, TestFragment.newInstance(id,type));
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void initData() {
        if(getIntent().hasExtra("id")){
            this.id=getIntent().getStringExtra("id");
        }
        if(getIntent().hasExtra("type")){
            type=getIntent().getStringExtra("type");
        }
    }

    private void initview() {
        tvTitle.setText("填写信息");
    }
}
