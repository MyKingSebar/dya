package cn.v1.unionc_user.ui.home.BloodPressure;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.OMLLostData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.OMLSuccessData;

/**
 * Created by An4 on 2018/5/9.
 */
@SuppressLint("NewApi")
public class TzOmRonBlueToothActivity extends BaseActivity {

    BluetoothManager bManager;
    BluetoothAdapter bAdapter;
    BluetoothDevice mDevice;
    boolean isFirstMatch = true;
    @BindView(R.id.rv_bluetooth_list)
    RecyclerView rvBluetoothList;

    @BindView(R.id.omron_blue_search_img)
    ImageView topSearchImg;

    @BindView(R.id.tv_specification)
    TextView tv_specification;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;
    @OnClick(R.id.img_back)
    public void back(){
        finish();
    }


    private Handler mHandler;
    private List<BluetoothDevice> deviceList = new ArrayList<>();
    public static final int REQUEST_PERMISSION_ACCESS_LOCATION = 1234;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omron_blue_tooth);
        ButterKnife.bind(this);
        initView();
        tvTitle.setText("欧姆龙HEM-9200T");
        // 使用此检查确定BLE是否支持在设备上，然后你可以有选择性禁用BLE相关的功能
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showTost("您的手机版本过低，暂不支持本设备使用");
            finish();
        } else {
            requestPermission();
        }
    }

    private void initView() {
        tv_specification.setBackground(getResources().getDrawable(R.drawable.oml_bz1));
        ObjectAnimator icon_anim = ObjectAnimator.ofFloat(topSearchImg, "rotation", 0.0F, 359.0F);
        icon_anim.setRepeatCount(-1);
        icon_anim.setDuration(1500);
        LinearInterpolator interpolator = new LinearInterpolator();
        icon_anim.setInterpolator(interpolator);
        icon_anim.start();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(searchDevices);
        disconnect();
        timeTaskHadler.removeCallbacksAndMessages(null);
        closeDialog();
        super.onDestroy();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkAccessFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_ACCESS_LOCATION);
                Log.e(getPackageName(), "没有权限，请求权限");
                return;
            }
            Log.e(getPackageName(), "已有定位权限");
            //这里可以开始搜索操作
            initBlueToothAdapter();
        }else{
            initBlueToothAdapter();
        }
        timeTaskHadler.sendEmptyMessageDelayed(0,3*60*1000);
    }

    private Handler timeTaskHadler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            Intent intents = new Intent(TzOmRonBlueToothActivity.this,OmrLoseCoActivity.class);
//            startActivity(intents);
            Log.d("linshi","OMLLostData2");
            OMLLostData eventData = new OMLLostData();
            BusProvider.getInstance().post(eventData);
            TzOmRonBlueToothActivity.this.finish();
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(getPackageName(), "开启权限permission granted!");
                    initBlueToothAdapter();
                } else {
                    Log.e(getPackageName(), "没有定位权限，请先开启!");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initBlueToothAdapter() {
        mHandler = new Handler();
        rvBluetoothList.setLayoutManager(new LinearLayoutManager(this));
        blueToothListAdapter = new BlueToothListAdapter();
        rvBluetoothList.setAdapter(blueToothListAdapter);
        bManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //获取BluetoothAdapter
        if (bManager != null) {
            bAdapter = bManager.getAdapter();
            IntentFilter intent = new IntentFilter();
            intent.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
            intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
            intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
            intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
            intent.addAction(ACTION_GATT_CONNECTED);
            intent.addAction(ACTION_GATT_DISCONNECTED);
            intent.addAction(ACTION_GATT_SERVICES_DISCOVERED);
            intent.addAction(ACTION_DATA_AVAILABLE);
            intent.addAction(EXTRA_DATA);
            intent.addAction(STATUS129);
            registerReceiver(searchDevices, intent);
            if (bAdapter != null) {
                if (!bAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 9999);
                } else {
                    getmHandler.sendEmptyMessage(0);
                }
            }
        }
    }

    public void startScanDevice() {
        if (bAdapter.isDiscovering()) {
            bAdapter.cancelDiscovery();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bAdapter.isDiscovering()) {
                    bAdapter.cancelDiscovery();
                }
            }
        }, 3 * 60 * 1000);
        bAdapter.startDiscovery();
    }

    Handler getmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startScanDevice();
            getmHandler.sendEmptyMessageDelayed(0,10*1000);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (9999 == requestCode) {
            if (resultCode == RESULT_OK) {
                getmHandler.sendEmptyMessage(0);
            } else {
                showTost("请开启蓝牙，连接设备");
            }
        }
    }

    public void addDevice(BluetoothDevice device) {
        boolean deviceFound = false;
        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }
        if (!deviceFound&&!TextUtils.isEmpty(device.getName()) && device.getName().contains("BLEsmart_00000116")) {
            deviceList.add(device);
            blueToothListAdapter.notifyDataSetChanged();
            timeTaskHadler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 蓝牙接收广播
     */
    private BroadcastReceiver searchDevices = new BroadcastReceiver() {
        //接收
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            Bundle b = intent.getExtras();
//            Object[] lstName = b.keySet().toArray();
//
//            // 显示所有收到的消息及其细节
//            for (int i = 0; i < lstName.length; i++) {
//                String keyName = lstName[i].toString();
//                Log.e("bluetooth", keyName + ">>>" + String.valueOf(b.get(keyName)));
//            }
            BluetoothDevice device;
            // 搜索发现设备时，取得设备的信息；注意，这里有可能重复搜索同一设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addDevice(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            }
            //状态改变时
            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        isFirstMatch = true;
                        Log.d("BlueToothTestActivity", "正在配对......");
                        tv_specification.setBackground(getResources().getDrawable(R.drawable.oml_bz2));
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.d("BlueToothTestActivity", "完成配对");
                        mDevice = device;
                        bindDevice();
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("BlueToothTestActivity", "取消配对");
//                        Intent intents = new Intent(TzOmRonBlueToothActivity.this,OmrLoseCoActivity.class);
//                        startActivity(intents);
                        Log.d("linshi","OMLLostData3");
                        OMLLostData eventData = new OMLLostData();
                        BusProvider.getInstance().post(eventData);

                        TzOmRonBlueToothActivity.this.finish();
                    default:
                        break;
                }
            } else if (ACTION_GATT_CONNECTED.equals(action)) { //连接成功
                Log.e(TAG, "Only gatt, just wait");
            } else if (ACTION_GATT_DISCONNECTED.equals(action)) { //连接失败
                Log.e(TAG, "lose");
//                bindDevice();
//                closeDialog();
                new Handler(TzOmRonBlueToothActivity.this.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        uploadMeasureData();
                    }
                });
            } else if (ACTION_GATT_SERVICES_DISCOVERED
                    .equals(action)) //可以通信
            {
                Log.e(TAG, "can trans DATA");
            } else if (ACTION_DATA_AVAILABLE.equals(action)) { //接受到数据
                Log.e(TAG, "RECV DATA");
                String data = intent.getStringExtra(EXTRA_DATA);
                Log.e(TAG, "RECV DATA:"+data);
                new Handler(TzOmRonBlueToothActivity.this.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadMeasureData();
                    }
                },2000);
            }else if(STATUS129.equals(action)){//129gatt内部错误处理
                try {
                    BluetoothGattService mService = mBluetoothGatt.getService(UUID_SERVICE);
                    BluetoothGattService mServiceCts = mBluetoothGatt.getService(UUID_CTS);
                    if(mService!=null) {
                        BluetoothGattCharacteristic batteryLevelGattC = mService.getCharacteristic(UUID_NOTIFY);
                        setCharacteristicIndication(batteryLevelGattC,true);
                    }
                    if(mServiceCts!=null) {
                        BluetoothGattCharacteristic batteryLevelGattCs = mService.getCharacteristic(UUID_CTS_CH);
                        setCharacteristicNotification(batteryLevelGattCs,true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void uploadMeasureData() {
//        if(mResults.size()>0){
//            bindObservable(mAppClient.saveBloodPressureData(new Gson().toJson(mResults), getUserId()), new Action1<BaseData>() {
//                @Override
//                public void call(BaseData baseData) {
//                    if(baseData.getCode().equals("0000")){
//                        //弹窗告诉用户有一个测量数据更新
//                        final BaoxianDialog dialog = new BaoxianDialog(TzOmRonBlueToothActivity.this,"您有新的血压数据已上传",new IRespCallBack() {
//                            @Override
//                            public boolean callback(int callCode, Object... param) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString("fromWhere","1");
//                                bundle.putString("BdaCode",bdCode);
//                                readyGo(BloodPresureHistoryActivity.class,bundle);
//                                return true;
//                            }
//                        });
//                        dialog.show();
//                    }else {
//                        showTost(baseData.getMessage());
//                    }
//                }
//            },new ErrorAction(TzOmRonBlueToothActivity.this));
//            mResults.clear();
//        }
    }

    private void bindDevice() {
//        Intent intent = new Intent(TzOmRonBlueToothActivity.this, BlueToothMeasureActivity.class);
//        intent.putExtra("deviceName", deviceName);
//        intent.putExtra("deviceBDA", bdCode);
//        intent.putExtra("goHome",true);
//        setResult(RESULT_OK);
//        startActivity(intent);

        OMLSuccessData eventData = new OMLSuccessData();
        eventData.setDeviceName(deviceName);
        eventData.setDeviceBDA(bdCode);
        eventData.setGoHome("true");
        BusProvider.getInstance().post(eventData);

        TzOmRonBlueToothActivity.this.finish();
////        closeDialog();
//        bindObservable(mAppClient.saveUserDeviceData("欧姆龙BLEsmart_00000116",bdCode,"1",getUserId()), new Action1<BaseData>() {
//            @Override
//            public void call(final BaseData baseData) {
//                        if(baseData.getCode().equals("0000")) {
//                            new Handler(TzOmRonBlueToothActivity.this.getMainLooper()).postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(TzOmRonBlueToothActivity.this, BlueToothMeasureActivity.class);
//                                    intent.putExtra("deviceName", deviceName);
//                                    intent.putExtra("deviceBDA", bdCode);
//                                    intent.putExtra("goHome",true);
//                                    setResult(RESULT_OK);
//                                    startActivity(intent);
//                                    TzOmRonBlueToothActivity.this.finish();
//                                }
//                            }, 1000);
//                        }else{
//                            Intent intent = new Intent(TzOmRonBlueToothActivity.this,OmrLoseCoActivity.class);
//                            startActivity(intent);
//                            TzOmRonBlueToothActivity.this.finish();
//                        }
//            }
//        },new ErrorAction(TzOmRonBlueToothActivity.this){
//            @Override
//            public void call(Throwable throwable) {
//                Intent intent = new Intent(TzOmRonBlueToothActivity.this,OmrLoseCoActivity.class);
//                startActivity(intent);
//                TzOmRonBlueToothActivity.this.finish();
//            }
//        });
    }



    /**
     * 反注册广播取消蓝牙的配对
     *
     * @param context
     */
//    public void unregisterReceiver(Context context) {
//        unregisterReceiver(searchDevices);
//        if (bAdapter != null)
//            bAdapter.cancelDiscovery();
////        try {
////            if(mDevice!=null) {
////                removeBond(mDevice.getClass(), mDevice);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }


    private BlueToothListAdapter blueToothListAdapter;
    List<OmronResultData> mResults = new ArrayList<>();

    class BlueToothListAdapter extends RecyclerView.Adapter<BlueToothListAdapter.BlueToothViewHolder> {

        @Override
        public BlueToothViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BlueToothViewHolder(LayoutInflater.from(TzOmRonBlueToothActivity.this).
                    inflate(R.layout.item_blue_tooth_omr, parent, false));
        }

        @Override
        public void onBindViewHolder(BlueToothViewHolder holder, final int position) {

            if (TextUtils.isEmpty(deviceList.get(position).getName())) {
                holder.tvDeviceName.setText(deviceList.get(position).getAddress() + "");
            } else {
                holder.tvDeviceName.setText(deviceList.get(position).getName() + "");
                holder.tvDeviceState.setText("Mac:"+deviceList.get(position).getAddress());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //连接指定设备
                    if (!TextUtils.isEmpty(deviceList.get(position).getAddress())) {// 连接蓝牙
                        if ((!TextUtils.isEmpty(deviceList.get(position).getName()) && deviceList.get(position).getName().contains("BLEsmart_00000116"))) {
                            showDialog("");
                            int type = BluetoothDevice.DEVICE_TYPE_CLASSIC;
                            BluetoothDevice device = bAdapter.getRemoteDevice(deviceList.get(position).getAddress());
                            try {
                                bAdapter.cancelDiscovery();
                                bdCode = deviceList.get(position).getAddress().replace(":","");
                                deviceName = "欧姆龙HEM-9200T";
                                mDevice = deviceList.get(position);
                                connect(device);
                                getmHandler.removeCallbacksAndMessages(null);
//                                createBond(device.getClass(), device);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            device = deviceList.get(position);
                        } else {
                            showTost("请连接正确的设备");
                        }
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
            @BindView(R.id.tv_device_bda)
            TextView tvDeviceState;

            public BlueToothViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    private void connect(BluetoothDevice device) throws IOException {
//        // 固定的UUID
//        final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
//        UUID uuid = UUID.fromString(SPP_UUID);
//        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
//        socket.connect();

        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        for(BluetoothDevice devices : bAdapter.getBondedDevices()) {
            if(devices.getAddress().equals(device.getAddress())) {
                isFirstMatch = false;
            }
        }
        BluetoothDevice devicess = bAdapter.getRemoteDevice(device.getAddress());
        mBluetoothGatt = devicess.connectGatt(TzOmRonBlueToothActivity.this, true, mGattCallback);
        timeTaskHadler.sendEmptyMessageDelayed(0,60*1000);
    }

    public void disconnect() {
        if (bAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
        if(mBluetoothGatt!=null){
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        bAdapter.cancelDiscovery();
    }

    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (bAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");//获取蓝牙的连接方法
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        mDevice = btDevice;
        return returnValue.booleanValue();//返回连接状态
    }

    public boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");//获取移除蓝牙设备的方法
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);//得到操作结果
        return returnValue.booleanValue();
    }

    public String TAG = "TZomronBlue";
    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    public final static String STATUS129 = "com.example.bluetooth.le.status129";

    public final static UUID UUID_NOTIFY =
            UUID.fromString("00002a35-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE =
            UUID.fromString("00001810-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_CTS =
            UUID.fromString("00001805-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_CTS_CH =
            UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb");
    private BluetoothGatt mBluetoothGatt;

    String bdCode = "";
    String deviceName = "";

    public void findService(List<BluetoothGattService> gattServices) {
        Log.i(TAG, "Count is:" + gattServices.size());
        if(gattServices.size()==0){
//            Intent intent = new Intent(TzOmRonBlueToothActivity.this,OmrLoseCoActivity.class);
//            startActivity(intent);
            Log.d("linshi","OMLLostData1");
            OMLLostData eventData = new OMLLostData();
            BusProvider.getInstance().post(eventData);
            TzOmRonBlueToothActivity.this.finish();
        }
        for (BluetoothGattService gattService : gattServices) {
            Log.i(TAG, gattService.getUuid().toString());
//            Log.i(TAG, UUID_SERVICE.toString());
            if (gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString())) {
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                Log.i(TAG, "Count is:" + gattCharacteristics.size());
                for (BluetoothGattCharacteristic gattCharacteristic :
                        gattCharacteristics) {
                    Log.i(TAG, gattCharacteristic.getUuid().toString());
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString())) {
                        Log.i(TAG, "in  "+UUID_NOTIFY.toString());
                        setCharacteristicIndication(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        break;
                    }
                }
            }
            if(gattService.getUuid().toString().equalsIgnoreCase(UUID_CTS.toString())){
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                Log.i(TAG, "Count is:" + gattCharacteristics.size());
                for (BluetoothGattCharacteristic gattCharacteristic :
                        gattCharacteristics) {
                    Log.i(TAG, gattCharacteristic.getUuid().toString());
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_CTS_CH.toString())) {
                        Log.i(TAG, UUID_CTS_CH.toString());
                        setCharacteristicNotification(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        break;
                    }
                }
            }
        }
    }

    public void setCharacteristicIndication(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (bAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        if (UUID_NOTIFY.toString().equalsIgnoreCase(characteristic.getUuid().toString())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            Log.w(TAG, "BluetoothGattDescriptor null");
            if(descriptor!=null) {
                Log.w(TAG, "BluetoothGattDescriptor 0x0002");
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
//            characteristic.setValue(,BluetoothGattCharacteristic.FORMAT_UINT16,0);
//            mBluetoothGatt.writeCharacteristic(characteristic);
                mBluetoothGatt.writeDescriptor(descriptor);
            }
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (bAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        if (UUID_CTS_CH.toString().equalsIgnoreCase(characteristic.getUuid().toString())) {
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            int minute = calendar.get(Calendar.MINUTE);
//            int seconds = calendar.get(Calendar.SECOND);
            characteristic.setValue(getHexTime());

//            characteristic.setValue(year,BluetoothGattCharacteristic.FORMAT_UINT16,0);
//            characteristic.setValue(month,BluetoothGattCharacteristic.FORMAT_UINT8,2);
//            characteristic.setValue(day,BluetoothGattCharacteristic.FORMAT_UINT8,3);
//            characteristic.setValue(hour,BluetoothGattCharacteristic.FORMAT_UINT8,4);
//            characteristic.setValue(minute,BluetoothGattCharacteristic.FORMAT_UINT8,5);
//            characteristic.setValue(seconds,BluetoothGattCharacteristic.FORMAT_UINT8,6);
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            Log.i(TAG, "oldStatus=" + status + " NewStates=" + newState);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED;
                    broadcastUpdate(intentAction);
                    Log.i(TAG, "Connected to GATT server.");
                    try{
                        Thread.sleep(50);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    // Attempts to discover services after successful connection.
//                    Log.i(TAG, "Attempting to start service discovery:" +
//                            mBluetoothGatt.discoverServices());
                    gatt.discoverServices();
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED;
//                    mBluetoothGatt.close();
//                    mBluetoothGatt = null;
                    Log.i(TAG, "Disconnected from GATT server.");
                    broadcastUpdate(intentAction);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "onServicesDiscovered received: " + status);
                findService(gatt.getServices());
            } else {
                if(status == 129) {
                    broadcastUpdate(STATUS129);
                }
                if (mBluetoothGatt.getDevice().getUuids() == null)
                    Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            Log.e(TAG, "OnCharacteristicWrite");
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic,
                                          int status) {
            Log.e(TAG, "OnCharacteristicWrite");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt,
                                     BluetoothGattDescriptor bd,
                                     int status) {
            Log.e(TAG, "onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor bd,
                                      int status) {
            Log.e(TAG, "onDescriptorWrite");
            if(!isFirstMatch) {
               new Handler(TzOmRonBlueToothActivity.this.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bindDevice();
                    }
                },2000);
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int a, int b) {
            Log.e(TAG, "onReadRemoteRssi");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int a) {
            Log.e(TAG, "onReliableWriteCompleted");
        }

    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0&&data.length == 18) {
//            final StringBuilder stringBuilder = new StringBuilder(data.length);
            OmronResultData mData = new OmronResultData();
            mData.setBdaCode(bdCode);
            mData.setDeviceType("0");
            mData.setDeviceModel(deviceName);
            mData.setHighPressure(String.valueOf(data[1]));
            mData.setLowPressure(String.valueOf(data[3]));
            mData.setPluseRate(String.valueOf(data[14]));
            mData.setMeasureWay("0");
            mData.setAvgMeasure(String.valueOf(data[5]));
            mData.setMeasureDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mData.setPatientInfoId("");
            mResults.add(mData);
//            for(byte byteChar : data) {
//                stringBuilder.append(String.format("%02X ", byteChar));
//            }
            intent.putExtra(EXTRA_DATA, mData.getHighPressure());
            //这里直接往服务器后台传送数据
//            intent.putExtra(EXTRA_DATA, new String(data));
        }
        sendBroadcast(intent);
    }


    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToByte1(int value){
        byte[] src = new byte[1];
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static String toHex(int num){
        return Integer.toHexString(num);
    }

    public static byte[] getHexTime() {
        String[] currentTime = getCurrentTime().split("-");
        byte yearH = intToBytes2(Integer.parseInt(currentTime[0]))[0];
        byte yearL = intToBytes2(Integer.parseInt(currentTime[0]))[1];
        byte month = intToByte1(Integer.parseInt(currentTime[1]))[0];
        byte day = intToByte1(Integer.parseInt(currentTime[2]))[0];
        byte hour = intToByte1(Integer.parseInt(currentTime[3]))[0];
        byte minute = intToByte1(Integer.parseInt(currentTime[4]))[0];
        byte second = intToByte1(Integer.parseInt(currentTime[5]))[0];
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
        if(week <= 0){
            week = 7;
        }
        Log.i("date", Arrays.toString(new byte[]{yearL,yearH, month, day, hour, minute, second,(byte)week,(byte)4,(byte)8}));
        return new byte[]{ yearL ,yearH , month, day, hour, minute, second,(byte)week,(byte)4,(byte)8};
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String get24Time(long time) {
        Date today = new Date(time);
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        return f.format(today);
    }

    public static String getYMDTime(long time) {
        Date today = new Date(time);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(today);
    }

    public static String getYMTime(long time) {
        Date today = new Date(time);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        return f.format(today);
    }

    /**
     * 在当前日期上加N天后的日期
     *
     * @param n
     * @return
     */
    public static String getNextNDay(int n) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + n);
        return format.format(c.getTime());
    }

    /**
     * 在当前日期上加N天后的日期
     *
     * @param n
     * @return
     */
    public static String getNextNMonth(int n) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + n);
        return format.format(c.getTime());
    }

    private int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<8)
                |(src[offset+1] & 0xFF));
        return value;
    }

}

