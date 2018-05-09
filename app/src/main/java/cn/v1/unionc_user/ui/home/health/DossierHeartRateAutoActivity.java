package cn.v1.unionc_user.ui.home.health;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.ToDoorWebViewActivity;
import cn.v1.unionc_user.ui.home.health.DossierHeartRateAutoFragment;
import cn.v1.unionc_user.ui.home.health.DossierHeartrateAutoPreviewFragment;

public class DossierHeartRateAutoActivity extends BaseActivity {




    @BindView(R.id.tv_dossier_hert_rate_history)
    TextView tvDossierHertRateHistory;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @BindView(R.id.last_time)
    TextView time;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;
    @OnClick(R.id.img_back)
    public void back(){
        finish();
    }

    @BindView(R.id.rl_help)
    public RelativeLayout rlHelp;

    @OnClick(R.id.rl_help)
    public void rhelp(){
//        new HeartRateContinueMeasureDialog(this, new HeartRateContinueMeasureDialog.MyDilogListener() {
//            @Override
//            public void btnConfirm(Dialog dialog) {
//                dialog.dismiss();
//            }
//        });
        Intent intent = new Intent(context, ToDoorWebViewActivity.class);
        intent.putExtra("type", Common.WEB_HELP);
        startActivity(intent);
    }

//    //历史记录
//    @OnClick(R.id.tv_dossier_hert_rate_history)
//    public void onClick() {
//        Intent intent = new Intent(DossierHeartRateAutoActivity.this, DossierHertRateHistoryActivity.class);
//        intent.putExtra("userId", userId);
//        intent.putExtra("monitorId", monitorId);
//        intent.putExtra("healthInfoId", healthInfoId);
//        startActivity(intent);
//    }

    //数据变量
    private String userId = "";
    private String monitorId = "";
    private String healthInfoId = "";
    public boolean isConnet = false;
    public boolean isRadioBtnReset = false;
    public EcgOpenApiHelper mOsdkHelper;
    public int ecgSample;
    public int autoMeasureType;
    public final int ONE_MINUTE_MEASURE = 1;
    public final int CONTINUITY_MINUTE_MEASURE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_hertrate_auto);
        ButterKnife.bind(this);
        registerReceiver(blueToothSataeBroadCastRecever, myINtentFilter());
        initData();
        initSdk();
        initView();
    }

    private static IntentFilter myINtentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction("com.example.localbroadcastdemo.LOCALBROADCAST");
        return intentFilter;
    }

    public String deviceName = "";
    //监听蓝牙开启和关闭的广播
    private BroadcastReceiver blueToothSataeBroadCastRecever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("blueTooth_action", intent.getAction() + "");
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        //showToast("蓝牙关闭");
                        deviceName = "";
                        //Log.d("aaa", "STATE_OFF 手机蓝牙关闭");
                        // tvBluetoothState.setText("蓝牙状态:关闭");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        // Log.d("aaa", "STATE_TURNING_OFF 手机蓝牙正在关闭");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //showToast("蓝牙打开");
                        // Log.d("aaa", "STATE_ON 手机蓝牙开启");
                        // tvBluetoothState.setText("蓝牙状态:打开");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        //Log.d("aaa", "STATE_TURNING_ON 手机蓝牙正在开启");
                        break;
                }
            }
            if (intent.getAction().equals("com.example.localbroadcastdemo.LOCALBROADCAST")) {
                ecgSample = intent.getIntExtra("ecgSample", 0);
                deviceName = intent.getStringExtra("ecgDeviceName");
                //showToast(ecgSample+"");
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mOsdkHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        unregisterReceiver(blueToothSataeBroadCastRecever);
    }

    @Override
    protected void onResume() {
        super.onResume();
            resumeViewSet();
        String HEARTRATE = (String) SPUtil.get(context, Common.HEARTRATE, "");
        num.setText(HEARTRATE);
    }

    /**
     * 返回后view的设置
     */
    private void resumeViewSet() {
        if (mOsdkHelper.isDeviceConnected()) {
            isConnet = true;
        } else {
            isConnet = false;
        }

            replaceFragment(0);
    }

    public void initView() {
        tvTitle.setText("心率检测");
        replaceFragment(0);
        String HEARTRATE = (String) SPUtil.get(context, Common.HEARTRATE, "");
        num.setText(HEARTRATE);
    }

    private void initData() {
        userId = getIntent().getStringExtra("userId");
        monitorId = getIntent().getStringExtra("monitorId");
        healthInfoId = getIntent().getStringExtra("healthInfoId");
    }

    /**
     * 初始化SDk
     */
    public void initSdk() {
        mOsdkHelper = EcgOpenApiHelper.getInstance();
        mOsdkHelper.setDeviceType(EcgOpenApiHelper.DEVICE.CONNECT_TYPE_BLUETOOTH_DUAL);
        UnioncApp.getInstance().setOsdkCallback(mOsdkCallback);

    }

    public EcgOpenApiCallback.OsdkCallback mOsdkCallback = new EcgOpenApiCallback.OsdkCallback() {

        @Override
        public void devicePlugIn() {
        }

        @Override
        public void devicePlugOut() {
        }

        @Override
        public void deviceSocketConnect() {
            isConnet = true;
        }

        @Override
        public void deviceSocketLost() {
            isConnet = false;
        }

        @Override
        public void deviceReady(int sample) {
//            ToastText("心电设备已准备好！");
//            mDeviceStatusTV.setText("准备就绪,设备号:" + mOsdkHelper.getDeviceSN());
//            ecgSample = sample;
//            mEcgBrowser.setSample(sample);
        }

        @Override
        public void deviceNotReady(int i) {
//            switch (msg) {
//                case EcgOpenApiCallback.DEVICE_NOT_READY_NOT_SUPPORT_DEVICE:// sdk不支持设备
//                    ToastText("当前sdk设备无法使用此型号设备");// sdk不支持型号
//                    mDeviceStatusTV.setText("型号不支持");
//                    break;
//                case EcgOpenApiCallback.DEVICE_NOT_READY_UNKNOWN_DEVICE:// 未知设备
//                    ToastText("设备无法使用");// 设备故障或者非熙健产品
//                    mDeviceStatusTV.setText("无法使用");
//                    break;
//                default:
//                    break;
//            }
        }
    };

    private DossierHeartrateAutoPreviewFragment dossierHeartrateAutoPreviewFragment;
    private DossierHeartRateAutoFragment dossierHeartRateAutoFragment;
//    private DossierHeartRateHandFragment dossierHeartRateHandFragment;
//    private DossierHeartRateSelectAutoTypeFragment dossierHeartRateSelectAutoTypeFragment;

    public void replaceFragment(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0:
                    if (null == dossierHeartrateAutoPreviewFragment) {
                        dossierHeartrateAutoPreviewFragment = DossierHeartrateAutoPreviewFragment.newInstance(userId,healthInfoId,monitorId);
                        transaction.add(R.id.fragment_container, dossierHeartrateAutoPreviewFragment);
                    } else {
                        transaction.show(dossierHeartrateAutoPreviewFragment);
                        if (isConnet) {
                            //TODO  选择自动测量模式
                            dossierHeartrateAutoPreviewFragment.connectTv.setText("已连接设备: "+ deviceName);
                            dossierHeartrateAutoPreviewFragment.disConnectTv.setVisibility(View.VISIBLE);
                            dossierHeartrateAutoPreviewFragment.tvLinkDevice.setText("开始测量");
                            dossierHeartrateAutoPreviewFragment.imStatus.setBackground(getResources().getDrawable(R.drawable.im_status_ok));
                        }else{
                            dossierHeartrateAutoPreviewFragment.connectTv.setText("请打开手机蓝牙和设备电源");
                            dossierHeartrateAutoPreviewFragment.disConnectTv.setVisibility(View.GONE);
                            dossierHeartrateAutoPreviewFragment.tvLinkDevice.setText("连接设备");
                            dossierHeartrateAutoPreviewFragment.imStatus.setBackground(getResources().getDrawable(R.drawable.im_status_no));
                        }
                    }
                break;
            case 1:
//                if (null == dossierHeartRateHandFragment) {
//                    dossierHeartRateHandFragment = DossierHeartRateHandFragment.newInstance(userId, healthInfoId, monitorId);
//                    transaction.add(R.id.fragment_container, dossierHeartRateHandFragment);
//                } else {
//                    transaction.show(dossierHeartRateHandFragment);
//                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (dossierHeartrateAutoPreviewFragment != null) {
            transaction.hide(dossierHeartrateAutoPreviewFragment);
        }
        if (dossierHeartRateAutoFragment != null) {
            transaction.hide(dossierHeartRateAutoFragment);
        }
//        if (dossierHeartRateHandFragment != null) {
//            transaction.hide(dossierHeartRateHandFragment);
//        }
//        if (dossierHeartRateSelectAutoTypeFragment != null) {
//            transaction.hide(dossierHeartRateSelectAutoTypeFragment);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
}
