package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;

import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.base.BaseActivity;

/**
 * Created by An4 on 2018/5/12.
 */
public class OmrLoseCoActivity extends BaseActivity {

    @OnClick(R.id.omr_lose_btn)
    void closeOmrLoseActivity(){
        OmrLoseCoActivity.this.finish();
        UnioncApp.getInstance().closeActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omrlose_layout);
        setTitle("欧姆龙HEM-9200T");
    }
}
