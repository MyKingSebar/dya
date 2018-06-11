package cn.v1.unionc_user.ui.me.guardianship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class BindguardianshipActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship_bindguardianship);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

}
