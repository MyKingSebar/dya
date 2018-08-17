package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.home.BloodPressure.views.TzShowHideTitleScrollView;
import cn.v1.unionc_user.ui.home.health.DossierDiabetesCureMedActivity;
import cn.v1.unionc_user.ui.home.health.StringUtil;
import cn.v1.unionc_user.utils.DP_PX;

/**
 * 高血压管理
 * Created by An4 on 2016/9/23.
 */
public class DossierHypertensionActivity extends AppCompatActivity implements ScrollViewListener{
Context context;
    @BindView(R.id.dossierdiabetes_titleupload_tv)
    TextView titleUplodTv;
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolBar;
    @BindView(R.id.tzscrollview)
    TzShowHideTitleScrollView mScrollView;
    @BindView(R.id.dossierdiabetes_title_tv)
    TextView titleTv;
    @BindView(R.id.dossierdiabetes_content_fr)
    FrameLayout mContentFr;
    @BindView(R.id.dossierdiabetes_week_tv)
    TextView weekTv;

    @OnClick(R.id.dossierdiabetes_titleupload_tv)
    void jumpToUploadPic(){
        Intent intent = new Intent(this,DossierUploadReportActivity.class);
        intent.putExtra("patientInfoId",patientInfoId);
        intent.putExtra("monitorId",monitorId);
        intent.putStringArrayListExtra("piclist",picUrlList);
        intent.putExtra("dateStr",dateStr);
        startActivityForResult(intent,7777);
    }

    @BindView(R.id.dossierdiabetes_medicine_tv)
    TextView medicineTv;
    @OnClick(R.id.dossierdiabetes_medicine_rl)
    void jumpToMedicine(){
        Intent intent = new Intent(this,DossierDiabetesCureMedActivity.class);
        intent.putExtra("content",medicineTv.getText().toString());
        startActivityForResult(intent,8888);
    }
    @BindView(R.id.dossierdiabetes_messagecount_tv)
    TextView messageConutTv;
    @OnClick(R.id.dossierdiabetes_messagecount_rl)
    void jumpToDoctorWords(){
//        Intent intent = new Intent(DossierHypertensionActivity.this,HomeEtDoctorWordsActivity.class);
//        intent.putExtra("patientInfoId",patientInfoId);
//        intent.putExtra("monitorId",monitorId);
//        intent.putExtra("dateStr",dateStr);
//        startActivity(intent);
    }

    @BindView(R.id.dossierdiabetes_left_img)
    ImageView leftImg;
    @OnClick(R.id.dossierdiabetes_left_img)
    void jumpToLeftFragment(){
        rightImg.setImageResource(R.drawable.icon_right_s);
        rightImg.setEnabled(true);
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            long startTimeMillions = startDate.getTime();
            long endTimeMillions = endDate.getTime();
            startTime = sdf1.format(new Date(startTimeMillions-7*24*3600*1000))+" 00:00:00";
            endTime = sdf1.format(new Date(endTimeMillions-7*24*3600*1000))+" 23:59:59";
            replaceFragment("left");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @BindView(R.id.dossierdiabetes_right_img)
    ImageView rightImg;
    @OnClick(R.id.dossierdiabetes_right_img)
    void jumpToRightFragment() {
        if(weekTv.getText().toString().equals("本周")){
            rightImg.setEnabled(false);
            rightImg.setImageResource(R.drawable.icon_right_n);
        }else {
            try {
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                long startTimeMillions = startDate.getTime();
                long endTimeMillions = endDate.getTime();
                startTime = sdf1.format(new Date(startTimeMillions + 7 * 24 * 3600 * 1000)) + " 00:00:00";
                endTime = sdf1.format(new Date(endTimeMillions + 7 * 24 * 3600 * 1000)) + " 23:59:59";
                replaceFragment("right");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWeekStr(String str){
        if(str.equals("本周")){
            rightImg.setImageResource(R.drawable.icon_right_n);
            rightImg.setEnabled(false);
        }else{
            rightImg.setImageResource(R.drawable.icon_right_s);
            rightImg.setEnabled(true);
        }
        weekTv.setText(str);
    }

    public void setMedicineStr(String str){
        medicineTv.setText(str);
    }

    public void setMessageConut(String str){
        messageConutTv.setText(StringUtil.showDiffSizeString("医生留言("+str+")","("+str+")","#FF0000",16));
    }

    public void setPicUrlList(ArrayList<String> picUrlList){
        this.picUrlList.clear();
        this.picUrlList.addAll(picUrlList);
    }

    public void setDateStr(String dateStr){
        this.dateStr = dateStr;
    }

    String dateStr,startTime,endTime,monitorId = CommonContract.DOSSIERJIANCE_HYPERTENSION,patientInfoId;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> picUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossierhypertension);
        context=this;
        initData();
        initView();
    }

    private void initData() {
        patientInfoId = getIntent().getStringExtra(DossierHypertensionFragment.KEY_PATIENTINFOID);
    }

    private void initView() {
        mScrollView.setScrollViewListener(this);
        mToolBar.setBackgroundResource(R.drawable.trans_bg);
        titleUplodTv.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_pressed);
        resetStartAndEndTime();
        titleTv.setText("");
        replaceFragment("");
    }

    private void resetStartAndEndTime() {
        Date currentTime = new Date();
        long currentMillions = currentTime.getTime();
        startTime = sdf1.format(new Date(currentMillions-getWeekOfDateStart(currentTime)))+" 00:00:00";
        endTime = sdf1.format(new Date(currentMillions+getWeekOfDateEnd(currentTime)))+" 23:59:59";
    }

    /**
     * {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
     * 获取当周几 并且返回距离周一的时间millions
     * @return 返回距离周一的时间millions
     */
    public static long getWeekOfDateStart(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0)
            w = 7;
        return (w-1)*24*3600*1000;
    }

    public static long getWeekOfDateEnd(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0)
            w = 7;
        return (7-w)*24*3600*1000;
    }

    private void replaceFragment(String inWay) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DossierHypertensionFragment fragment = DossierHypertensionFragment.newInstance(startTime,endTime,monitorId,patientInfoId);
        switch (inWay){
            case "right":
                transaction.setCustomAnimations(R.anim.accordion_right_in, R.anim.accordion_left_out);
                break;
            case "left":
                transaction.setCustomAnimations(R.anim.accordion_left_in, R.anim.accordion_right_out);
                break;
            default:
                break;
        }
        transaction.replace(R.id.dossierdiabetes_content_fr, fragment);
        transaction.commit();
    }

    @Override
    public void onScroll(int y) {
        if(y<=0){
            mToolBar.setBackgroundResource(R.drawable.trans_bg);
            titleUplodTv.setTextColor(getResources().getColor(R.color.white));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_pressed);
            titleTv.setText("");
        }else if (y >= DP_PX.dp2px(context,64)) {
            mToolBar.setBackgroundColor(getResources().getColor(R.color.background));
            titleUplodTv.setTextColor(getResources().getColor(R.color.allcolor));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_bg);
            titleTv.setText(R.string.title_healcare_dossierhypertension);
            mToolBar.getBackground().setAlpha(255);
        } else {
            mToolBar.setBackgroundColor(getResources().getColor(R.color.background));
            titleUplodTv.setTextColor(getResources().getColor(R.color.allcolor));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_bg);
            titleTv.setText(R.string.title_healcare_dossierhypertension);
            mToolBar.getBackground().setAlpha((int)((float) y / (float)  DP_PX.dp2px(context,64)*255));
        }
    }

    public void updateHealthData(String id, String cureMedicine, String monitorId, String monitorDate,
                                 String data1, String data2, String data3, String data4, String data5,
                                 String data6, String data7, String data8, String pic1, String pic2, String pic3, String pic4, String pic5,
                                 String pic6, String diabetesType){
        final String ids = id;
//        bindObservable(mAppClient.saveHealthData(id, cureMedicine, monitorId, patientInfoId, monitorDate, data1, data2, data3, data4, data5, data6,
//                data7, data8, pic1, pic2, pic3, pic4, pic5, pic6, diabetesType), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData baseData) {
//                if(baseData.getCode().equals("0000")){
//                    if(TextUtils.isEmpty(ids)){
//                        if(getSupportFragmentManager().getFragments().size()>0) {
//                            if(getSupportFragmentManager().getFragments().get(0) instanceof DossierHypertensionFragment){
//                                ((DossierHypertensionFragment) getSupportFragmentManager().getFragments().get(0)).initData("保存中...");
//                            }
//                        }
//                    }
//                }
//            }
//        },new ErrorAction(this));
    }

    public void updateHealthOtherData(String id, String cureMedicine, String monitorId, String monitorDate,
                                      String data1, String data2, String data3, String data4, String data5,
                                      String data6, String data7, String data8, String pic1, String pic2, String pic3, String pic4, String pic5,
                                      String pic6, String diabetesType){
//        bindObservable(mAppClient.saveHealthData(id, cureMedicine, monitorId, patientInfoId, monitorDate, data1, data2, data3, data4, data5, data6,
//                data7, data8, pic1, pic2, pic3, pic4, pic5, pic6, diabetesType), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData baseData) {
//            }
//        },new ErrorAction(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 8888){
                medicineTv.setText(data.getStringExtra("content"));
                updateHealthOtherData("", medicineTv.getText().toString(), monitorId, dateStr, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
            }else if(requestCode == 7777){
                //这里为了保证上传报告之后的图片和这个页面打开上传报告页面的图片一致，所以加个刷新接口更新下当前的picUrlList
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        replaceFragment("");
                    }
                },1000);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
