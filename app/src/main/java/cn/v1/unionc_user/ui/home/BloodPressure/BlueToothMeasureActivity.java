package cn.v1.unionc_user.ui.home.BloodPressure;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

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
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

/**
 * Created by An4 on 2018/5/12.
 */
@SuppressLint("NewApi")
public class BlueToothMeasureActivity extends BaseActivity {

    @BindView(R.id.bluetooth_measure_history_tv)
    TextView bluetoothMeasureHistoryTv;
    @BindView(R.id.bluetooth_measure_start_btn)
    Button bluetoothMeasureStartBtn;
    @BindView(R.id.bluetooth_measure_topname_tv)
    TextView bluetoothMeasureTopnameTv;
    @BindView(R.id.bluetooth_measure_name_tv)
    TextView bluetoothMeasureNameTv;
    @BindView(R.id.bluetooth_measure_bda_tv)
    TextView bluetoothMeasureBdaTv;
    @OnClick(R.id.bluetooth_measure_useway_tv)
    void jumpToUserWay(){
//        Intent intent = new Intent(BlueToothMeasureActivity.this,WebviewCanNotGoBackActivity.class);
//        intent.putExtra("h5_url","http://m.yihu365.com/hzb/message/oumul.shtml");
//        intent.putExtra("title","使用说明");
//        startActivity(intent);
    }

    String deviceName = "";
    String deviceBda = "";
    String deviceAddress = "";
    boolean isGoHome = false;
    public static final int REQUEST_PERMISSION_ACCESS_LOCATION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_measure_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        // 使用此检查确定BLE是否支持在设备上，然后你可以有选择性禁用BLE相关的功能
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showTost("您的手机版本过低，暂不支持本设备使用");
            finish();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(searchDevices);
        disconnect();
        getmHandler.removeCallbacksAndMessages(null);
        if(isGoHome){
//            Intent intent = new Intent(BlueToothMeasureActivity.this,MainActivity.class);
//            EventBus.getDefault().post(new CloseActivityEvent());
//            startActivity(intent);
        }
        super.onDestroy();
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
    }

    private void initData() {
        deviceName = getIntent().getStringExtra("deviceName");
        deviceBda = getIntent().getStringExtra("deviceBDA");
        if(getIntent().hasExtra("goHome")){
            isGoHome = getIntent().getBooleanExtra("goHome",false);
        }
        if(!TextUtils.isEmpty(deviceName)) {
            bluetoothMeasureTopnameTv.setText(deviceName);
            bluetoothMeasureNameTv.setText(deviceName);
        }
        if(!TextUtils.isEmpty(deviceBda)) {
            bluetoothMeasureBdaTv.setText("BDA码:" + "******" + deviceBda.substring(deviceBda.length() - 4));
        }
    }

    private void initView() {
        setTitle("欧姆龙HEM-9200T");
    }

    @OnClick({R.id.bluetooth_measure_history_tv, R.id.bluetooth_measure_start_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bluetooth_measure_history_tv:
                Bundle bundle = new Bundle();
                bundle.putString("fromWhere","1");
                bundle.putString("BdaCode",deviceBda);
                readyGo(BloodPresureHistoryActivity.class,bundle);
                break;
            case R.id.bluetooth_measure_start_btn:
                readyGo(BloodPresureDescriptionActivity.class);
                break;
        }
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
    }
    BluetoothManager bManager;
    BluetoothAdapter bAdapter;
    private Handler mHandler;

    Handler getmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startScanDevice();
            getmHandler.sendEmptyMessageDelayed(0,10*1000);
            super.handleMessage(msg);
        }
    };

    private void initBlueToothAdapter() {
        mHandler = new Handler();
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
                    startActivityForResult(enableBtIntent, REQUEST_PERMISSION_ACCESS_LOCATION);
                } else {
                    startScanDevice();
                    if (mBluetoothGatt != null) {
                        mBluetoothGatt.close();
                        mBluetoothGatt = null;
                    }
                    for(BluetoothDevice devices : bAdapter.getBondedDevices()) {
                        if(devices.getAddress().replace(":","").equals(deviceBda)) {
                            BluetoothDevice devicess = bAdapter.getRemoteDevice(devices.getAddress());
                            mBluetoothGatt = devicess.connectGatt(this, true, mGattCallback);
                        }
                    }
                    getmHandler.sendEmptyMessage(0);
                }
            }
        }

    }

    public void startScanDevice() {
        if (bAdapter.isDiscovering()) {
            bAdapter.cancelDiscovery();
        }
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bAdapter.isDiscovering()) {
                    bAdapter.cancelDiscovery();
                }
            }
        }, 5 * 60 * 1000);
        bAdapter.startDiscovery();
    }

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

    private List<BluetoothDevice> deviceList = new ArrayList<>();

    boolean isMatch = false;

    public void addDevice(BluetoothDevice device) {
//        boolean deviceFound = false;
//        for (BluetoothDevice listDev : deviceList) {
//            if (listDev.getAddress().equals(device.getAddress())) {
//                deviceFound = true;
//                break;
//            }
//        }
        if (!TextUtils.isEmpty(device.getName()) && device.getName().contains("BLEsmart_00000116")) {
//            deviceList.add(device);
            if(device.getName().contains(deviceBda)) {
                //绑定的蓝牙设备 已经匹配过了
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                }
                mBluetoothGatt = device.connectGatt(this, true, mGattCallback);
            }
        }
    }

    /**
     * 蓝牙接收广播
     */
    private BroadcastReceiver searchDevices = new BroadcastReceiver() {
        //接收
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
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
                        Log.d("BlueToothTestActivity", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.d("BlueToothTestActivity", "完成配对");
                        bindDevice();
                        isMatch = true;
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("BlueToothTestActivity", "取消配对");
                        isMatch = false;
                    default:
                        break;
                }
            } else if (ACTION_GATT_CONNECTED.equals(action)) { //连接成功
                Log.e(TAG, "Only gatt, just wait");
            } else if (ACTION_GATT_DISCONNECTED.equals(action)) { //连接失败
                Log.e(TAG, "lose");
                new Handler(BlueToothMeasureActivity.this.getMainLooper()).post(new Runnable() {
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
                new Handler(BlueToothMeasureActivity.this.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadMeasureData();
                    }
                },2000);
            }else if(STATUS129.equals(action)){
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

    private void bindDevice() {
////        closeDialog();
//        bindObservable(mAppClient.saveUserDeviceData("欧姆龙BLEsmart_00000116",deviceBda,"1",getUserId()), new Action1<BaseData>() {
//            @Override
//            public void call(final BaseData baseData) {
//            }
//        },new ErrorAction(BlueToothMeasureActivity.this){
//            @Override
//            public void call(Throwable throwable) {
//            }
//        });
    }

    /**
     *  uploadMeasureData:[{"avgMeasure":"81","bdaCode":"B0495F034800","deviceModel":"欧姆龙HEM-9200T","deviceType":"0","highPressure":"99","lowPressure":"72","measureDate":"2018-05-28 15:49:03","measureWay":"0","patientInfoId":"","pluseRate":"89"}]
     *
     */
    private void uploadMeasureData() {Log.d("linshi","uploadMeasureData:"+new Gson().toJson(mResults));
//        if(mResults.size()>0){
//            bindObservable(mAppClient.saveBloodPressureData(new Gson().toJson(mResults), getUserId()), new Action1<BaseData>() {
//                @Override
//                public void call(BaseData baseData) {
//                    if(baseData.getCode().equals("0000")){
//                        //弹窗告诉用户有一个测量数据更新
//                        final BaoxianDialog dialog = new BaoxianDialog(BlueToothMeasureActivity.this,"您有新的血压数据已上传",new IRespCallBack() {
//                            @Override
//                            public boolean callback(int callCode, Object... param) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString("fromWhere","1");
//                                bundle.putString("BdaCode",deviceBda);
//                                readyGo(BloodPresureHistoryActivity.class,bundle);
//                                return true;
//                            }
//                        });
//                        dialog.show();
//                    }else {
//                        showToast(baseData.getMessage());
//                    }
//                }
//            },new ErrorAction(BlueToothMeasureActivity.this));
//            mResults.clear();
//        }
    }

    public void findService(List<BluetoothGattService> gattServices) {
        Log.i(TAG, "Count is:" + gattServices.size());
        for (BluetoothGattService gattService : gattServices) {
            Log.i(TAG, gattService.getUuid().toString());
//            Log.i(TAG, UUID_SERVICE.toString());
            if (gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString())) {
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                Log.i(TAG, "Count is:" + gattCharacteristics.size());
                for (final BluetoothGattCharacteristic gattCharacteristic :
                        gattCharacteristics) {
                    Log.i(TAG, gattCharacteristic.getUuid().toString());
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString())) {
                        Log.i(TAG, "in  "+UUID_NOTIFY.toString());
                        setCharacteristicIndication(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        new Handler(BlueToothMeasureActivity.this.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                broadcastUpdate(ACTION_DATA_AVAILABLE,gattCharacteristic);
                            }
                        },2000);
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
                if(!isMatch) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                }else{
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                }
//            characteristic.setValue(,BluetoothGattCharacteristic.FORMAT_UINT16,0);
//            mBluetoothGatt.writeCharacteristic(characteristic);
                mBluetoothGatt.writeDescriptor(descriptor);
            }
            mBluetoothGatt.readCharacteristic(characteristic);
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

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
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
                    try{
                        Thread.sleep(50);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.i(TAG, "Connected to GATT server.");
                    // Attempts to discover services after successful connection.
                    Log.i(TAG, "Attempting to start service discovery:" +
                            mBluetoothGatt.discoverServices());
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED;
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
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

    List<OmronResultData> mResults = new ArrayList<>();

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0&&data.length == 18) {
//            final StringBuilder stringBuilder = new StringBuilder(data.length);
            OmronResultData mData = new OmronResultData();
            mData.setBdaCode(deviceBda);
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

}
