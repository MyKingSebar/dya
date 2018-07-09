package cn.v1.unionc_user.ui.me.oldregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class OldRegisterActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView bakc;
    @BindView(R.id.tv_title)
    TextView title;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @OnClick(R.id.bt_next)
    void next(){
        goNewActivity(OldRegisterActivity1.class);
    }


    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_old_register);
        unbinder= ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
