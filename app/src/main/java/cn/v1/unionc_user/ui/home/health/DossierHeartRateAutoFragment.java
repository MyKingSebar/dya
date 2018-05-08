package cn.v1.unionc_user.ui.home.health;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;
import com.mhealth365.osdk.ecgbrowser.RealTimeEcgBrowser;
import com.mhealth365.osdk.ecgbrowser.Scale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.health.file.EcgDataSource;
import cn.v1.unionc_user.ui.home.health.file.EcgFile;

import static com.mhealth365.osdk.EcgOpenApiCallback.EcgConstant.ECG_BATTERY;
import static com.mhealth365.osdk.EcgOpenApiCallback.EcgConstant.ECG_HEART;
import static com.mhealth365.osdk.EcgOpenApiCallback.EcgConstant.ECG_RR;


/**
 * 自动测量心率
 */
public class DossierHeartRateAutoFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.ecg_browser)
    RealTimeEcgBrowser ecgBrowser;
    //测量倒计时
    @BindView(R.id.tv_time)
    TextView tvTime;
    //瞬时心率
    @BindView(R.id.tv_instant_heart_rate)
    TextView tvInstantHeartRate;
    //开始测量
    @BindView(R.id.img_start_measure)
    ImageView imgStartMeasure;
    //坐标纸布局
    @BindView(R.id.fl_ecg_browser)
    public FrameLayout flEcgBrowser;
    //心率
    @BindView(R.id.tv_hert_rate)
    TextView tvHertRate;
    //测量结果
    @BindView(R.id.tv_result)
    TextView tvResult;
    //重新测量
    @BindView(R.id.tv_re_measure)
    TextView tvReMeasure;
    //查看心电图
    @BindView(R.id.tv_view_ecg)
    TextView tvViewEcg;
    //测量完成的布局
    @BindView(R.id.rl_after_testing)
    public RelativeLayout rlAfterTesting;
    //测量日期
    @BindView(R.id.tv_dossier_hert_rate_date)
    TextView tvDossierHertRateDate;
    //不适应症
    @BindView(R.id.tv_dossier_hert_rate_not_indications)
    TextView tvDossierHertRateNotIndications;
    //治疗药物
    @BindView(R.id.tv_dossier_hert_rate_medicine)
    TextView tvDossierHertRateMedicine;
    //心脏病类型
    @BindView(R.id.tv_dossier_hert_rate_type)
    TextView tvDossierHertRateType;
    //保存按钮
    @BindView(R.id.tv_dossier_hert_rate_save)
    TextView tvDossierHertRateSave;
    //底部按钮布局
    @BindView(R.id.ll_bottom_btn)
    LinearLayout llBottomBtn;



    @OnClick({R.id.img_start_measure, R.id.tv_re_measure, R.id.tv_view_ecg, R.id.tv_dossier_hert_rate_not_indications, R.id.tv_dossier_hert_rate_medicine, R.id.tv_dossier_hert_rate_type, R.id.tv_dossier_hert_rate_save})
    public void onClick(View view) {
        switch (view.getId()) {
            //开始测量
            case R.id.img_start_measure:
                //坐标纸布局
                //flEcgBrowser.setVisibility(View.GONE);
                //测量完布局
                //rlAfterTesting.setVisibility(View.VISIBLE);
                if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
                    imgStartMeasure.setVisibility(View.GONE);
                    EcgOpenApiHelper.getInstance().login("8888", mLoginCallback);
                } else if (parent.autoMeasureType == CONTINUITY_MINUTE_MEASURE) {
                    if (null != mOsdkHelper && mOsdkHelper.isRunningRecord()) {
                        try {
                            mOsdkHelper.stopRecord();
                            imgStartMeasure.setImageResource(R.drawable.icon_measure);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        imgStartMeasure.setImageResource(R.drawable.icon_measure_stop);
                        showDialog("准备中...");
                        EcgOpenApiHelper.getInstance().login("8888", mLoginCallback);
                    }
                }
                break;
            //重新检测
            case R.id.tv_re_measure:
                //坐标纸布局
                flEcgBrowser.setVisibility(View.VISIBLE);
                //测量完布局
                rlAfterTesting.setVisibility(View.GONE);
                //底部按钮影藏
                llBottomBtn.setVisibility(View.GONE);
                //测量时间初始化
                tvDossierHertRateDate.setText("");
                break;
            //查看心电图
            case R.id.tv_view_ecg:
                if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
                    Intent intentECGPhoto = new Intent(getActivity(), DossierHeartRateECGPhotoActivity.class);
                    intentECGPhoto.putExtra("pngFileName", pngFileName);
                    startActivity(intentECGPhoto);
                }
                if (parent.autoMeasureType == CONTINUITY_MINUTE_MEASURE) {
                    Intent intentECGPreview = new Intent(getActivity(), EcgDataSourceReviewActivity.class);
                    intentECGPreview.putExtra("ecgFile", ecgFile);
                    startActivity(intentECGPreview);
                }
                break;
            //不适应症
            case R.id.tv_dossier_hert_rate_not_indications:
                Intent intentNoIndications = new Intent(getActivity(), DossierHertrateNotIndicationsActivity.class);
                intentNoIndications.putExtra("noIndication", tvDossierHertRateNotIndications.getText().toString().trim());
                startActivityForResult(intentNoIndications, 6666);
                break;
            //治疗药物
            case R.id.tv_dossier_hert_rate_medicine:
                Intent intentMedicine = new Intent(getActivity(), DossierDiabetesCureMedActivity.class);
                intentMedicine.putExtra("content", tvDossierHertRateMedicine.getText().toString());
                startActivityForResult(intentMedicine, 8888);
                break;
            //心脏病类型
            case R.id.tv_dossier_hert_rate_type:
                Intent intentType = new Intent(getActivity(), DossierHertrateNotIndicationsActivitys.class);
                startActivityForResult(intentType, 9990);
//                Intent intentType = DossierWheelViewActivity.getPickViewActivityOne(getActivity(), DossierWheelViewActivity.TYPE_ONE, "", heartDisease);
//                startActivityForResult(intentType, 9990);
                break;
            //保存
            case R.id.tv_dossier_hert_rate_save:
                monitorDate = tvDossierHertRateDate.getText().toString().trim() + ":00";
                hertrate = tvHertRate.getText().toString().trim();
                notIndications = tvDossierHertRateNotIndications.getText().toString().trim();
                cureMedicine = tvDossierHertRateMedicine.getText().toString().trim();
                hertDieaseType = tvDossierHertRateType.getText().toString().trim();
                monitorResult = tvResult.getText().toString().trim();
                try {
                    if (!new File(pngFileName).exists()) {
//                        saveHertRateData("", cureMedicine, monitorDate, notIndications, hertrate, disId, monitorResult, "");
                    } else {
//                        uploadPic(pngFileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 上传心电图
     *
     * @param imgPath
     */
//    private void uploadPic(String imgPath) {
//        showDialog("保存中...");
//        //按钮保护
//        tvDossierHertRateSave.setEnabled(false);
//        bindObservable(mAppClient.uploadHeartPic(imgPath, UpLoadServiceEnmu.UPLOADPIC.getName(), UpLoadServiceEnmu.UPLOADPIC.getId()),
//                new Action1<UploadPicData>() {
//                    @Override
//                    public void call(UploadPicData uploadPicData) {
//                        String imgUrl = uploadPicData.getUrl();
//                        saveHertRateData("", cureMedicine, monitorDate, notIndications, hertrate, disId, monitorResult, imgUrl);
//                    }
//                }, new ErrorAction(this) {
//                    @Override
//                    public void call(Throwable throwable) {
//                        closeDialog();
//                        tvDossierHertRateSave.setEnabled(true);
//                        super.call(throwable);
//                    }
//                });
//    }

    /**
     * 保存心率数据
     */
//    private void saveHertRateData(String id, String cureMedicine, String monitorDate, String notIndications, String hertrate, String hertDieaseType, String monitorResult, String heartPicUrl) {
//        bindObservable(mAppClient.saveHealthInfoData(id, userId, cureMedicine, monitorId, healthInfoId, monitorDate, "", "", "", "", "", "", hertrate, "", hertDieaseType, monitorResult, heartPicUrl,notIndications), new Action1<SaveHealthInfoData>() {
//            @Override
//            public void call(SaveHealthInfoData data) {
//                LogUtils.LOGD("DossierHeartRateActivity", data.toString());
//                if (TextUtils.equals("0000", data.getCode())) {
//                    //TODO
//                    //如果是一分钟测量保存成功跳转历史记录，自定义测量跳转选择读图
//                    Intent intent = new Intent();
//                    intent.putExtra("userId", userId);
//                    intent.putExtra("monitorId", monitorId);
//                    intent.putExtra("healthInfoId", healthInfoId);
//                    intent.putExtra("measureTime", tvDossierHertRateDate.getText().toString().trim());
//                    intent.putExtra("ecgFile", ecgFile);
//                    if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
//                        intent.setClass(getActivity(), TzDossierRecordSuccActivity.class);
//                    }
//                    if (parent.autoMeasureType == CONTINUITY_MINUTE_MEASURE) {
//                        intent.setClass(getActivity(), DossierHeartRateAutoFinishActivity.class);
//                    }
//                    startActivity(intent);
//                    //将选择一分测量或者自定义测量状态复位
//                    DossierHeartRateAutoActivity dossierHeartRateAutoActivity = (DossierHeartRateAutoActivity) getActivity();
//                    dossierHeartRateAutoActivity.autoMeasureType = 0;
//                    dossierHeartRateAutoActivity.isRadioBtnReset = true;
//                } else {
//                    showTost(data.getMessage());
//                }
//                closeDialog();
//                tvDossierHertRateSave.setEnabled(true);
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                closeDialog();
//                tvDossierHertRateSave.setEnabled(true);
//                super.call(throwable);
//                LogUtils.LOGE("DossierHeartRateActivity", throwable.toString());
//            }
//        });
//    }

    private EcgOpenApiHelper mOsdkHelper;
    private int countEcg;//心率测量计时
    private EcgDataSource demoData;
    private int ecgSample = 200;
    private String monitorDate, hertrate, notIndications, cureMedicine, hertDieaseType, monitorResult;
    public final int ONE_MINUTE_MEASURE = 1;
    public final int CONTINUITY_MINUTE_MEASURE = 2;
    private String ecgFile;
    private List<String> cutFileList = new ArrayList<>();


    /**
     * 登录回调
     */
    EcgOpenApiCallback.LoginCallback mLoginCallback = new EcgOpenApiCallback.LoginCallback() {

        @Override
        public void loginOk() {
            DossierHeartRateAutoActivity dossierHeartRateAutoActivity = (DossierHeartRateAutoActivity) getActivity();
            mOsdkHelper = dossierHeartRateAutoActivity.mOsdkHelper;
            if (TextUtils.isEmpty(mOsdkHelper.getDeviceSN())) {
                //showTost("请先连接设备");
                return;
            }
            if (mOsdkHelper.isRunningRecord()) {
                //showTost("正在记录中，请等待记录完成后再操作");
                return;
            }
            if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
                startRecord(EcgOpenApiHelper.RECORD_MODE.RECORD_MODE_60);
            } else if (parent.autoMeasureType == CONTINUITY_MINUTE_MEASURE) {
                closeDialog();
                startRecord(EcgOpenApiHelper.RECORD_MODE.RECORD_MODE_MANUAL);
            }
        }

        @Override
        public void loginFailed(EcgOpenApiCallback.LOGIN_FAIL_MSG msg) {
            closeDialog();
            String text = "";
            if (msg == null) {
                text = "未知异常";
            } else {
                switch (msg) {
                    case LOGIN_FAIL_NO_NET:
                        text = "无网络";
                        break;
                    case LOGIN_FAIL_NO_OPENID:
                        text = "OpenId为空值";
                        break;
                    case LOGIN_FAIL_NO_RESPOND:
                        text = "服务器未响应";
                        break;
                    case LOGIN_FAIL_NO_USER:
                        text = "无此用户";
                        break;
                    case LOGIN_FAIL_OSDK_INIT_ERROR:
                        text = "sdk初始化异常";
                        break;
                    case LOGIN_FAIL_UNAUTHORIZED:
                        text = "未授权";
                        break;
                    case LOGIN_FAIL_ACCOUNT_FROZEN:
                        text = "账户冻结";
                        break;
                    case LOGIN_FAIL_PACKAGE_NAME_MISMATCH:
                        text = "包名不匹配";
                        break;
                    // 20150716----------------------------
                    case SYS_0:
                        text = "系统错误";// 系统错误
                        break;
                    case SYS_USER_EXIST_E:
                        text = "注册用户已回收";// Openid存在，但是账号已回收
                        break;
                    case SYS_THIRD_PARTY_ID_CHECKING:
                        text = "公司id审核中";// thiredpartyId存在，正在审核未生效
                        break;
                    case SYS_THIRD_PARTY_ID_NOT_EXIST:
                        text = "公司id不存在";// thiredpartyId不存在
                        break;
                    case SYS_APP_ID_CHECKING:
                        text = "appid审核中";// appid存在，正在审核未生效
                        break;
                    case SYS_APP_ID_ERROR:
                        // text ="appid不存在，或者appSecret有错误";//appid不存在，或者appSecret有错误
                        text = "包名 appId 公司id 不匹配";// TODO 包名 appId 公司id 不匹配
                        break;
                    case SYS_APP_PACKAGE_ID_NOT_EXIST:
                        text = "包名不存在";// 包名不正确
                        break;
                    case SYS_LOW_VERSION:
                        text = "sdk版本低需要升级";
                        break;
                    default:
                        break;
                }
                Log.v("Login", "loginFailed:" + msg.name());
            }
            displayMessage.obtainMessage(LOGIN_FAILED, text).sendToTarget();
        }
    };

    private static DossierHeartRateAutoFragment fragment;
    //数据变量
    private String userId = "";
    private String monitorId = "";
    private String healthInfoId = "";
    private ArrayList<PsyDictionaryData.DataBean> hertDiseaseArray = new ArrayList<>();
    private ArrayList<String> heartDisease = new ArrayList<>();
    private String disId = "";
    private String nodicationId = "";

    public static DossierHeartRateAutoFragment newInstance(String userId, String healthInfoId, String monitorId) {

        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("healthInfoId", healthInfoId);
        args.putString("monitorId", monitorId);
        if (null == fragment) {
            fragment = new DossierHeartRateAutoFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    private DossierHeartRateAutoActivity parent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString("userId");
        healthInfoId = getArguments().getString("healthInfoId");
        monitorId = getArguments().getString("monitorId");
        parent = (DossierHeartRateAutoActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dossier_hert_rate_auto, container, false);
        unbinder= ButterKnife.bind(this, view);
        initView();
        initEcg();
//        initHeartDiseaseData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //progressDialog初始化
        if (null == mWaitDialog) {
            mWaitDialog = new ProgressDialog(getActivity().getApplication());
        }
    }


    public void initEcg() {
        ecgBrowser.setSpeedAndGain(Scale.SPEED_25MM_S, Scale.GAIN_10MM_MV);// 设置增益和走速
        ecgBrowser.setSample(500);
        ecgBrowser.showFps(true);
        ecgBrowser.setScreenDPI(ecgBrowser.getDisplayDPI());
        ecgBrowser.clearEcg();
    }

    /**
     * 初始化布局
     */
    public void initView() {
        //坐标纸布局
        flEcgBrowser.setVisibility(View.VISIBLE);
        //测量完布局
        rlAfterTesting.setVisibility(View.GONE);
        //底部按钮
        llBottomBtn.setVisibility(View.GONE);
        //测量时间初始化
        tvDossierHertRateDate.setText("");

    }

    /**
     * 初始化心脏病类型
     */
//    private void initHeartDiseaseData() {
//        bindObservable(mAppClient.psyDictionary("9"), new Action1<PsyDictionaryData>() {
//            @Override
//            public void call(PsyDictionaryData psyDictionaryData) {
//                if(psyDictionaryData.getCode().equals("0000")){
//                    if(psyDictionaryData.getData().size()>0) {
//                        heartDisease.clear();
//                        hertDiseaseArray.clear();
//                        hertDiseaseArray.addAll(psyDictionaryData.getData());
//                        for (PsyDictionaryData.DataBean mData : hertDiseaseArray){
//                            heartDisease.add(mData.getPfsnlName());
//                        }
//                    }
//                }
//            }
//        },new ErrorAction(this));
//    }

    /**
     * 开始记录
     */
    public void startRecord(EcgOpenApiHelper.RECORD_MODE mode) {
        try {
            //result.setText("");
            countEcg = 0;
            mOsdkHelper.startRecord(mode, mRecordCallback);
        } catch (IllegalArgumentException e) {
            imgStartMeasure.setVisibility(View.VISIBLE);
            e.printStackTrace();
            showTost("【开始记录】文件异常,开始记录失败！");
        } catch (Exception e) {
            imgStartMeasure.setVisibility(View.VISIBLE);
            e.printStackTrace();
            showTost("【开始记录】文件异常,开始记录失败！");
        }
    }

    /**
     * 心率记录回调
     */
    EcgOpenApiCallback.RecordCallback mRecordCallback = new EcgOpenApiCallback.RecordCallback() {

        @Override
        public void recordTime(int second) {
            //记录时间
            //Log.w(getClass().getSimpleName(), "recordTime--- second=" + second);
            displayMessage.obtainMessage(ECG_COUNTER, second, -1).sendToTarget();
        }

        //心率分析数据
        @Override
        public void recordStatistics(String id, int averageHeartRate,
                                     int[] heartRectPercentages, int[] rhythmRectPercentages,
                                     int rhythmType) {
            if (null != id) {
                // FIXME 节律异常范围，修改为节律正常范围
                String toastMSG = "平均心率：" + averageHeartRate + "(bpm),心率正常范围：" + heartRectPercentages[0]
                        + "%,稍快稍慢：" + heartRectPercentages[1] + "%,过快过慢：" + heartRectPercentages[2]
                        + "%,节律正常范围：" + rhythmRectPercentages[0]
                        + "%,疑似心率不齐或早搏：" + rhythmRectPercentages[1] + "%,疑似心房颤动或早搏：" + rhythmRectPercentages[2] + "%";
                String warnString = "";
                if (rhythmType == 2) {
                    warnString = "心脏节律异常风险-中" + " --- " + "如您多次在静止、无干扰的状态下测量，异常节律仍高于10%，为了您的健康，请您咨询医师或专业人员。异常节律可以是心律不齐或者早搏、干扰引起，请您咨询医师或专业人员。请您定期或随时监测，跟踪心脏健康风险。";
                } else if (rhythmType == 3) {
                    warnString = "心脏节律异常风险-高" + " --- " + "如您多次在静止、无干扰的状态下测量，异常节律仍高于20%，提示您的心脏可能存在心律失常风险，建议您尽快咨询医师或专业人员。请您定期和随时监测，跟踪心脏健康风险。";
                } else {//rhythmType = 1
                    warnString = "心脏节律异常风险-低" + " --- " + "您的心脏节律异常风险低。请您继续保持良好的生活习惯：清淡饮食、适量运动、保证睡眠、戒烟限酒。少量的异常节律可以是心律不齐或者早搏、干扰引起，请您咨询医师或专业人员。定期和随时监测，有助您提早发现心脏风险。";
                }
                displayMessage.obtainMessage(AVERAGE_HEART_RATE, averageHeartRate + "").sendToTarget();
                displayMessage.obtainMessage(ECG_STAISTICS_RESULT, toastMSG + "\n" + warnString).sendToTarget();
            } else {
                String msg = "【统计数据异常】";// 一般是数据文件错误引起
                displayMessage.obtainMessage(ECG_STAISTICS_RESULT, msg).sendToTarget();
            }
        }

        @Override
        public void recordStart(String id) {
//            Log.w(getClass().getSimpleName(), "recordStart--- id=" + id);
//            Log.w(getClass().getSimpleName(), "recordStart--- countEcg=" + countEcg);
            DossierHeartRateAutoActivity autoActivity = (DossierHeartRateAutoActivity) getActivity();
            ecgSample = autoActivity.ecgSample;
            try {
                demoData = new EcgDataSource(System.currentTimeMillis(), ecgSample);
            } catch (Exception e) {
                e.printStackTrace();
                //showTost("创建记录失败，ecgSample：" + ecgSample);
            }
        }

        @Override
        public void recordEnd(String id) {
            displayMessage.obtainMessage(RECORD_END, id).sendToTarget();
//            Log.w(getClass().getSimpleName(), "recordEnd--- id=" + id);
            if (demoData != null) {
                String rootDir = getFileRoot();
                File file = new File(rootDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String filename = rootDir + System.currentTimeMillis() + ".ecg";
                File demoFile = new File(filename);
                if (demoFile.exists()) {
                    demoFile.delete();
                }
                try {
                    Log.w(getClass().getSimpleName(), "recordEnd--- demoData:" + demoData.toString());
                    if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
                        boolean ok = EcgFile.write(demoFile, demoData);
                        if (ok) {
                            displayMessage.obtainMessage(ECG_SHOW_DATA, filename).sendToTarget();
                            creatEcgPNG(filename);
                        }
                    }
                    if (parent.autoMeasureType == CONTINUITY_MINUTE_MEASURE) {
                        displayMessage.obtainMessage(ECG_SHOW_DATA, filename).sendToTarget();
                        createEcgFile(demoFile);
                        createCutEcgFile(demoData);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                demoData = null;
            }
        }


        @Override
        public void heartRate(int hr) {
            //即时心率
            //Log.w(getClass().getSimpleName(), "heartRate--- hr=" + hr);
            displayMessage.obtainMessage(ECG_HEART, hr, -1).sendToTarget();
        }

        @Override
        public void ecg(int[] value) {
            countEcg++;
            Log.w(getClass().getSimpleName(), "ecg--- " + value[0]);
            ecgBrowser.ecgPackage(value);
            if (demoData != null)
                demoData.addPackage(value);
        }

        @Override
        public void RR(int ms) {
            Log.v(getClass().getSimpleName(), "RR--- rr=" + ms);
            displayMessage.obtainMessage(ECG_RR, ms, -1).sendToTarget();
        }

        @Override
        public void startFailed(EcgOpenApiCallback.RECORD_FAIL_MSG msg) {
            //记录失败
            Log.e(getClass().getSimpleName(), "startFailed--- " + msg.name());
            String text = "";
            switch (msg) {
                case RECORD_FAIL_A_RECORD_RUNNING:
                    text = "已经开始记录了";
                    break;
                case RECORD_FAIL_DEVICE_NO_RESPOND:
                    text = "设备没有响应";// 设备没有响应控制指令，可以重试
                    break;
                case RECORD_FAIL_DEVICE_NOT_READY:
                    text = "设备没有准备好";// 设备未插入，或者未被识别
                    break;
                case RECORD_FAIL_NOT_LOGIN:
                    text = "还没有登陆";
                    break;
                case RECORD_FAIL_OSDK_INIT_ERROR:
                    text = "osdk没有初始化";
                    break;
                case RECORD_FAIL_PARAMETER:
                    text = "参数错误";
                    break;
                case RECORD_FAIL_LOW_VERSION:
                    text = "开发者验证失败,版本低,需要升级sdk";
                    break;
                case RECORD_FAIL_VALIDATE_SDK_FAILED_PACKAGE_NAME_MISMATCH:
                    text = "开发者验证失败,包名不匹配";
                    break;
                case RECORD_FAIL_VALIDATE_SDK_FAILED_ACCOUNT_FROZEN:
                    text = "开发者验证失败,账户冻结";
                    break;
                case RECORD_FAIL_VALIDATE_SDK_FAILED_NETWORK_UNAVAILABLE:
                    text = "开发者验证失败,没有网络";
                    break;
                case RECORD_FAIL_VALIDATE_SDK_FAILED:
                    text = "开发者验证失败";
                    break;
                default:
                    break;
            }
            displayMessage.obtainMessage(START_FAILED, text).sendToTarget();
        }

        @Override
        public void battery(int value) {
            //电池电量
            displayMessage.obtainMessage(ECG_BATTERY, value, -1).sendToTarget();
        }

        @Override
        public void addAccelerate(short x, short y, short z) {
            //三轴加速度
            displayMessage.obtainMessage(ECG_ACC, "x:" + x + " y:" + y + " z:" + z).sendToTarget();
        }

        @Override
        public void addAccelerateVector(float arg0) {
        }

        @Override
        public void leadOff(boolean isOff) {
            Log.i("导联提示", isOff ? "导联脱落" : "导联正常");
        }
    };

    /**
     * 生成切割的ecg
     *
     * @param demoData
     */
    private void createCutEcgFile(final EcgDataSource demoData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cutFileList.addAll(EcgFile.write(demoData));
                    if (cutFileList.size() > 0) {
                        displayMessage.obtainMessage(CUT_FILE_SUCCESS).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 生成图片
     *
     * @param ecgFileName
     */
    private void creatEcgPNG(final String ecgFileName) {
        LogUtils.LOGD("ecgFileName", ecgFileName + "");
        new Thread(new Runnable() {

            @Override
            public void run() {
                Context context = getActivity().getApplicationContext();
                InputStream is = null;
                try {
                    // is = context.getAssets().open(ecgFileName);//心率60，usb设备，振幅1.0mV.ecg
                    is = new FileInputStream(new File(ecgFileName));
                    int[] ecg = readEcg(is);

                    //采样率usb500，蓝牙200
                    int ecgSampleHz = 200;
                    //10秒1行，60秒6行
                    int seconds = 60;
                    int len = ecgSampleHz * seconds;
                    int[] copy = new int[len];
                    for (int i = 0; i < copy.length; i++) {
                        copy[i] = ecg[i];
                    }

                    String DIR_SD_CARD = Environment
                            .getExternalStorageDirectory().getPath();
                    String root = "/mhealth365/demo/sdk/";
                    String dir = DIR_SD_CARD + root;
                    createFileDir(dir);

                    Bitmap bitmap = EcgRender.test(copy, ecgSampleHz);
                    String fileName = dir + "123456.png";
                    fileName = tryANewFile(fileName);
                    boolean ok = createPng(fileName, bitmap);
                    Log.i("Main", "fileName:" + fileName + ",ok:" + ok);
                    String msg = ok ? "生成图片成功！" : "生成图片失败！";
                    displayMessage.obtainMessage(CREATE_PNG, msg).sendToTarget();
                    if (ok) {
                        displayMessage.obtainMessage(CREATE_PNG_SUCCESS, fileName).sendToTarget();
                    } else {
                        displayMessage.obtainMessage(CREAT_PNG_FAIL, 0).sendToTarget();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    displayMessage.obtainMessage(CREAT_PNG_FAIL, 0).sendToTarget();
                }
            }
        }).start();
    }

    /**
     * 创建ecg文件
     *
     * @param demoFile
     * @return
     */
    private void createEcgFile(final File demoFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean ok = EcgFile.write(demoFile, demoData);
                    displayMessage.obtainMessage(CREATE_ECG_SUCCESS, ok).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private final int ECG_GAIN_SPEED = 10001;
    private final int TOAST_TEXT = 10002;
    private final int CPU_STATE = 10003;
    private final int DEBUG_STATE = 10004;
    private final int LIB_NAME = 10005;
    private final int ECG_COUNTER = 10006;
    private final int ECG_SHOW_DATA = 10007;
    private final int ECG_STAISTICS_RESULT = 10008;
    private final int ECG_ACC = 10009;
    private final int RECORD_END = 10010;
    private final int START_FAILED = 10011;
    private final int AVERAGE_HEART_RATE = 10012;
    private final int CREATE_PNG = 10013;
    private final int CREATE_PNG_SUCCESS = 10014;
    private final int CREAT_PNG_FAIL = 10015;
    private final int LOGIN_FAILED = 10016;
    private final int CUT_FILE_SUCCESS = 10017;
    private final int CREATE_ECG_SUCCESS = 10018;

    private ProgressDialog mWaitDialog;
    private String pngFileName = "";
    /**
     * 显示刷新
     */
    Handler displayMessage = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case ECG_HEART:
                    int hrValue = msg.arg1;
                    if (hrValue >= 1 && hrValue <= 355) {
                        //即时心率
                        tvInstantHeartRate.setText("" + hrValue + "bmp");
                        //hr.setText("" + hrValue);
                    } else {
                        // hr.setText("---");
                    }
                    break;
                case ECG_RR:
                    if (msg.arg1 >= 10000) {
                        //rr间期
//                        rr.setText("---");
                    } else {
//                        rr.setText("" + msg.arg1);
                    }
                    break;
                case ECG_COUNTER:
                    //记录时间
                    if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
                        tvTime.setText("00:" + StringUtil.getStrNumWithZero(60 - msg.arg1));
                    } else {
                        tvTime.setText(formatLongToTimeStr(msg.arg1) + "");
                    }
                    break;
                case RECORD_END:
                    String id = (String) msg.obj;
                    if (id == null) {
                        showTost("关闭记录，未生成有效数据");
                    } else {
                        showTost("记录结束，开始统计分析");
                    }
                    //开始记录的按钮
                    imgStartMeasure.setVisibility(View.VISIBLE);
                    //倒计时
                    tvTime.setText("");
                    //计时xinlv
                    tvInstantHeartRate.setText("");
                    //测完后的布局
                    rlAfterTesting.setVisibility(View.VISIBLE);
//                    if(autoMeasureType == CONTINUITY_MINUTE_MEASURE){
//                        tvViewEcg.setVisibility(View.GONE);
//                    }else{
//                        tvViewEcg.setVisibility(View.VISIBLE);
//                    }
                    tvViewEcg.setVisibility(View.VISIBLE);
                    //坐标纸布局
                    flEcgBrowser.setVisibility(View.GONE);
                    //底部保存按钮
                    llBottomBtn.setVisibility(View.VISIBLE);
                    //测量时间
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    int currentMonth = calendar.get(Calendar.MONTH) + 1;
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    tvDossierHertRateDate.setText(currentYear + "-" + StringUtil.getStrNumWithZero(currentMonth)
                            + "-" + StringUtil.getStrNumWithZero(currentDay) + " " + StringUtil.getStrNumWithZero(currentHour)
                            + ":" + StringUtil.getStrNumWithZero(currentMinute)
                    );
                    ecgBrowser.clearEcg();
                    break;
                case START_FAILED:
                    String textStartFailed = (String) msg.obj;
                    showTost("开始记录失败：" + textStartFailed);
                    break;
                //分析结果
                case ECG_STAISTICS_RESULT:
                    showTost("统计分析完成");
                    String text = (String) msg.obj;
                    if (text != null)
                        //分析结果
                        tvResult.setText(text);
                    break;
                //平均心率
                case AVERAGE_HEART_RATE:
                    String averageHeartRate = (String) msg.obj;
                    tvHertRate.setText(averageHeartRate + "");
                    break;
                //ECG文件生成成功，创建心电图和切割文件
                case ECG_SHOW_DATA:
                    ecgFile = (String) msg.obj;
                    mWaitDialog.setMessage("正在生成心电图");
                    mWaitDialog.setIndeterminate(true);
                    mWaitDialog.setCancelable(false);
                    mWaitDialog.show();
//                    showDataFile = (String) msg.obj;
//                    if (showDataFile != null) {
//                        showDialog(0);
//                    }
//                    mEcgBrowser.clearEcg();
//                    clearValue();
                    break;
                case CREATE_ECG_SUCCESS:
                    boolean ok = (boolean) msg.obj;
//                    if(ok){
//                        //生成图片
//                        if (parent.autoMeasureType == ONE_MINUTE_MEASURE) {
//                            mWaitDialog.setMessage("正在生成心电图");
//                            mWaitDialog.setIndeterminate(true);
//                            mWaitDialog.setCancelable(false);
//                            mWaitDialog.show();
//                            creatEcgPNG(ecgFile);
//                        }
//                    }
                    break;
                //创建图片成功与否的提示
                case CREATE_PNG:
                    String message = (String) msg.obj;
                    showTost(message + "");
                    if (mWaitDialog != null && mWaitDialog.isShowing()) {
                        mWaitDialog.dismiss();
                    }
                    break;
                //创建成功png
                case CREATE_PNG_SUCCESS:
                    pngFileName = (String) msg.obj;
                    if (mWaitDialog != null && mWaitDialog.isShowing()) {
                        mWaitDialog.dismiss();
                    }
                    break;
                //创建失败
                case CREAT_PNG_FAIL:
                    if (mWaitDialog != null && mWaitDialog.isShowing()) {
                        mWaitDialog.dismiss();
                    }
                    break;
                //登录失败
                case LOGIN_FAILED:
                    String loginFailedText = (String) msg.obj;
                    imgStartMeasure.setVisibility(View.VISIBLE);
                    showTost("登录失败 " + loginFailedText);
                    //切割文件成功
                case CUT_FILE_SUCCESS:
                    if (mWaitDialog != null && mWaitDialog.isShowing()) {
                        mWaitDialog.dismiss();
                    }
                    CaiboSetting.setStringAttr(getActivity(),
                            tvDossierHertRateDate.getText().toString().trim(),
                            new Gson().toJson(cutFileList));
                    cutFileList.clear();
                case ECG_GAIN_SPEED:
                    float speedValue = msg.arg2 / 10.0f;
                    float gainValue = msg.arg1 / 10.0f;
                    //加速度和增益
                    //speed.setText("" + speedValue + " mm/s");
                    //gain.setText("" + gainValue + " mm/mv");
                    break;
                case TOAST_TEXT:
                    String t = (String) msg.obj;
                    if (t != null)
                        showTost(t);
                    break;

                case ECG_BATTERY:
//                    battery.setText(msg.arg1 + "/3");
                    if (msg.arg1 == 0) {
                        showTost("电量不足");
//                        battery.setText("电量不足：" + battery.getText());
                    }
                    break;
                case ECG_ACC:
                    //三周加速度
                    String acc = (String) msg.obj;
                    if (acc != null) {
                        //tvAcc.setText(acc);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888 && resultCode == getActivity().RESULT_OK) {
            //治疗药物
            tvDossierHertRateMedicine.setText(data.getStringExtra("content"));
        } else if (requestCode == 9990 && resultCode == getActivity().RESULT_OK) {
            //心脏病类型
            tvDossierHertRateType.setText(data.getStringExtra("firstColum"));
            String disName = data.getStringExtra("firstColum");
            for (PsyDictionaryData.DataBean mdatas : hertDiseaseArray){
                if(mdatas.getPfsnlName().equals(disName)){
                    disId = mdatas.getPfsnId();
                }
            }
        } else if (requestCode == 6666 && resultCode == getActivity().RESULT_OK) {
            //不适应症
            tvDossierHertRateNotIndications.setText(data.getStringExtra("noIndication"));
            nodicationId = data.getStringExtra("noIndicationId");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private String getFileRoot() {
        String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EcgSdkDemo/";
        return rootDir;
    }

    public static void createFileDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String formatLongToTimeStr(int l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strHour = "";
        String strMinute = "";
        String strSecond = "";
        if (hour < 10) {
            strHour = String.format("%02d", hour);
        } else {
            strHour = hour + "";
        }
        if (minute < 10) {
            strMinute = String.format("%02d", minute);
        } else {
            strMinute = minute + "";
        }
        if (second < 10) {
            strSecond = String.format("%02d", second);
        } else {
            strSecond = second + "";
        }

        String strtime = strHour + ":" + strMinute + ":" + strSecond;
        return strtime;

    }

    public static String tryANewFile(String filename) {
        String get = filename;
        int count = 0;
        int index = filename.lastIndexOf('.');
        String name = filename.substring(0, index);
        String suffix = filename.substring(index);
        File f = new File(filename);
        // String path = f.getParent();
        // File dir = new File(path);
        // if (!dir.exists())
        // dir.mkdirs();

        if (f.isFile() && f.exists()) {
            count++;
            get = tryANewFile(name, count, suffix);
        }

        return get;
    }

    static String tryANewFile(String filename, int count, String filesuffix) {
        String get = filename + count + filesuffix;
        File f = new File(get);
        // String path = f.getParent();

        if (f.isFile() && f.exists()) {
            count++;
            get = tryANewFile(filename, count, filesuffix);
        }

        return get;
    }


    private boolean createPng(String fileName, Bitmap bitmap) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static int[] readEcg(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String startTimeString = br.readLine();
        long startTime = 0;
        startTime = Long.parseLong(startTimeString);
        String sampleString = br.readLine();
        int sample = 0;
        sample = Integer.parseInt(sampleString);

        ArrayList<int[]> packageList = new ArrayList<int[]>();
        String readLine = null;
        do {
            readLine = null;
            readLine = br.readLine();
            if (readLine != null) {
                // Log.i("EcgFile", readLine);
                int ecgValue = Integer.parseInt(readLine);
                packageList.add(new int[]{ecgValue});
            }

        } while (readLine != null);
        br.close();
        is.close();
        if (packageList.isEmpty())
            return null;


        int[] ecgData = new int[packageList.size()];
        for (int i = 0; i < ecgData.length; i++) {
            ecgData[i] = packageList.get(i)[0];
        }
        return ecgData;
    }

}
