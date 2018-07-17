package cn.v1.unionc_user.ui.me.oldregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class TestInfo extends BaseActivity {
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testinfo);
        unbinder= ButterKnife.bind(this);

    }
}
