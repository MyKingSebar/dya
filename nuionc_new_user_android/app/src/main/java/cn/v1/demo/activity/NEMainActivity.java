package cn.v1.demo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.neliveplayer.sdk.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.NESDKConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.v1.demo.receiver.NELivePlayerObserver;
import cn.v1.demo.receiver.Observer;
import cn.v1.demo.util.HttpPostUtils;
import cn.v1.unionc_user.R;


public class NEMainActivity extends Activity
{
	public final static String TAG = NEMainActivity.class.getSimpleName();
	private static final int REQUEST_CODE = 100;

	private TextView mMediaOption;    //显示播放选项
	private OnClickListener mOnClickEvent; //用于监听按钮事件
	private Button mBtnLiveStream;
	private Button mBtnVideoOnDemand;
	private ImageView mMediaTypeSelected;
	//private ImageView mMediaTypeUnselected;
	private EditText mEditURL; //用于输入网络流地址
	private Button mQRScan; // 二维码扫描
	private Button mBtnPlay;   //开始播放
	private RadioButton mHardware; //硬件解码
	private RadioButton mSoftware; //软件解码
	private TextView mHardwareReminder; //硬件解码提示语
	private String decodeType = "software";  //解码类型，默认软件解码
	private String mediaType = "livestream"; //媒体类型，默认网络直播

	private NESDKConfig config;

	/**   6.0权限处理     **/
	private boolean bPermission = false;
	private final int WRITE_PERMISSION_REQ_CODE = 100;
	private boolean checkPublishPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissions = new ArrayList<>();
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(NEMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(NEMainActivity.this, Manifest.permission.CAMERA)) {
				permissions.add(Manifest.permission.CAMERA);
			}
			if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(NEMainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
				permissions.add(Manifest.permission.READ_PHONE_STATE);
			}
			if (permissions.size() != 0) {
				ActivityCompat.requestPermissions(NEMainActivity.this,
						(String[]) permissions.toArray(new String[0]),
						WRITE_PERMISSION_REQ_CODE);
				return false;
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case WRITE_PERMISSION_REQ_CODE:
				for (int ret : grantResults) {
					if (ret != PackageManager.PERMISSION_GRANTED) {
						return;
					}
				}
				bPermission = true;
				break;
			default:
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/**   6.0权限申请     **/
		bPermission = checkPublishPermission();

		setContentView(R.layout.demo_activity_main);
		config = new NESDKConfig();
		//动态加载
		config.dynamicLoadingConfig = new NEDynamicLoadingConfig();
		//是否开启动态加载功能，默认关闭
//		config.dynamicLoadingConfig.isDynamicLoading = true;
		config.dynamicLoadingConfig.isArmeabi = true;
		config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;
		config.supportDecodeListener = mOnSupportDecodeListener;
		//SDK将内部的网络请求以回调的方式开给上层，如果需要上层自己进行网络请求请实现config.dataUploadListener，如果上层不需要自己进行网络请求而是让SDK进行网络请求，这里就不需要操作config.dataUploadListener
		config.dataUploadListener =  mOnDataUploadListener;
		NELivePlayer.init(this,config);
		NELivePlayerObserver.getInstance().observeNELivePlayerObserver(observer,true);

		mMediaOption = (TextView)findViewById(R.id.mediaOption);
		mMediaOption.setGravity(Gravity.CENTER);

		mBtnLiveStream = (Button)findViewById(R.id.livestreamBtn);
		mBtnVideoOnDemand = (Button)findViewById(R.id.videoOnDemandBtn);

		mMediaTypeSelected = (ImageView)findViewById(R.id.mediaTypeSelected);

		mEditURL          = (EditText)findViewById(R.id.netVideoUrl);
		mQRScan           = (Button)findViewById(R.id.btnScan);
		mBtnPlay          = (Button)findViewById(R.id.play_button);

		mHardware         = (RadioButton)findViewById(R.id.hardware);
		mSoftware         = (RadioButton)findViewById(R.id.software);

		mSoftware.setButtonDrawable(R.drawable.decode_type_selected);
		mHardware.setButtonDrawable(R.drawable.decode_type_unselected);
		mHardwareReminder = (TextView)findViewById(R.id.hardware_reminder);

		DisplayMetrics dm = new DisplayMetrics(); //获取屏幕分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int screenW = dm.widthPixels;
		final int screenH = dm.heightPixels;

		final int buttonWidth = screenW / 2;
		mBtnLiveStream.setWidth(buttonWidth);
		mBtnVideoOnDemand.setWidth(buttonWidth);

		LayoutParams params = mMediaTypeSelected.getLayoutParams();
		params.width = screenW / 2;
		mMediaTypeSelected.setLayoutParams(params);

		mOnClickEvent = new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(NEMainActivity.this, NEVideoPlayerActivity.class);

				switch (v.getId()) {
					case R.id.livestreamBtn:
						mBtnLiveStream.setEnabled(false);
						mBtnVideoOnDemand.setEnabled(true);

						mMediaTypeSelected.setX((float) 0.0);

						mEditURL.setVisibility(View.VISIBLE);
						mEditURL.setHint("请输入直播流地址：URL");
						mHardware.setVisibility(View.VISIBLE);
						mSoftware.setVisibility(View.VISIBLE);
						mHardwareReminder.setVisibility(View.VISIBLE);
						mBtnPlay.setVisibility(View.VISIBLE);
						mediaType = "livestream";
						break;

					case R.id.videoOnDemandBtn:
						mBtnLiveStream.setEnabled(true);
						mBtnVideoOnDemand.setEnabled(false);

						mMediaTypeSelected.setX((float)buttonWidth);

						mEditURL.setVisibility(View.VISIBLE);
						mEditURL.setHint("请输入点播流地址：URL");
						mHardware.setVisibility(View.VISIBLE);
						mSoftware.setVisibility(View.VISIBLE);
						mHardwareReminder.setVisibility(View.VISIBLE);
						mBtnPlay.setVisibility(View.VISIBLE);
						mediaType = "videoondemand";
						break;

					case R.id.btnScan:
//						Intent intent2 = new Intent(NEMainActivity.this, NEQRCodeScanActivity.class);
//						startActivityForResult(intent2, REQUEST_CODE);
						break;

					case R.id.hardware:
						mSoftware.setButtonDrawable(R.drawable.decode_type_unselected);
						mHardware.setButtonDrawable(R.drawable.decode_type_selected);
						decodeType = "hardware";
						break;

					case R.id.software:
						mSoftware.setButtonDrawable(R.drawable.decode_type_selected);
						mHardware.setButtonDrawable(R.drawable.decode_type_unselected);
						decodeType = "software";
						break;

					case R.id.play_button:
						String url = mEditURL.getText().toString();
						Log.d(TAG, "url = "+ url);
						Log.d(TAG, "decode_type = "+ decodeType);

						if ((mediaType.equals("livestream") && url.isEmpty()) || (mediaType.equals("videoondemand") && url.isEmpty())) {
							AlertDialogBuild(0);
							break;
						}

						if(!bPermission){
							Toast.makeText(getApplication(),"请先允许app所需要的权限",Toast.LENGTH_LONG).show();
							bPermission = checkPublishPermission();
							return;
						}
						if(config!=null && config.dynamicLoadingConfig!=null &&config.dynamicLoadingConfig.isDynamicLoading&&!NELivePlayer.isDynamicLoadReady()) {
							Toast.makeText(getApplication(),"请等待加载so文件",Toast.LENGTH_LONG).show();
							return;
						}

						//把多个参数传给NEVideoPlayerActivity
						intent.putExtra("media_type", mediaType);
						intent.putExtra("decode_type", decodeType);
						intent.putExtra("videoPath", url);
						startActivity(intent);
						break;
				}
			}
		};

		mBtnLiveStream.setOnClickListener(mOnClickEvent);
		mBtnVideoOnDemand.setOnClickListener(mOnClickEvent);
		mHardware.setOnClickListener(mOnClickEvent);
		mSoftware.setOnClickListener(mOnClickEvent);
		mBtnPlay.setOnClickListener(mOnClickEvent);
		mQRScan.setOnClickListener(mOnClickEvent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data ==null || data.getExtras() == null || TextUtils.isEmpty(data.getExtras().getString("result"))) {
			return;
		}
		String result = data.getExtras().getString("result");
		if (mEditURL != null) {
			mEditURL.setText(result);
		}
	}

	public void AlertDialogBuild(int flag) //创建对话框
	{
		AlertDialog alertDialog;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("注意");
		if (flag == 0) {
			if (mediaType.equals("livestream")) {
				alertDialogBuilder.setMessage("请输入直播流地址");
			}
			else if (mediaType.equals("videoondemand")) {
				alertDialogBuilder.setMessage("请输入点播流地址");
			}
		}
		else if(flag == 1) {
			if (mediaType.equals("livestream")) {
				alertDialogBuilder.setMessage("请输入正确的直播流地址");
			}
			else if (mediaType.equals("videoondemand")) {
				alertDialogBuilder.setMessage("请输入正确的点播流地址");
			}
		}
		alertDialogBuilder.setCancelable(false)

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						;
					}
				});
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "on pause");
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "on destroy");
		NELivePlayerObserver.getInstance().observeNELivePlayerObserver(observer,false);
		super.onDestroy();
	}

	@Override
	public void onRestart() {
		Log.d(TAG, "on restart");
		super.onRestart();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "on resmue");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.d(TAG, "on start");
		super.onStart();
	}
	private Observer<Void> observer = new Observer<Void>() {
		@Override
		public void onEvent(Void aVoid) {
			//接收到播放器资源释放结束通知
			Log.i(TAG,"onEvent -> NELivePlayer RELEASE SUCCESS!");
		}
	};

	private NELivePlayer.OnDynamicLoadingListener mOnDynamicLoadingListener = new NELivePlayer.OnDynamicLoadingListener() {
		@Override
		public void onDynamicLoading(NEDynamicLoadingConfig.ArchitectureType type, boolean isCompleted) {
			Log.d(TAG, "type:"+type+"，isCompleted:"+isCompleted);
		}
	};

	private NELivePlayer.OnSupportDecodeListener mOnSupportDecodeListener = new NELivePlayer.OnSupportDecodeListener() {
		@Override
		public void onSupportDecode(boolean isSupport) {
			Log.d(TAG, "是否支持H265硬件解码 onSupportDecode isSupport:" + isSupport);
			//如果支持H265硬件解码，那么可以使用H265的视频源进行播放
		}
	};


	private NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {
		@Override
		public boolean onDataUpload(String url, String data) {
			Log.d(TAG, "onDataUpload url:" + url + ", data:" + data);
			sendData(url, data);
			return true;
		}

		@Override
		public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
			Log.d(TAG, "onDataUpload url:" + url + ", params:" + params+",filepaths:"+filepaths);
			return  (new HttpPostUtils(url, params, filepaths).connPost());
		}
	};

	private boolean sendData(final String urlStr, final String content) {
		int response = 0;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(content.getBytes());

			response = conn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				Log.i(TAG, " sendData finished,data:" + content);

			} else {
				Log.i(TAG, " sendData, response: " + response);

			}
		} catch (IOException e) {
			Log.e(TAG, "sendData, recv code is error: " + e.getMessage());

		} catch (Exception e) {
			Log.e(TAG, "sendData, recv code is error2: " + e.getMessage());

		}
		return (response == HttpURLConnection.HTTP_OK);
	}

}
