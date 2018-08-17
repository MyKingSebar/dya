package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.DateUtil;
import cn.v1.unionc_user.ui.home.BloodPressure.views.VerticalTuneWheel;
import cn.v1.unionc_user.ui.home.health.AlarmDialog;
import cn.v1.unionc_user.ui.home.health.DossierWheelViewActivity;
import cn.v1.unionc_user.ui.home.health.IRespCallBack;
import cn.v1.unionc_user.ui.home.health.StringUtil;

public class BloodPresureHandMeasureActivity extends BaseActivity2 {


    @BindView(R.id.tunewheel_high_presure)
    VerticalTuneWheel tunewheelHighPresure;
    @BindView(R.id.tv_high_presure)
    TextView tvHighPresure;
    @BindView(R.id.tunewheel_low_presure)
    VerticalTuneWheel tunewheelLowPresure;
    @BindView(R.id.tv_low_presure)
    TextView tvLowPresure;
    @BindView(R.id.tunewheel_heart_rate)
    VerticalTuneWheel tunewheelHeartRate;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_measure_date)
    TextView tvMeasureDate;
    @BindView(R.id.ll_measure_date)
    LinearLayout llMeasureDate;
    @BindView(R.id.tv_measure_time)
    TextView tvMeasureTime;
    @BindView(R.id.ll_measure_time)
    LinearLayout llMeasureTime;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private Context context;
    private int mResultHour;
    private int mResultMinute;
    private String patientInfoId;
    private boolean istextChanged = false;
    private int higtPresureValure = 130;
    private int lowPresureValue = 80;
    private String currentSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure_hand_measure);
        context = this;
        initTimeData();
        initData();
        initView();
    }


    @Override
    public void onBackPressed() {
        if (istextChanged) {
            new AlarmDialog(BloodPresureHandMeasureActivity.this, 1, new IRespCallBack() {
                @Override
                public boolean callback(int callCode, Object... param) {
                    if (callCode == 0) {
                        saveBloodPressureData();
                    } else {
                        finish();
                    }
                    return true;
                }
            }, "", "是否保存本次修改？").show();
        } else {
            super.onBackPressed();
        }

    }

    @OnClick({R.id.ll_measure_date, R.id.ll_measure_time, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_measure_date:
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
            case R.id.tv_save:
                saveBloodPressureData();
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("patientInfoId")) {
            patientInfoId = getIntent().getStringExtra("patientInfoId");
        }

    }

    private void initView() {

        setTitle("手动录入");
        getactionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvHighPresure.setText(130 + "");
        tvLowPresure.setText(80 + "");
        tvHeartRate.setText(75 + "");

        tunewheelHighPresure.initViewParam(130, 240, VerticalTuneWheel.MOD_TYPE_ONE);
        tunewheelHighPresure.setValueChangeListener(new VerticalTuneWheel.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                higtPresureValure = (int) (value);
                tvHighPresure.setText((int) (value) + "");
            }
        });

        tunewheelLowPresure.initViewParam(80, 240, VerticalTuneWheel.MOD_TYPE_ONE);
        tunewheelLowPresure.setValueChangeListener(new VerticalTuneWheel.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                lowPresureValue = (int) (value);
                tvLowPresure.setText((int) (value) + "");
            }
        });

        tunewheelHeartRate.initViewParam(75, 150, VerticalTuneWheel.MOD_TYPE_ONE);
        tunewheelHeartRate.setValueChangeListener(new VerticalTuneWheel.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvHeartRate.setText((int) (value) + "");
            }
        });

        tvHighPresure.addTextChangedListener(new TextWatcher() {
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
        tvLowPresure.addTextChangedListener(new TextWatcher() {
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
        tvHeartRate.addTextChangedListener(new TextWatcher() {
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
        tvMeasureDate.addTextChangedListener(new TextWatcher() {
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
        tvMeasureTime.addTextChangedListener(new TextWatcher() {
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
     * 保存血压数据
     */
    private void saveBloodPressureData() {
        if (higtPresureValure < lowPresureValue) {
            showToast("输入错误，低压不能大于高压。");
            return;
        }
        showDialog("保存中...");
        Map<String, String> updataData = new HashMap<>();
        updataData.put("patientInfoId", patientInfoId);
        updataData.put("measureDate", tvMeasureDate.getText().toString().trim() + " " + tvMeasureTime.getText().toString().trim() + ":" + currentSecond);
        updataData.put("measureWay", "1");
        updataData.put("pluseRate", tvHeartRate.getText().toString().trim());
        updataData.put("lowPressure", tvLowPresure.getText().toString().trim());
        updataData.put("highPressure", tvHighPresure.getText().toString().trim());
        List<Map> saveData = new ArrayList<>();
        saveData.add(updataData);
        String result = new Gson().toJson(saveData);
//        bindObservable(mAppClient.saveBloodPressureData(result, getUserId()), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    showToast("保存成功");
//                    finish();
//                } else {
//                    showToast(data.getMessage());
//                }
//
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
                showToast("不能选择未来的日期");
            } else {
                tvMeasureDate.setText(timeStr);
            }
        } else if (requestCode == 9999 && resultCode == RESULT_OK) {
            //心率测量时间
            try {
                int resultHour = data.getIntExtra("firstIndex", 0);
                int resultMinute = data.getIntExtra("seconeIndex", 0);
                String selectTime = tvMeasureTime.getText().toString().trim() + " " + StringUtil.getStrNumWithZero(resultHour) + ":" + StringUtil.getStrNumWithZero(resultMinute) + ":00";
                if (DateUtil.timeCompare(selectTime)) {
                    showToast("不可以选择未来的时间");
                } else {
                    mResultHour = resultHour;
                    mResultMinute = resultMinute;
                    tvMeasureTime.setText(StringUtil.getStrNumWithZero(resultHour) + ":" + StringUtil.getStrNumWithZero(resultMinute));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        int currentMiles = calendar.get(Calendar.SECOND);
        if (currentMiles < 10) {
            currentSecond = "0" + currentMiles;
        }else{
            currentSecond = currentMiles+"";
        }
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
        if (null != tvMeasureDate) {
            tvMeasureDate.setText(currentDate);
        }
        //给测量时间一个默认值
        if (null != tvMeasureTime) {
            tvMeasureTime.setText(StringUtil.getStrNumWithZero(currentHour) + ":" + StringUtil.getStrNumWithZero(currentMinute));
        }
    }

}
