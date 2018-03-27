package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.ClinicInfoData;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.HospitalDoctorAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.ScrollListView;

public class HospitalDetailActivity extends BaseActivity {

    @Bind(R.id.tv_summary)
    TextView tvSummary;


    @Bind(R.id.tv_open)
    TextView tvOpen;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_hospital)
    ImageView imgHospital;
    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @Bind(R.id.labels)
    LabelsView labels;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.img_dial)
    ImageView imgDial;
    @Bind(R.id.listView)
    ScrollListView listView;

    @Bind(R.id.ll_recommend)
    LinearLayout llRecommend;
    @Bind(R.id.ll_follow)
    LinearLayout llFollow;
    @Bind(R.id.ll_comment)
    LinearLayout llComment;
    @Bind(R.id.ll_bottom_sheet)
    LinearLayout llBottomSheet;
    @Bind(R.id.ll_recommend_sheet)
    LinearLayout llRecommendSheet;
    @Bind(R.id.img_follow)
    ImageView imgFollow;
    @Bind(R.id.tv_recommend)
    TextView tvRecommend;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.img_recommend)
    ImageView imgRecommend;
    @Bind(R.id.cb_recommend)
    CheckBox cbRecommend;
    @Bind(R.id.cb_no_recommend)
    CheckBox cbNoRecommend;

    private String recommendNum;
    private String attention;

    private int mLines;


    private String clinicId;

    //诊所参数
    private String IsHaveContributing;
    private String AUniress;
    private String EvaCount;
    private String ImagePath;
    private String Tel;
    private String Latitude;
    private String CollectionCount;
    private String RecommendCount;
    private String Longitude;
    private String Distance;
    private String Notes;
    private String Name;
    private List<String> Tips;

    private HospitalDoctorAdapter hospitalDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        initBottomSheet();
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.img_dial,R.id.ll_recommend,R.id.ll_follow,R.id.ll_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.img_dial:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + Tel);
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.ll_recommend:
                if (!isLogin()) {
                    showTost("登录后推荐");
                } else {
                    llBottomSheet.setVisibility(View.GONE);
                    llRecommendSheet.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_follow:
                if (!isLogin()) {
                    showTost("登录后关注");
                } else {
                    if (TextUtils.equals("0", attention)) {
                        attentionDoctor("0");
                    }
                    if (TextUtils.equals("1", attention)) {
                        attentionDoctor("1");
                    }
                }
                break;
            case R.id.ll_comment:
                if (!isLogin()) {
                    showTost("登录后评论");
                } else {
                    Intent intent2 = new Intent(context, EvaluateActivity.class);
                    intent2.putExtra("clinicId", clinicId);
                    startActivity(intent2);
                }
                break;
        }
    }
private void initData(){
    if (getIntent().hasExtra("clinicId")) {
        clinicId = getIntent().getStringExtra("clinicId");
        initfragmentData();
        Logger.d(clinicId);
    }



}
    private void initView() {
        tvSummary.post(new Runnable() {
            @Override
            public void run() {
                mLines = tvSummary.getLineCount();
                if (mLines > 1) {
                    tvSummary.setMaxLines(2);
                    tvSummary.setEllipsize(TextUtils.TruncateAt.END);
                    tvOpen.setVisibility(View.VISIBLE);
                }else{
                    tvOpen.setVisibility(View.GONE);
                }
                tvOpen.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        tvSummary.setMaxLines(mLines);
                        tvOpen.setVisibility(View.GONE);
                    }
                });
            }
        });
//        listView.setFocusable(false);
        hospitalDoctorAdapter = new HospitalDoctorAdapter(context);
        listView.setAdapter(hospitalDoctorAdapter);
    }

private void initfragmentData(){

}

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(clinicId)) {
            getCliniInfo();
        }
    }

    private void getCliniInfo(){
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getClinicInfo(token, clinicId,
                (String) SPUtil.get(context, Common.LONGITUDE, ""),
                (String) SPUtil.get(context, Common.LATITUDE, "")),
                new BaseObserver<ClinicInfoData>(context) {
                    @Override
                    public void onResponse(ClinicInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            bindData(data);
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }

    private void bindData(ClinicInfoData data) {
        ClinicInfoData.DataData.ClinicData clinicData = data.getData().getClinicInfo();
        List<ClinicInfoData.DataData.DoctorsData> doctorData = data.getData().getDoctors();

        IsHaveContributing=clinicData.getIsHaveContributing();
        AUniress=clinicData.getAUniress();
        EvaCount=clinicData.getEvaCount();
        ImagePath=clinicData.getImagePath();
        Tel=clinicData.getTel();
        Latitude=clinicData.getLatitude();
        CollectionCount=clinicData.getCollectionCount();
        RecommendCount=clinicData.getRecommendCount();
        Longitude=clinicData.getLongitude();
        Distance=clinicData.getDistance();
        Notes=clinicData.getNotes();
        Name=clinicData.getName();
        Tips=clinicData.getTips();
        Glide.with(context).load(ImagePath).into(imgHospital);
        tvHospitalName.setText(Name);
        tvSummary.setText(Notes);
        tvAddress.setText(AUniress);
        tvDistance.setText(Distance+"km");
        labels.setLabels(Tips);

        hospitalDoctorAdapter.setData(doctorData);

        tvRecommend.setText("推荐" + clinicData.getRecommendCount());
        recommendNum = clinicData.getIsRecom();
        if (TextUtils.equals("0", clinicData.getIsRecom())) {
            imgRecommend.setImageResource(R.drawable.icon_recommend_btn);
        }
        if (TextUtils.equals("1", clinicData.getIsRecom())) {
            cbNoRecommend.setChecked(true);
            imgRecommend.setImageResource(R.drawable.icon_upper_no_recommend_select);
        }
        if (TextUtils.equals("5", clinicData.getIsRecom())) {
            cbRecommend.setChecked(true);
            imgRecommend.setImageResource(R.drawable.icon_upper_recommend_select);
        }
        attention = clinicData.getIsAttention();
        if (TextUtils.equals("0", clinicData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_grey);
        }
        if (TextUtils.equals("1", clinicData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_red);
        }
        tvCommentNum.setText("评论" + clinicData.getEvaCount());

//        tvDoctorName.setText(doctorsData.getDoctorName() + "");
//        tvDepartment.setText(doctorsData.getDepartName() + "  " + doctorsData.getProfessLevel());
//        tvHospital.setText(doctorsData.getFirstClinicName() + "");
//        tvSummary.setText(doctorsData.getRemarks() + "");
//        List<DoctorInfoData.DataData.QuestionsData> questionsData = data.getData().getQuestions();
//        questionsDataList.clear();
//        questionsDataList.addAll(questionsData);
//        doctorAnswerFragment.setData(questionsDataList);
//
//        //查看医生是否提供家医签约服务
//        String familyDoctFlag = data.getData().getFamilyDoctFlag();
//        if (TextUtils.equals("0", familyDoctFlag)) {
//            rlSign.setVisibility(View.GONE);
//        }
//        if (TextUtils.equals("1", familyDoctFlag)) {
//            rlSign.setVisibility(View.VISIBLE);
//        }
//
//        tvRecommend.setText("推荐" + doctorsData.getRecommendCount());
//        recommendNum = doctorsData.getIsRecom();
//        if (TextUtils.equals("0", doctorsData.getIsRecom())) {
//            imgRecommend.setImageResource(R.drawable.icon_recommend_btn);
//        }
//        if (TextUtils.equals("1", doctorsData.getIsRecom())) {
//            cbNoRecommend.setChecked(true);
//            imgRecommend.setImageResource(R.drawable.icon_upper_no_recommend_select);
//        }
//        if (TextUtils.equals("5", doctorsData.getIsRecom())) {
//            cbRecommend.setChecked(true);
//            imgRecommend.setImageResource(R.drawable.icon_upper_recommend_select);
//        }
//        attention = doctorsData.getIsAttention();
//        if (TextUtils.equals("0", doctorsData.getIsAttention())) {
//            imgFollow.setImageResource(R.drawable.icon_follow_grey);
//        }
//        if (TextUtils.equals("1", doctorsData.getIsAttention())) {
//            imgFollow.setImageResource(R.drawable.icon_follow_red);
//        }
//        tvCommentNum.setText("评论" + doctorsData.getEvaluateCount());
    }

    /**
     * 关注和取消关注诊所
     */
    private void attentionDoctor(String attentFlag) {
        showDialog("提交...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.attentionDoctor(token, "2", clinicId, attentFlag),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            getCliniInfo();
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }

    /**
     * 推荐和不推荐诊所
     */
    private void evaluateDoctor(String starCount) {
        showDialog("提交...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.evaluateClinic(token, clinicId, starCount),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            llBottomSheet.setVisibility(View.VISIBLE);
                            llRecommendSheet.setVisibility(View.GONE);
                            getCliniInfo();
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }

    private void initBottomSheet() {
        llBottomSheet.setVisibility(View.VISIBLE);
        llRecommendSheet.setVisibility(View.GONE);
        cbRecommend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    evaluateDoctor("5");
                    cbRecommend.setClickable(false);
                    cbNoRecommend.setChecked(false);
                } else {
                    cbRecommend.setClickable(true);
                }
            }
        });
        cbNoRecommend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    evaluateDoctor("1");
                    cbNoRecommend.setClickable(false);
                    cbRecommend.setChecked(false);
                } else {
                    cbNoRecommend.setClickable(true);
                }
            }
        });
    }
}
