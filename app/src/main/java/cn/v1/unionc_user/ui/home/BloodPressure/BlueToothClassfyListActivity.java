package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.adapter.BlueToothClassfyAdapter;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DeviceData;


public class BlueToothClassfyListActivity extends BaseActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private List<DeviceData> devicedatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_classfy_list);
        initData();
        initView();
    }


    private void initData() {
        if(getIntent().hasExtra("device")){
            String device = getIntent().getStringExtra("device");
            if(TextUtils.equals("1",device)){
                //血压计
                setTitle("选择血压计");
                devicedatas.add(new DeviceData("1",R.drawable.icon_omron_device_picture,"欧姆龙HEM-9200T"));
            }
            if(TextUtils.equals("2",device)){
                //心电仪
                setTitle("选择心电仪");
                devicedatas.add(new DeviceData("2",R.drawable.img_xj_heart_device,"掌上心电-EB10"));
            }
        }
    }

    private void initView() {


        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(new BlueToothClassfyAdapter(this,devicedatas));
    }
}
