//package cn.v1.unionc_user.ui.home.health;
//
//import android.app.Dialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.vodone.cp365.R;
//import com.vodone.cp365.caibodata.BaseData;
//import com.vodone.cp365.caibodata.HeartRateTimesData;
//import com.vodone.cp365.caibodata.UploadEcgData;
//import com.vodone.cp365.dialog.UploadEcgProgressBarDialog;
//import com.vodone.cp365.network.ErrorAction;
//import com.vodone.cp365.service.DossierHeartRateUploadEcgFileService;
//import com.vodone.cp365.util.CaiboSetting;
//import com.vodone.cp365.util.FileSizeUtil;
//import com.vodone.cp365.util.NetWorkUtils;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.BindView;
//import butterknife.OnClick;
//import cn.v1.unionc_user.R;
//import cn.v1.unionc_user.ui.base.BaseActivity;
//import rx.functions.Action1;
//
//
//public class DossierHeartRateAutoFinishActivity extends BaseActivity {
//
//    @BindView(R.id.toolbar_actionbar_title_tv)
//    TextView toolbarActionbarTitleTv;
//    @BindView(R.id.toolbar_actionbar)
//    Toolbar toolbarActionbar;
//    @BindView(R.id.img_read_pic_state)
//    ImageView imgReadPicState;
//    @BindView(R.id.tv_line_one)
//    TextView tvLineOne;
//    @BindView(R.id.tv_line_two)
//    TextView tvLineTwo;
//    @BindView(R.id.tv_line_three)
//    TextView tvLineThree;
//    @BindView(R.id.tv_btn_one)
//    TextView tvBtnOne;
//    @BindView(R.id.tv_btn_two)
//    TextView tvBtnTwo;
//
//    @OnClick({R.id.tv_btn_one, R.id.tv_btn_two})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_btn_one:
//                if(tvLineThree.getVisibility() == View.GONE){
//                    goHistory();
//                }else{
//                    showFinishDialog();
//                }
//                break;
//            case R.id.tv_btn_two:
//                //如果有测量次数，显示申请读图，没有的话显示购买次数
////                Intent buyTimesIntent = new Intent(DossierHeartRateAutoFinishActivity.this, DossierHertRateBuyTimesActivity.class);
////                startActivity(buyTimesIntent);
//                if(TextUtils.equals("购买次数",tvBtnTwo.getText().toString().trim())){
//                    Intent buyTimesIntent = new Intent(DossierHeartRateAutoFinishActivity.this, DossierHertRateBuyTimesActivity.class);
//                    startActivity(buyTimesIntent);
//                }else{
//                    //申请读图
//                    updateHeartRateIsRead();
//
//                }
//                break;
//        }
//    }
//
//
//    /**
//     * 变量
//     */
//    private String userId;
//    private String monitorId;
//    private String healthInfoId;
//    private String measureTime;
//    private int measureType;
//    private String heartPicUrl;
//    private String ecgFile;
//    private int staticId;
//    private List<String> ecgFileList;
//    private final String startUploadAction = "com.example.localbroadcastdemo.UPLOADECGBROADCAST";
//    private final String beingUploadAction = "com.example.localbroadcastdemo.INUPLOADECGBROADCAST";
//    private final String haveUploadProgressAction = "com.example.localbroadcastdemo.HAVEUPLOADECGPROGRESSBROADCAST";
//    private final String uploadFinishAction = "com.example.localbroadcastdemo.UPLOADFINISHBROADCAST";
//    private UploadEcgProgressBarDialog uploadEcgProgressBarDialog;
//    public final int ONE_MINUTE_MEASURE = 1;
//    public final int CONTINUITY_MINUTE_MEASURE = 2;
//    private Context context;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dossier_heart_rate_auto_finish);
//        getactionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tvLineThree.getVisibility() == View.VISIBLE){
//                    showCancelReadDialog();
//                }else{
//                    finish();
//                }
//            }
//        });
//        context = this;
//        registerReceiver(uploadEcgBroadCastRecever, myINtentFilter());
//        startService(new Intent(this, DossierHeartRateUploadEcgFileService.class));
//        initData();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if(tvLineThree.getVisibility() == View.VISIBLE){
//            showCancelReadDialog();
//        }else{
//            finish();
//        }
//    }
//    /**
//     * 按返回弹框
//     */
//    private void showCancelReadDialog() {
//
//        final AlarmDialog dialog = new AlarmDialog(this, AlarmDialog.CANCELANDOK, new IRespCallBack() {
//            @Override
//            public boolean callback(int callCode, Object... param) {
//                if (callCode == AlarmDialog.ALARMDIALOGOK) {
//                    finish();
//                }
//                return true;
//            }
//        }, "", "您是否放弃申请读图，如果放弃，本次测量将无法再进行申请读图。");
//        dialog.tvSmallMessage.setVisibility(View.GONE);
//        dialog.setStr_okbtn("去意已决");
//        dialog.setStr_cancelbtn("我再想想");
//        dialog.show();
//    }
//
//    /**
//     * 按完成测量弹框
//     */
//    private void showFinishDialog() {
//
//        final AlarmDialog dialog = new AlarmDialog(this, AlarmDialog.CANCELANDOK, new IRespCallBack() {
//            @Override
//            public boolean callback(int callCode, Object... param) {
//                if (callCode == AlarmDialog.ALARMDIALOGOK) {
//                    goHistory();
//                }
//                return true;
//            }
//        }, "", "您是否放弃申请读图，如果放弃，本次测量将无法再进行申请读图。");
//        dialog.tvSmallMessage.setVisibility(View.GONE);
//        dialog.setStr_okbtn("去意已决");
//        dialog.setStr_cancelbtn("我再想想");
//        dialog.show();
//    }
//
//    @Override
//    protected void onResume() {
//        getHeartRateReadTimes();
//        String ecgDataJson = CaiboSetting.getStringAttr(this, "uploaded" + measureTime);
//        UploadEcgData ecgData = new Gson().fromJson(ecgDataJson, UploadEcgData.class);
//        if (null != ecgData && !TextUtils.isEmpty(ecgData.getTxtUrl()) && !ecgData.isUploadFinish()) {
//            saveHeartRateResult(ecgData.getTxtUrl());
//        }
//        super.onResume();
//    }
//
//    /**
//     * 广播过滤器
//     *
//     * @return
//     */
//    private IntentFilter myINtentFilter() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(beingUploadAction);
//        intentFilter.addAction(haveUploadProgressAction);
//        intentFilter.addAction(uploadFinishAction);
//        return intentFilter;
//    }
//
//    //监听上传Ecg文件的广播
//    private BroadcastReceiver uploadEcgBroadCastRecever = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            LogUtils.LOGD("uploadEcg_action", intent.getAction() + "");
//            if (intent.getAction().equals(beingUploadAction)) {
//                String ecgData = intent.getStringExtra("ecgData");
//                LogUtils.LOGE("Receiver", ecgData + "");
//                UploadEcgData ecgResult = new Gson().fromJson(ecgData, UploadEcgData.class);
//                updateProgressDialog(ecgResult);
//            }
//            if (intent.getAction().equals(haveUploadProgressAction)) {
//                showToast("已经有任务在进行");
//            }
//            if (intent.getAction().equals(uploadFinishAction)) {
//                String ecgData = intent.getStringExtra("ecgData");
//                LogUtils.LOGE("Receiver", ecgData + "");
//                UploadEcgData ecgResult = new Gson().fromJson(ecgData, UploadEcgData.class);
//                saveHeartRateResult(ecgResult.getTxtUrl());
//            }
//        }
//
//    };
//
//    private void initData() {
//        userId = getIntent().getStringExtra("userId");
//        monitorId = getIntent().getStringExtra("monitorId");
//        healthInfoId = getIntent().getStringExtra("healthInfoId");
//        measureTime = getIntent().getStringExtra("measureTime");
//        ecgFile = getIntent().getStringExtra("ecgFile");
//        measureType = getIntent().getIntExtra("measureType",0);
//        heartPicUrl = getIntent().getStringExtra("heartPicUrl");
//        staticId = getIntent().getIntExtra("staticId",0);
//        String ecgFiles = CaiboSetting.getStringAttr(this, measureTime);
//        ecgFileList = new Gson().fromJson(ecgFiles, new TypeToken<List<String>>() {
//        }.getType());
//        getHeartRateReadTimes();
//
//    }
//
//    /**
//     * 4.2.14.	上传成功后保存心电图结果（对接人:王博尧）
//     */
//    private void saveHeartRateResult(String url) {
//        if(null != uploadEcgProgressBarDialog){
//            uploadEcgProgressBarDialog.dissMiss();
//            uploadEcgProgressBarDialog = null;
//        }
//        showDialog("加载中...");
//        bindObservable(mAppClient.saveHeartRateResult(userId, url, healthInfoId,measureTime,staticId+"",measureType+""), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                if (TextUtils.equals("0000", data.getCode())) {
//                    if(measureType == CONTINUITY_MINUTE_MEASURE){
//                        setUploadFinish(true);
//                    }
//                    tvBtnTwo.setVisibility(View.GONE);
//                    imgReadPicState.setImageResource(R.drawable.img_heart_rate_finish_apply_success);
//                    tvLineOne.setText("申请成功，读图完成后会提示您");
//                    tvLineTwo.setText("请注意查看");
//                    tvLineThree.setVisibility(View.GONE);
//                } else {
//                    showToast(data.getMessage());
//                }
//                closeDialog();
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                closeDialog();
//            }
//        });
//    }
//
//    /**
//     * 4.2.18.	申请读图(对接人:王博尧)
//     */
//    private void updateHeartRateIsRead() {
//        showDialog("读取中...");
//        bindObservable(mAppClient.updateHeartRateIsRead(staticId+""), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                if(TextUtils.equals("0000",data.getCode())){
//                    if(measureType == ONE_MINUTE_MEASURE){
//                        saveHeartRateResult(heartPicUrl);
//                    }else if(measureType == CONTINUITY_MINUTE_MEASURE){
//                        showFileSizeDiaolg();
//                    }
//                }else{
//                    showToast(data.getMessage());
//                }
//                closeDialog();
//            }
//        },new ErrorAction(this){
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                closeDialog();
//            }
//        });
//    }
//    /**
//     * 跳转到历史记录页面
//     */
//    private void goHistory() {
//        Intent intent = new Intent(DossierHeartRateAutoFinishActivity.this, DossierHertRateHistoryActivity.class);
//        intent.putExtra("userId", userId);
//        intent.putExtra("monitorId", monitorId);
//        intent.putExtra("healthInfoId", healthInfoId);
//        startActivity(intent);
//        finish();
//    }
//
//    /**
//     * 跳转读图结果
//     */
//    private void goReadresult(){
//        Intent intent = new Intent(DossierHeartRateAutoFinishActivity.this, DossierHertRateUploadFileActivity.class);
//        intent.putExtra("measureTime", measureTime);
//        intent.putExtra("userId", userId);
//        intent.putExtra("healthInfoId", healthInfoId);
//        intent.putExtra("monitorId", monitorId);
//        intent.putExtra("staticId",staticId);
//        intent.putExtra("isRead","0");
//        intent.putExtra("readType","2");
//        intent.putExtra("from","finish");
//        startActivity(intent);
//        finish();
//
//    }
//    /**
//     * 获取心电图剩余读图次数
//     */
//    private void getHeartRateReadTimes() {
//
//        bindObservable(mAppClient.getHeartRateReadTimes(userId), new Action1<HeartRateTimesData>() {
//            @Override
//            public void call(HeartRateTimesData data) {
//                if (TextUtils.equals("0000", data.getCode())) {
//                    tvLineOne.setText("本次测量结果是否需要医生读图");
//                    tvLineTwo.setText("免费读图（" + data.getData().getTimes() + "/100次）");
//                    tvLineThree.setText("超出免费解读次数后，每次2元。");
//                    if (data.getData().getTimes() > 0) {
//                        tvBtnTwo.setText("申请读图");
//                    } else {
//                        tvBtnTwo.setText("购买次数");
//                    }
//                } else {
//                    showToast(data.getMessage());
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//            }
//        });
//    }
//
//    /**
//     * 取到文件大小，判断网络状态,给出提示语
//     */
//    private void showFileSizeDiaolg() {
//        String fileSize = FileSizeUtil.getAutoFileOrFilesSize(ecgFile);
//        //没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
//        int type = NetWorkUtils.getAPNType(DossierHeartRateAutoFinishActivity.this);
//        if (type == 0) {
//            showToast("没有网络");
//        } else if (type == 1) {
//            showUploadDialog("本次测量文件大小为" + fileSize + "，是否开始上传？", "开始上传", "稍后上传");
//        } else {
//            showUploadDialog("本次测量文件大小为" + fileSize + "，当前网络为移动数据网络，是否开始上传？", "开始上传", "稍后上传");
//        }
//    }
//
//    /**
//     * 基弹框
//     */
//    private void showUploadDialog(String text, final String okbtn, final String cancelbtn) {
//
//        final AlarmDialog dialog = new AlarmDialog(this, AlarmDialog.CANCELANDOK, new IRespCallBack() {
//            @Override
//            public boolean callback(int callCode, Object... param) {
//                if (callCode == AlarmDialog.ALARMDIALOGOK) {
//                    //判断服务是否开启，如果没有开启，开启，如果开启的，发送广播进行上传
//                    if(TextUtils.equals(okbtn,"开始上传")){
//                        setStart(true);
//                        Intent uploadIntent = new Intent(startUploadAction);
//                        uploadIntent.putExtra("measureTime", measureTime);
//                        sendBroadcast(uploadIntent);
//                        showProgressBarDialog();
//                    }
//                    if(TextUtils.equals(okbtn,"否，继续上传")){
//                        showProgressBarDialog();
//                    }
//                }
//                if (callCode == AlarmDialog.ALARMDIALOGCANCEL) {
//                    if(TextUtils.equals(cancelbtn,"是，不上传了")){
//                        setStart(false);
//                        stopService(new Intent(DossierHeartRateAutoFinishActivity.this, DossierHeartRateUploadEcgFileService.class));
//                        goHistory();
//                    }
//                    if(TextUtils.equals(cancelbtn,"稍后上传")){
//                        goReadresult();
//                    }
//                }
//                return true;
//            }
//        }, "", text);
//        dialog.tvSmallMessage.setVisibility(View.GONE);
//        dialog.setStr_okbtn(okbtn);
//        dialog.setStr_cancelbtn(cancelbtn);
//        dialog.show();
//    }
//
//    /**
//     * 带进度条的dialog
//     */
//    private void showProgressBarDialog() {
//        if (null != uploadEcgProgressBarDialog) {
//            uploadEcgProgressBarDialog = null;
//        }
//        uploadEcgProgressBarDialog = new UploadEcgProgressBarDialog(this, new UploadEcgProgressBarDialog.MyDilogListener() {
//            @Override
//            public void btnCancel(Dialog dialog) {
//                //showToast("取消");
//                showUploadDialog("您是否想要取消本次上传，取消后将无法对本次测量进行读图。", "否，继续上传", "是，不上传了");
//                dialog.dismiss();
//            }
//
//            @Override
//            public void btnConfirm(Dialog dialog) {
////                    showToast("确定");
//                goReadresult();
//                dialog.dismiss();
//            }
//        });
//        String ecgData = CaiboSetting.getStringAttr(this, "uploaded" + measureTime);
//        UploadEcgData ecgResult = new Gson().fromJson(ecgData, UploadEcgData.class);
//        if (null != ecgResult) {
//            updateProgressDialog(ecgResult);
//        } else {
//            uploadEcgProgressBarDialog.upLoadView(0, ecgFileList.size(), 0, 0);
//
//        }
//    }
//
//    /**
//     * 更新进度条弹框
//     *
//     * @param ecgResult
//     */
//    private void updateProgressDialog(UploadEcgData ecgResult) {
//        int loadProgress = ecgResult.getCurrentSize();
//        int totalProgress = ecgFileList.size();
//        int progress = 100 * ecgResult.getCurrentSize() / ecgFileList.size();
//        int percent = 100 * ecgResult.getCurrentSize() / ecgFileList.size();
//        if(loadProgress > totalProgress){
//            uploadEcgProgressBarDialog = null;
//        }
//        uploadEcgProgressBarDialog.upLoadView(loadProgress, totalProgress, progress, percent);
//    }
//
//    /**
//     * 设置当前上传文件的状态
//     *
//     * @param isStart
//     * @return
//     */
//    private void setStart(boolean isStart) {
//        String ecgData = CaiboSetting.getStringAttr(this, "uploaded" + measureTime);
//        UploadEcgData ecgResult = new Gson().fromJson(ecgData, UploadEcgData.class);
//        if (null == ecgResult) {
//            ecgResult = new UploadEcgData();
//            ecgResult.setStart(isStart);
//        }else{
//            ecgResult.setStart(isStart);
//        }
//        CaiboSetting.setStringAttr(this, "uploaded" + measureTime,new Gson().toJson(ecgResult));
//
//    }
//
//    /**
//     * 设置当上传完毕的状态
//     * @param isStart
//     */
//    private void setUploadFinish(boolean isStart) {
//        String ecgData = CaiboSetting.getStringAttr(this, "uploaded" + measureTime);
//        UploadEcgData ecgResult = new Gson().fromJson(ecgData, UploadEcgData.class);
//        if (null != ecgResult) {
//            ecgResult.setUploadFinish(isStart);
//        }
//        CaiboSetting.setStringAttr(this, "uploaded" + measureTime,new Gson().toJson(ecgResult));
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(uploadEcgBroadCastRecever);
//    }
//}
