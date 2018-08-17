package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.adapter.BlueToothDeviceAdapter;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DeviceData;
import cn.v1.unionc_user.ui.home.health.DossierHeartRateAutoActivity;

public class BlueToothDeviceActivity2 extends BaseActivity {

    @BindView(R.id.xueya)
    TextView xueya;
    @BindView(R.id.xindian)
    TextView xindian;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.img_back)
    ImageView back;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.xueya)
    void xueya() {
        Intent intent = new Intent(context, OMRONBannerActivity2.class);
        intent.putExtra("device", "血压仪");
        context.startActivity(intent);
    }

    @OnClick(R.id.xindian)
    void xindian() {
        Intent intent = new Intent(context, DossierHeartRateAutoActivity.class);
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        intent.putExtra("userId", token);
        intent.putExtra("monitorId", "1");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_device2);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {

        tv_title.setText("智能设备");

    }
}
