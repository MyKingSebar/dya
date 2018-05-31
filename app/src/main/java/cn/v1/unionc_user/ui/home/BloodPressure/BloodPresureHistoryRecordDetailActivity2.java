package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresureHistoryData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresuresaveData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.DossierListData;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.DateUtil;
import cn.v1.unionc_user.ui.home.health.AlarmDialog;
import cn.v1.unionc_user.ui.home.health.DossierWheelViewActivity;
import cn.v1.unionc_user.ui.home.health.IRespCallBack;
import cn.v1.unionc_user.ui.home.health.StringUtil;
import cn.v1.unionc_user.utils.ZXingUtils;

public class BloodPresureHistoryRecordDetailActivity2 extends BaseActivity {

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
    @BindView(R.id.tv_update_date)
    TextView tvUpdateDate;
    @BindView(R.id.llmeasure_date)
    LinearLayout llmeasureDate;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.ll_measure_time)
    LinearLayout llMeasureTime;
    @BindView(R.id.ll_belong_to)
    LinearLayout llBelongTo;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.ll_high_presure)
    LinearLayout llHighPresure;
    @BindView(R.id.ll_low_presure)
    LinearLayout llLowPresure;
    @BindView(R.id.img_presure_state)
    ImageView imgPresureState;
    @BindView(R.id.tv_patient_info)
    TextView tvPatientInfo;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.im_qr)
    ImageView im_qr;
    @BindView(R.id.img_back)
    ImageView img_back;
    @OnClick(R.id.img_back)
void back(){
        finish();
    }


    private Context context;
    private int mResultHour;
    private int mResultMinute;
//    private BloodPresureHistoryData.DataData.BloodPressureData historyItemData;
    private BloodPresuresaveData savedata;
    private int screenWidth;
    private int imgWidth;
    private DossierListData.ListBean patientData;
    private String patientInfoId = "";
    private boolean istextChanged = false;

//    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure_history_record_detail2);
        ButterKnife.bind(this);
        context = this;
        initData();
        initTimeData();
        initView();
    }

    @OnClick({R.id.llmeasure_date, R.id.ll_measure_time, R.id.ll_belong_to, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llmeasure_date:
                //选择日期
                Intent intentSelectDate = DossierWheelViewActivity.getPickViewActivityDate(context, DossierWheelViewActivity.TYPE_DATE, "", YearTimeList, MonthTimeList, DayTimeList,
                        currentYearIndex, currentMonthIndex, currentDayIndex);
                startActivityForResult(intentSelectDate, 9998);
                break;
            case R.id.ll_measure_time:
                //选择时间
                Intent intentSelectTime = DossierWheelViewActivity.getPickViewActivityTwo(context, DossierWheelViewActivity.TYPE_TWO, "", hourTimeeList, minuteTimeeList, currentHourIndex,
                        currentMinuteIndex);
                startActivityForResult(intentSelectTime, 9999);
                break;
            case R.id.ll_belong_to:
//                Intent intentDossier = new Intent(context, DossiersListActviity.class);
//                intentDossier.putExtra("bloodpresureSelect", "patientInfoId=" + historyItemData.getPatientInfoId() + "");
//                startActivityForResult(intentDossier, 9000);
                break;
            case R.id.tv_save:
                saveEdit();
                break;
        }
    }


//    @Override
//    public void onBackPressed() {
//        if (istextChanged) {
//            new AlarmDialog(BloodPresureHistoryRecordDetailActivity2.this, 1, new IRespCallBack() {
//                @Override
//                public boolean callback(int callCode, Object... param) {
//                    if (callCode == 0) {
//                        saveEdit();
//                    } else {
//                        finish();
//                    }
//                    return true;
//                }
//            }, "", "是否保存本次修改？").show();
//        } else {
//            super.onBackPressed();
//        }
//    }

    private void initData() {
//        if (getIntent().hasExtra("historyItemData")) {
//            historyItemData = (BloodPresureHistoryData.DataData.BloodPressureData) getIntent().getSerializableExtra("historyItemData");
//        }
        if (getIntent().hasExtra("savedata")) {
            savedata = (BloodPresuresaveData) getIntent().getSerializableExtra("savedata");
        }
    }

    private void initView() {
        toplayout.setBackgroundColor(getResources().getColor(R.color.green_oml));
//        getactionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        tv_title.setText("测量详情");
        if(null!=savedata){

        }
        if (null != savedata) {
            patientInfoId = savedata.getPatientInfoId();
            tvMeasureTime.setText(savedata.getMeasureDate());
//            tvMeasureTime.setText(savedata.getMeasureDate() + " " + savedata.getMeasureTime());
            tvUpdateDate.setText(savedata.getMeasureDate() + "");
            tvUpdateTime.setText(savedata.getMeasureTime() + "");
            String measureWay = savedata.getMeasureWay();
            if (TextUtils.equals("0", measureWay)) {
                //血压计测量
            }
            if (TextUtils.equals("1", measureWay)) {
                //手动测量
                llBelongTo.setVisibility(View.GONE);
            }
            String highPresure = savedata.getHighPressure();
            String lowPresure = savedata.getLowPressure();
            String heartrate = savedata.getPluseRate();
            int highPresureNum = 0;
            int lowPresureNum = 0;
            try {
                highPresureNum = Integer.parseInt(highPresure);
                lowPresureNum = Integer.parseInt(lowPresure);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvHighPresure.setText(highPresureNum+"");
            tvLowPresure.setText(lowPresureNum+"");
            tvHeartRate.setText(heartrate+"");
//            if (highPresureNum >= 140) {
//                tvHighPresure.setText(Html.fromHtml("<font color=\"#ed1b24\">" + highPresureNum + "</font>" +
//                        "<font color=\"#333333\">mmHg</font>"));
//                llHighPresure.setBackgroundResource(R.drawable.img_red_dot_line_circle);
//            } else {
//                tvHighPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + highPresureNum + "</font>" +
//                        "<font color=\"#333333\">mmHg</font>"));
//                llHighPresure.setBackgroundResource(R.drawable.img_green_dot_line_circle);
//            }
//            if (lowPresureNum >= 90) {
//                tvLowPresure.setText(Html.fromHtml("<font color=\"#ed1b24\">" + lowPresureNum + "</font>" +
//                        "<font color=\"#333333\">mmHg</font>"));
//                llLowPresure.setBackgroundResource(R.drawable.img_red_dot_line_circle);
//            } else {
//                tvLowPresure.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + lowPresureNum + "</font>" +
//                        "<font color=\"#333333\">mmHg</font>"));
//                llLowPresure.setBackgroundResource(R.drawable.img_green_dot_line_circle);
//            }
//            tvHeartRate.setText(Html.fromHtml("<font color=\"#1cc6a3\">" + heartrate + "</font>" +
//                    "<font color=\"#333333\">BPM</font>"));
            tvPresureState.setText(savedata.getRateName() + "");
            //设置血压在标尺上的状态
            DisplayMetrics dm = getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            imgPresureState.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgWidth = imgPresureState.getWidth();
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imgGreenArrow.getLayoutParams();
                    switch (savedata.getRate()) {
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
            if (!TextUtils.isEmpty(savedata.getPatientInfoId())) {
                String sex = "";
                if (TextUtils.equals("0", savedata.getSex())) {
                    sex = "男";
                }
                if (TextUtils.equals("1", savedata.getSex())) {
                    sex = "女";
                }
                tvPatientInfo.setText(savedata.getRealName() + " " + savedata.getRelationShip() + " " +
                        sex + " " + savedata.getAge() + "岁");
            } else {
                tvPatientInfo.setText("");
            }

            Bitmap bitmap = ZXingUtils.createQRImage("医巴士血压二维码"+highPresure+","+lowPresure+","+heartrate+","+savedata.getRate()+","+savedata.getRateName()+","+savedata.getMeasureDate(), 500, 500);
            im_qr.setImageBitmap(bitmap);
        }

        tvUpdateDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                istextChanged = true;
            }
        });
        tvUpdateTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                istextChanged = true;
            }
        });
        tvPatientInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                istextChanged = true;
            }
        });


    }


    /**
     * 保存编辑数据
     *      *token:"yGGSVMEtTZdpdw8zSJJlpzhUgzeBRuKAyrOmdTw4OdQ\u003d",
     * monitorId:"2",
     * heartRate:"65",
     * monitorDate:"2018-05-29 10:38:00",
     * avgMeasure:"0",
     * bdaCode:"resgresgresres",
     * deviceModel:"欧姆龙血压仪",
     * highPressure:"100",
     * lowPressure:"65",
     * deviceType:"0",
     * measureWay:"0",
     * patientInfoId:""
     */
    private void saveEdit() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.saveOMLData(token, "2", savedata.getPluseRate(), savedata.getMeasureDate(),"0",savedata.getBdaCode(),"欧姆龙血压仪", savedata.getHighPressure(), savedata.getLowPressure(),
                "0","0",""), new BaseObserver<BaseData>(context) {

            @Override
            public void onResponse(BaseData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("保存成功");
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                showTost("保存失败");
            }
        });
//        showDialog("保存中...");
//        String updateDate = tvUpdateDate.getText().toString().trim();
//        String updateTime = tvUpdateTime.getText().toString().trim();
//        String measureDate = updateDate + " " + updateTime + ":00";
//        bindObservable(mAppClient.bindHealthRecordData(patientInfoId, getUserId(), historyItemData.getRecordId() + "", measureDate), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    showToast("保存成功");
//                    finish();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9998 && resultCode == RESULT_OK) {
            int year = Integer.parseInt(data.getStringExtra("firstColum"));
            int month = Integer.parseInt(data.getStringExtra("secondColum"));
            int day = Integer.parseInt(data.getStringExtra("thirdColum"));
            String timeStr = data.getStringExtra("firstColum") + "-" + StringUtil.getStrNumWithZero(month) + "-" + StringUtil.getStrNumWithZero(day);
            if (DateUtil.timeCompare(timeStr + " 00:00:00")) {
                Toast.makeText(context,"不可以选择未来的时间",Toast.LENGTH_SHORT);
            } else {
                tvUpdateDate.setText(timeStr);
            }
        } else if (requestCode == 9999 && resultCode == RESULT_OK) {
            //心率测量时间
            try {
                int resultHour = data.getIntExtra("firstIndex", 0);
                int resultMinute = data.getIntExtra("seconeIndex", 0);
                String selectTime = tvMeasureTime.getText().toString().trim() + " " + StringUtil.getStrNumWithZero(resultHour) + ":" + StringUtil.getStrNumWithZero(resultMinute) + ":00";
                if (DateUtil.timeCompare(selectTime)) {
                    Toast.makeText(context,"不可以选择未来的时间",Toast.LENGTH_SHORT);
                } else {
                    mResultHour = resultHour;
                    mResultMinute = resultMinute;
                    tvUpdateTime.setText(StringUtil.getStrNumWithZero(resultHour) + ":" + StringUtil.getStrNumWithZero(resultMinute));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 9000 && resultCode == RESULT_OK) {
            patientData = (DossierListData.ListBean) data.getSerializableExtra("patient");
            if (null != patientData) {
                patientInfoId = patientData.getID();
                String sex = "";
                if (TextUtils.equals("0", patientData.getSEX())) {
                    sex = "男";
                }
                if (TextUtils.equals("1", patientData.getSEX())) {
                    sex = "女";
                }
                tvPatientInfo.setText(patientData.getREAL_NAME() + " " + patientData.getRELATIONSHIP() + " " +
                        sex + " " + patientData.getAGE() + "岁");
            }

        }
    }

    /**
     * 初始化时间部分
     */
    private int currentHour, currentMinute, currentHourIndex, currentMinuteIndex;
    private int currentYearIndex;
    private int currentMonthIndex;
    private int currentDayIndex;
    private ArrayList<String> YearTimeList = new ArrayList<>();
    private ArrayList<String> MonthTimeList = new ArrayList<>();
    private ArrayList<String> DayTimeList = new ArrayList<>();
    private ArrayList<String> hourTimeeList = new ArrayList<>();
    private ArrayList<String> minuteTimeeList = new ArrayList<>();
    private String currentDate;

    private void initTimeData() {
        YearTimeList.clear();
        MonthTimeList.clear();
        DayTimeList.clear();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentYearIndex = 0;
        currentMonthIndex = currentMonth - 1;
        currentDayIndex = currentDay - 1;
        for (int i = currentYear; i > currentYear - 100; i--) {
            YearTimeList.add(i + "");
        }
        for (int i = 1; i <= 12; i++) {
            MonthTimeList.add(i + "");
        }
        int day = 0;
        boolean leayyear = false;
        if (currentYear % 4 == 0 && currentYear % 100 != 0 || currentYear % 400 == 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        switch (currentMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                if (leayyear) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
        }
        for (int i = 1; i <= day; i++) {
            DayTimeList.add(i + "");
        }
        currentDate = currentYear + "-" + StringUtil.getStrNumWithZero(currentMonth) + "-" + StringUtil.getStrNumWithZero(currentDay);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        currentHourIndex = currentHour;
        currentMinuteIndex = currentMinute;
        for (int i = 0; i <= 23; i++) {
            hourTimeeList.add(i + "时");
        }
        for (int i = 0; i <= 59; i++) {
            minuteTimeeList.add(i + "分");
        }
        mResultHour = currentHour;
        mResultMinute = currentMinute;
        //给测量日期一个默认值
        if (null != tvUpdateDate) {
            tvUpdateDate.setText(currentDate);
        }
        //给测量时间一个默认值
        if (null != tvUpdateTime) {
            tvUpdateTime.setText(StringUtil.getStrNumWithZero(currentHour) + ":" + StringUtil.getStrNumWithZero(currentMinute));
        }
    }


//    public Toolbar getactionBarToolbar() {
//        if (mActionBarToolbar == null) {
//            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//            if (mActionBarToolbar != null)
//                setSupportActionBar(mActionBarToolbar);
//        }
//        return mActionBarToolbar;
//    }
}
