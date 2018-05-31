package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresureHistoryData;
import cn.v1.unionc_user.ui.home.health.DossierDiabetesCureMedActivity;

public class BloodPresureMeasureActivity2 extends BaseActivity {

    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.ll_top_view)
    LinearLayout llTopView;
    @BindView(R.id.tv_measure_time)
    TextView tvMeasureTime;
    @BindView(R.id.tv_high_presure)
    TextView tvHighPresure;
    @BindView(R.id.tv_low_presure)
    TextView tvLowPresure;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_presure_state)
    TextView tvPresureState;
    @BindView(R.id.img_green_arrow)
    ImageView imgGreenArrow;
    @BindView(R.id.ll_drugs)
    LinearLayout llDrugs;
    @BindView(R.id.ll_reports)
    LinearLayout llReports;
    @BindView(R.id.tv_hand_in)
    TextView tvHandIn;
    @BindView(R.id.tv_omron_in)
    TextView tvOmronIn;
    @BindView(R.id.tv_history_record)
    TextView tvHistoryRecord;
    @BindView(R.id.tv_drug)
    TextView tvDrug;
    @BindView(R.id.ll_high_presure)
    LinearLayout llHighPresure;
    @BindView(R.id.ll_low_presure)
    LinearLayout llLowPresure;
    @BindView(R.id.ll_heart_rate)
    LinearLayout llHeartRate;
    @BindView(R.id.img_presture_state)
    ImageView imgPrestureState;
    @BindView(R.id.ll_bottom_btn)
    LinearLayout llBottomBtn;

    private String patientInfoId, dateStr, monitorId = CommonContract.DOSSIERJIANCE_HYPERTENSION;
    ArrayList<String> picUrlList = new ArrayList<>();
    private BloodPresureHistoryData.DataData.BloodPressureData latestPresure;
    private int screenWidth;
    private int imgWidth;
    private BloodPresureHistoryData.DataData.Device device;
    private boolean isResumeRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure_measure2);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isResumeRefresh) {
            getHealthBindBloodPressureData();
        }
    }

    @OnClick({R.id.ll_top_view, R.id.ll_drugs, R.id.ll_reports,
            R.id.tv_hand_in, R.id.tv_omron_in, R.id.tv_history_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top_view:
                if (null == device) {
                    Intent intentBind = new Intent(this, BlueToothDeviceActivity.class);
                    intentBind.putExtra("bloodpresureOnly", "bloodpresureOnly");
                    startActivity(intentBind);
                } else {
                    Intent intentMeausre = new Intent(this, BlueToothMeasureActivity.class);
                    intentMeausre.putExtra("deviceName", device.getDeviceName());
                    intentMeausre.putExtra("deviceBDA", device.getBdaCode());
                    startActivity(intentMeausre);
                }
                break;
            case R.id.ll_drugs:
                Intent intentDrug = new Intent(this, DossierDiabetesCureMedActivity.class);
                intentDrug.putExtra("content", tvDrug.getText().toString());
                startActivityForResult(intentDrug, 8888);
                break;
            case R.id.ll_reports:
                Intent intentReport = new Intent(this, DossierUploadReportActivity.class);
                intentReport.putExtra("patientInfoId", patientInfoId);
                intentReport.putExtra("monitorId", monitorId);
                intentReport.putStringArrayListExtra("piclist", picUrlList);
                intentReport.putExtra("dateStr", dateStr);
                startActivityForResult(intentReport, 7777);
                break;
            case R.id.tv_hand_in:
                Intent intent = new Intent(this, BloodPresureHandMeasureActivity.class);
                intent.putExtra("patientInfoId", patientInfoId);
                startActivity(intent);
                break;
            case R.id.tv_omron_in:
                Intent intentMeausre = new Intent(this, BlueToothMeasureActivity.class);
                intentMeausre.putExtra("deviceName", device.getDeviceName());
                intentMeausre.putExtra("deviceBDA", device.getBdaCode());
                startActivity(intentMeausre);
                break;
            case R.id.tv_history_record:
                Intent intentHistory = new Intent(this,
                        BloodPresureHistoryActivity.class);
                intentHistory.putExtra("patientInfoId", patientInfoId);
                startActivity(intentHistory);
                break;
        }
    }

    private void initData() {
        patientInfoId = getIntent().getStringExtra(DossierHypertensionFragment.KEY_PATIENTINFOID);
    }

    private void initView() {

        setTitle("高血压");
        tvHistoryRecord.bringToFront();
        llBottomBtn.setVisibility(View.GONE);
        llTopView.setVisibility(View.GONE);
    }

    /**
     * 获取血压数据
     */
    private void getHealthBindBloodPressureData() {
        showDialog("加载中...");
//        bindObservable(mAppClient.getHealthBindBloodPressureData(patientInfoId, "1", getUserId()), new Action1<BloodPresureHistoryData>() {
//            @Override
//            public void call(BloodPresureHistoryData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    if (data.getData().getBloodPressure().size() > 0) {
//                        latestPresure = data.getData().getBloodPressure().get(0);
//                        viewToData();
//                    } else {
//                        tvHighPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + 0 + "</font>" +
//                                "<font color=\"#333333\">mmHg</font>"));
//                        tvLowPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + 0 + "</font>" +
//                                "<font color=\"#333333\">mmHg</font>"));
//                        tvHeartRate.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + 0 + "</font>" +
//                                "<font color=\"#333333\">BPM</font>"));
//                    }
//
//                    device = data.getData().getDevice();
//                    if (null == device) {
//                        llTopView.setVisibility(View.VISIBLE);
//                        llTopView.setBackgroundColor(Color.parseColor("#FCF1C0"));
//                        tvTopTitle.setTextColor(Color.parseColor("#796638"));
//                        tvTopTitle.setText("快去绑定设备，自动测量吧！");
//
//                        llBottomBtn.setVisibility(View.VISIBLE);
//                        tvHandIn.setVisibility(View.VISIBLE);
//                        tvHandIn.setBackgroundResource(R.drawable.green_btn_bg);
//                        tvHandIn.setTextColor(Color.parseColor("#ffffff"));
//                        tvOmronIn.setVisibility(View.GONE);
//
//                    } else {
//                        llTopView.setVisibility(View.VISIBLE);
//                        llTopView.setBackgroundColor(Color.parseColor("#C7C7CD"));
//                        tvTopTitle.setTextColor(Color.parseColor("#ffffff"));
//                        tvTopTitle.setText("已绑定设备：" + device.getDeviceName());
//
//                        llBottomBtn.setVisibility(View.VISIBLE);
//                        tvHandIn.setVisibility(View.VISIBLE);
//                        tvOmronIn.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    showToast(data.getCode());
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                closeDialog();
//            }
//        });
    }

    private void viewToData() {
        if (null != latestPresure) {
            tvMeasureTime.setText(latestPresure.getMeasureDate() + " " + latestPresure.getMeasureTime());
            String highPresure = latestPresure.getHighPressure();
            String lowPresure = latestPresure.getLowPressure();
            String heartrate = latestPresure.getPluseRate();
            int highPresureNum = 0;
            int lowPresureNum = 0;
            try {
                highPresureNum = Integer.parseInt(highPresure);
                lowPresureNum = Integer.parseInt(lowPresure);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (highPresureNum >= 140) {
                tvHighPresure.setText(Html.fromHtml("<font color=\"#ed1b24\">" + highPresureNum + "</font>" +
                        "<font color=\"#333333\">mmHg</font>"));
                llHighPresure.setBackgroundResource(R.drawable.img_red_dot_line_circle);
            } else {
                tvHighPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + highPresureNum + "</font>" +
                        "<font color=\"#333333\">mmHg</font>"));
                llHighPresure.setBackgroundResource(R.drawable.img_green_dot_line_circle);
            }
            if (lowPresureNum >= 90) {
                tvLowPresure.setText(Html.fromHtml("<font color=\"#ed1b24\">" + lowPresureNum + "</font>" +
                        "<font color=\"#333333\">mmHg</font>"));
                llLowPresure.setBackgroundResource(R.drawable.img_red_dot_line_circle);
            } else {
                tvLowPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + lowPresureNum + "</font>" +
                        "<font color=\"#333333\">mmHg</font>"));
                llLowPresure.setBackgroundResource(R.drawable.img_green_dot_line_circle);
            }
            tvHeartRate.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + heartrate + "</font>" +
                    "<font color=\"#333333\">BPM</font>"));
            if (TextUtils.isEmpty(latestPresure.getRateName())) {
                tvPresureState.setText("");
            } else {
                tvPresureState.setText(latestPresure.getRateName() + "");
            }
            //设置血压在标尺上的状态
            DisplayMetrics dm = getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            imgPrestureState.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgWidth = imgPrestureState.getWidth();
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imgGreenArrow.getLayoutParams();
                    switch (latestPresure.getRate()) {
                        case 1:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth / 4 - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#06c2ef"));
                            break;
                        case 2:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth / 2 - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#61d3a2"));
                            break;
                        case 3:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth / 2 + (imgWidth / 2) / 4 - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#ead92e"));
                            break;
                        case 4:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth / 2 + (imgWidth / 2) / 2 - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#1cc6a3"));
                            break;
                        case 5:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth / 2 + ((imgWidth / 2) * 3) / 4 - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#ff801f"));
                            break;
                        case 6:
                            lp.leftMargin = (screenWidth - imgWidth) / 2 + imgWidth - imgGreenArrow.getWidth() / 2;
                            tvPresureState.setTextColor(Color.parseColor("#ff391f"));
                            break;

                    }
                    imgGreenArrow.setLayoutParams(lp);
                }
            }, 500);

            tvDrug.setText(latestPresure.getCureMedicine() + "");
            if (!TextUtils.isEmpty(latestPresure.getReportUrl())) {
                picUrlList.clear();
                String[] splitPics = latestPresure.getReportUrl().split(",");
                for (int i = 0; i < splitPics.length; i++) {
                    picUrlList.add(splitPics[i]);
                }
            } else {
                picUrlList.clear();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 8888) {
                isResumeRefresh = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String medicine = data.getStringExtra("content");
                        String reportUrl = "";
                        if (TextUtils.isEmpty(medicine)) {
                            medicine = "";
                        }
                        if (null != latestPresure) {
                            if (TextUtils.isEmpty(latestPresure.getReportUrl())) {
                                reportUrl = "";
                            } else {
                                reportUrl = latestPresure.getReportUrl();
                            }

                        }
                        saveMedicineAndReportData(medicine, reportUrl);
                    }
                }, 100);
//                updateHealthOtherData("", medicineTv.getText().toString(), monitorId, dateStr, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
            } else if (requestCode == 7777) {
                isResumeRefresh = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveMedicineAndReportData(tvDrug.getText().toString().trim() + "", data.getStringExtra("picUrlList"));
                    }
                }, 100);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveMedicineAndReportData(String cureMedicine, String reportUrl) {
        showDialog("保存中...");
        String recordId = "";
        try {
            if (!TextUtils.isEmpty(latestPresure.getRecordId())) {
                recordId = latestPresure.getRecordId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        bindObservable(mAppClient.saveMedicineAndReportData(patientInfoId, recordId, getUserId(), cureMedicine, reportUrl), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    isResumeRefresh = true;
//                    getHealthBindBloodPressureData();
//                } else {
//                    showToast(data.getMessage());
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                closeDialog();
//            }
//        });
    }
}
