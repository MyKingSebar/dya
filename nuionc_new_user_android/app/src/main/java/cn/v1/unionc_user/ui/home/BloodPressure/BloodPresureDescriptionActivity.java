package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class BloodPresureDescriptionActivity extends BaseActivity {

    @BindView(R.id.tv_done)
    TextView tvDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure_description);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("欧姆龙HEM-9200T");
    }

    @OnClick(R.id.tv_done)
    public void onClick() {
        finish();
    }
}
