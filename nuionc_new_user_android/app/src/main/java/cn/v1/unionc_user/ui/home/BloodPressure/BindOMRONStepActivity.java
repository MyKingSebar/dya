package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class BindOMRONStepActivity extends BaseActivity {


    @BindView(R.id.tv_step_title)
    TextView tvStepTitle;
    @BindView(R.id.img_step_picture)
    ImageView imgStepPicture;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private int[] stepPicture = new int[]{R.drawable.img_bind_omron_step_1, R.drawable.img_bind_omron_step_2
            , R.drawable.img_bind_omron_step_3, R.drawable.img_bind_omron_step_4};
    private String[] stepTitle = new String[]{
            "断开电源或取出设备底部电池，点击2~3次开始/停止按钮", "接通电源或在设备底部安装电池",
            "血压计屏幕上出现出现P字母，用app进行搜索点击搜索到的血压计",
            "血压计屏幕上出现出现P字母，用app进行搜索点击搜索到的血压计"};
    private int step = 0;
    private String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_omronstep);
        ButterKnife.bind(this);
        initData();
        initView();
        UnioncApp.getInstance().addActivity(BindOMRONStepActivity.this);
    }

    @OnClick(R.id.tv_next)
    public void onClick() {
        step++;
        if(step < 4){
            tvStepTitle.setText(stepTitle[step]);
            imgStepPicture.setImageResource(stepPicture[step]);
        }else{
            Intent intent = new Intent(this, TzOmRonBlueToothActivity.class);
            startActivityForResult(intent,9999);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 9999 && resultCode==RESULT_OK){
            BindOMRONStepActivity.this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        if(getIntent().hasExtra("deviceName")){
            deviceName = getIntent().getStringExtra("deviceName");
        }
    }


    private void initView() {

        setTitle(deviceName);
        tvStepTitle.setText(stepTitle[0]);
        imgStepPicture.setImageResource(stepPicture[0]);
    }
}
