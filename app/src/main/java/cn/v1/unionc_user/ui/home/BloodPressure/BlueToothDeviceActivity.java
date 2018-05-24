package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.adapter.BlueToothDeviceAdapter;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DeviceData;

public class BlueToothDeviceActivity extends BaseActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private List<DeviceData> devicedatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_device);
        initData();
        initView();
    }

    private void initData() {
        if(getIntent().hasExtra("bloodpresureOnly")){
            devicedatas.add(new DeviceData("1",R.drawable.icon_omron_xueyaji,"血压计"));
        }else{
            devicedatas.add(new DeviceData("1",R.drawable.icon_omron_xueyaji,"血压计"));
            devicedatas.add(new DeviceData("2",R.drawable.icon_xj_xindianyi,"心电仪"));
        }
    }


    private void initView() {

        setTitle("智能设备");

        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(new BlueToothDeviceAdapter(this,devicedatas));
    }
}
