package cn.v1.unionc_user.ui.home.BloodPressure;

import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.OMLHistoryData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.OMLLostData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.OMLSuccessData;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;
import cn.v1.unionc_user.ui.home.health.AlarmDialog;
import cn.v1.unionc_user.ui.home.health.IRespCallBack;

public class OMRONBannerActivity2 extends BaseActivity {
    //链接设备
    @BindView(R.id.tv_link_device)
    public TextView tvLinkDevice;
    //蓝牙状态
    @BindView(R.id.dossierheartrate_connect_tv)
    public TextView connectTv;
    @BindView(R.id.dossierheartrate_disconnect_tv)
    public TextView disConnectTv;

    @BindView(R.id.im_status)
    public ImageView imStatus;


    @BindView(R.id.toplayout)
    RelativeLayout toplayout;

    @BindView(R.id.history)
    RelativeLayout history;
    @BindView(R.id.tv_dossier_hert_rate_history)
    TextView tvDossierHertRateHistory;

    @BindView(R.id.last_time)
    TextView time;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @BindView(R.id.rl_help)
    public RelativeLayout rlHelp;

    @OnClick(R.id.rl_help)
    void help() {
        Intent intent = new Intent(context, ToDoorWebViewActivity.class);
        intent.putExtra("type", Common.WEB_OMLHELP);
        startActivity(intent);
    }

    @OnClick(R.id.history)
    public void history() {

        goNewActivity(BloodPresureHistoryActivity.class);
    }


    private List<View> viewList = new ArrayList<>();

    private String deviceName;
    OMLSuccessData omldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oml_bload_auto);
        ButterKnife.bind(this);
        BusProvider.getInstance().register(this);
        initData();
        initView();
        getData();
    }

    private void getData() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getOMLData(token, "2", "1","1" +
                ""), new BaseObserver<OMLHistoryData>(context) {

            @Override
            public void onResponse(OMLHistoryData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("获取成功");
                }
                else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                showTost("删除失败");
            }
        });
    }

    //连接设备
    @OnClick(R.id.tv_link_device)
    public void onClick() {
        if (tvLinkDevice.getText().toString().equals("连接设备")) {
            if (Build.VERSION.SDK_INT < 18) {
                showTost("手机系统版本过低，不支持心电设备");
                return;
            }
            if (isBTConnected()) {
                Intent intent = new Intent(context, TzOmRonBlueToothActivity.class);
                context.startActivity(intent);
            } else {
                showUploadDialog();
            }
        } else if (tvLinkDevice.getText().toString().equals("开始测量")) {
            Intent intent = new Intent(context, BlueToothMeasureActivity2.class);
            intent.putExtra("deviceName", omldata.getDeviceName());
            intent.putExtra("deviceBDA", omldata.getDeviceBDA()

            );
            intent.putExtra("goHome", true);
            setResult(RESULT_OK);
            startActivity(intent);

        }
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return
     */
    public boolean isBTConnected() {
        boolean BLEState = false;
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        //adapter也有getState(), 可获取ON/OFF...其它状态
        int state = blueadapter.getState();
        if (state == BluetoothAdapter.STATE_ON) {
            BLEState = true;
        }
        if (state == BluetoothAdapter.STATE_OFF) {
            BLEState = false;
        }
        return BLEState;
    }

    /**
     * 打开蓝牙的弹框
     */
    private void showUploadDialog() {

        final AlarmDialog dialog = new AlarmDialog(context, AlarmDialog.CANCELANDOK, new IRespCallBack() {
            @Override
            public boolean callback(int callCode, Object... param) {
                if (callCode == AlarmDialog.ALARMDIALOGOK) {

                }
                if (callCode == AlarmDialog.ALARMDIALOGCANCEL) {
                    openSetting();
                }
                return true;
            }
        }, "", "打开蓝牙来允许“血压仪”连接到配件");
        dialog.tvSmallMessage.setVisibility(View.GONE);
        dialog.setStr_okbtn("好");
        dialog.setStr_cancelbtn("设置");
        dialog.show();
    }

    /**
     * 设置蓝牙
     *
     * @return
     */
    private void openSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        if (getIntent().hasExtra("deviceName")) {
            deviceName = getIntent().getStringExtra("deviceName");
        }
        deviceName = "血压仪";
    }

    private void initView() {
        tvTitle.setText(deviceName);
        toplayout.setBackgroundColor(getResources().getColor(R.color.green_oml));
        imStatus.setBackground(getResources().getDrawable(R.drawable.im_status));
    }

    @Subscribe
    public void omllost(OMLLostData data) {
        Log.d("linshi", "OMLLostData");
        tvLinkDevice.setText("连接设备");
        connectTv.setText("请绑定设备");
        imStatus.setBackground(getResources().getDrawable(R.drawable.im_status));
    }

    @Subscribe
    public void omlsuccess(OMLSuccessData data) {
        Log.d("linshi", "OMLSuccessData");
        tvLinkDevice.setText("开始测量");
        connectTv.setText("绑定成功");
        imStatus.setBackground(getResources().getDrawable(R.drawable.im_status_ok));
        omldata = data;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this); //取消订阅
    }
}
