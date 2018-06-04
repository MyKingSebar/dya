package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.base.BaseActivity;

/**
 * Created by An4 on 2018/5/12.
 */
public class OmrLoseCoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.img_back)
    ImageView img_back;
    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @OnClick(R.id.omr_lose_btn)
    void back2(){
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omrlose_layout2);
        ButterKnife.bind(this);
        tv_title.setText("欧姆龙HEM-9200T");
    }
}
