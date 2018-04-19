package cn.v1.unionc_user.ui.home.health;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.base.BaseActivity;

/**
 *
 * 选择蓝牙设备
 */
public class DossierHeartRateSelectBlueToothActivity extends BaseActivity implements EcgOpenApiCallback.BluetoothCallback {

    //关闭页面
    @BindView(R.id.img_close)
    ImageView imgClose;
    //刷新
    @BindView(R.id.img_refresh)
    ImageView imgRefresh;
    //蓝牙名字
    @BindView(R.id.tv_bluetooth_device)
    TextView tvBluetoothDevice;
    //蓝牙连接状态
    @BindView(R.id.tv_device_state)
    TextView tvDeviceState;
    //蓝牙列表
    @BindView(R.id.rv_bluetooth_list)
    RecyclerView rvBluetoothList;


    @OnClick({R.id.img_close, R.id.img_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                Intent intent = new Intent("com.example.localbroadcastdemo.LOCALBROADCAST");
                intent.putExtra("ecgSample", ecgSample);
                if (device != null)
                    intent.putExtra("ecgDeviceName", device.getName());
                sendBroadcast(intent);
                finish();
                break;
            case R.id.img_refresh:
                tvBluetoothDevice.setVisibility(View.GONE);
                tvDeviceState.setText("蓝牙搜索中.....");
                deviceList.clear();
                scanLeDevice(true);
                break;
        }
    }


    private EcgOpenApiHelper mOsdkHelper;
    private BlueToothListAdapter blueToothListAdapter;
    private Handler mHandler;
    private List<BluetoothDevice> deviceList = new ArrayList<>();
    private HashMap<String, Integer> devRssiValues = new HashMap<>();
    private BluetoothDevice device;
    private int ecgSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_hert_rateselect_blue_tooth);
        ButterKnife.bind(this);
        mHandler = new Handler();
        initBlueTooth();
        initRecycleView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent("com.example.localbroadcastdemo.LOCALBROADCAST");
        intent.putExtra("ecgSample",ecgSample);
        if (device != null)
            intent.putExtra("ecgDeviceName", device.getName());
        sendBroadcast(intent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }


    @Override
    public void onStop() {
        super.onStop();
        mOsdkHelper.stopDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOsdkHelper.stopDiscovery();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        rvBluetoothList.setLayoutManager(new LinearLayoutManager(this));
        blueToothListAdapter = new BlueToothListAdapter();
        rvBluetoothList.setAdapter(blueToothListAdapter);
    }

    /**
     * 初始化BlueTooth
     */
    private void initBlueTooth() {
        mOsdkHelper = EcgOpenApiHelper.getInstance();
        UnioncApp.getInstance().setOsdkCallback(mOsdkCallback);
        mOsdkHelper.createBluetoothDiscovery(this);
        if (!mOsdkHelper.isSupportBluetooth()) {
            showTost("蓝牙低电量");
            finish();
            return;
        }
        mOsdkHelper.enableBluetooth();
        tvBluetoothDevice.setVisibility(View.GONE);
        tvDeviceState.setText("蓝牙搜索中.....");
        deviceList.clear();
        scanLeDevice(true);
    }

    /**
     * 扫描蓝牙设备
     *
     * @param enable
     */
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mOsdkHelper.stopDiscovery();
                }
            }, 10000);
            mOsdkHelper.startDiscovery();
        } else {
            mOsdkHelper.stopDiscovery();
        }
    }


    /**
     * 添加设备
     *
     * @param device
     * @param rssi
     */
    private void addDevice(BluetoothDevice device, int rssi) {
        boolean deviceFound = false;

        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
//				byte[] pin = new byte[] { '0', '1', '2', '3' };
//				device.setPin(pin);
                deviceFound = true;
                break;
            }
        }

        devRssiValues.put(device.getAddress(), rssi);
        if (!deviceFound) {
            deviceList.add(device);
            blueToothListAdapter.notifyDataSetChanged();
        }
    }


    //******************* BluetoothCallback*******************
    @Override
    public void start() {
        blueToothListAdapter.notifyDataSetChanged();
    }

    @Override
    public void foundNewDevice(final BluetoothDevice bluetoothDevice, final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addDevice(bluetoothDevice, i);
                LogUtils.LOGD("SCANE_BLUETOOTH", "foundNewDevice");
            }
        });
    }

    @Override
    public void finished() {
        tvDeviceState.setText("请选择mHealth365设备");
        blueToothListAdapter.notifyDataSetChanged();
    }

    /******************
     * BluetoothCallback
     *******************/
    EcgOpenApiCallback.OsdkCallback mOsdkCallback = new EcgOpenApiCallback.OsdkCallback() {

        @Override
        public void devicePlugIn() {
        }

        @Override
        public void devicePlugOut() {
        }

        @Override
        public void deviceSocketConnect() {
            tvBluetoothDevice.setVisibility(View.GONE);
            tvDeviceState.setText("已连接");
//            ToastText("设备已连接！");
//            mDeviceStatusTV.setText("已连接");
        }

        @Override
        public void deviceSocketLost() {
            tvBluetoothDevice.setVisibility(View.GONE);
            tvDeviceState.setText("已断开");
//            ToastText("设备连接断开！");
//            mDeviceStatusTV.setText("已断开");
        }

        @Override
        public void deviceReady(int sample) {
            showTost("心电设备已准备好！");
            tvBluetoothDevice.setVisibility(View.VISIBLE);
            tvDeviceState.setText("连接成功");
            if (null != device) {
                if (!TextUtils.isEmpty(device.getName())) {
                    tvBluetoothDevice.setText(device.getName() + "");
                } else {
                    tvBluetoothDevice.setText(device.getAddress() + "");
                }
            }
//            mDeviceStatusTV.setText("准备就绪,设备号:" + mOsdkHelper.getDeviceSN());
            ecgSample = sample;
//            LogUtils.LOGD("DossierHeartRateSelectBlueToothActivity",sample+"");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent("com.example.localbroadcastdemo.LOCALBROADCAST");
                    intent.putExtra("ecgSample",ecgSample);
                    if (device != null)
                        intent.putExtra("ecgDeviceName", device.getName());
                    sendBroadcast(intent);
                    finish();
                }
            },500);
//            mEcgBrowser.setSample(sample);
        }

        @Override
        public void deviceNotReady(int msg) {
            tvBluetoothDevice.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.icon_no_connect);
            tvDeviceState.setCompoundDrawablePadding(10);
            tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            tvDeviceState.setText("连接失败");
            switch (msg) {
                case EcgOpenApiCallback.DEVICE_NOT_READY_NOT_SUPPORT_DEVICE:// sdk不支持设备
                    showTost("当前sdk设备无法使用此型号设备");// sdk不支持型号
                    break;
                case EcgOpenApiCallback.DEVICE_NOT_READY_UNKNOWN_DEVICE:// 未知设备
                    showTost("设备无法使用");// 设备故障或者非熙健产品
//                    mDeviceStatusTV.setText("无法使用");
                    break;
                default:
                    break;
            }
        }
    };


    class BlueToothListAdapter extends RecyclerView.Adapter<BlueToothListAdapter.BlueToothViewHolder> {

        @Override
        public BlueToothViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BlueToothViewHolder(LayoutInflater.from(DossierHeartRateSelectBlueToothActivity.this).inflate(R.layout.item_blue_tooth_list, parent, false));
        }

        @Override
        public void onBindViewHolder(BlueToothViewHolder holder, final int position) {

            if (TextUtils.isEmpty(deviceList.get(position).getName())) {
                holder.tvDeviceName.setText(deviceList.get(position).getAddress() + "");
            } else {
                holder.tvDeviceName.setText(deviceList.get(position).getName() + "");
            }
            if (deviceList.get(position).getBondState() == BluetoothDevice.BOND_BONDED) {
                holder.tvDeviceState.setText("可连接");
            } else {
                holder.tvDeviceState.setText("可连接");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOsdkHelper.isRunningRecord()) {
                        showTost("正在记录中，请等待记录完成后再操作");
                        return;
                    }
                    //断开设备
                    try {
                        if (mOsdkHelper.isDeviceConnected()) {
                            mOsdkHelper.close();
                            tvBluetoothDevice.setVisibility(View.GONE);
                            tvDeviceState.setText("已断开");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //连接指定设备
                    if (!TextUtils.isEmpty(deviceList.get(position).getAddress())) {// 连接蓝牙
                        int type = BluetoothDevice.DEVICE_TYPE_CLASSIC;
                        device = deviceList.get(position);
                        tvBluetoothDevice.setVisibility(View.GONE);
                        tvDeviceState.setText("连接中...");
                        scanLeDevice(false);
                        mOsdkHelper.connectBluetooth(deviceList.get(position).getAddress(), type);
                        if (!TextUtils.isEmpty(device.getName()) && device.getName().startsWith("mHealth365")) {

                        } else {

                        }
                    } else {
                        showTost("没有设置蓝牙");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return deviceList.size();
        }

        class BlueToothViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_device_name)
            TextView tvDeviceName;
            @BindView(R.id.tv_device_state)
            TextView tvDeviceState;

            public BlueToothViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

}
