package cn.v1.unionc_user.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.ClinicInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.adapter.HospitalDoctorAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.welcome.DepthPageTransformer;
import cn.v1.unionc_user.ui.welcome.ViewPagerAdatper;
import cn.v1.unionc_user.view.ScrollListView;

public class HospitalDetailActivity extends BaseActivity {
    @BindView(R.id.in_viewpager)
    ViewPager mIn_vp;
    @BindView(R.id.in_ll)
    LinearLayout mIn_ll;
    @BindView(R.id.iv_light_dots)
    ImageView mLight_dots;
    private List<View> mViewList;
    private int mDistance;
    int position2;

private int clinicType=R.drawable.icon_hospital_zh;

    @BindView(R.id.tv_summary)
    TextView tvSummary;


    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_share)
    ImageView imgShare;
//    @BindView(R.id.img_hospital)
//    ImageView imgHospital;
    @BindView(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @BindView(R.id.labels)
    LabelsView labels;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.img_dial)
    ImageView imgDial;
    @BindView(R.id.listView)
    ScrollListView listView;

    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.ll_follow)
    LinearLayout llFollow;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_bottom_sheet)
    LinearLayout llBottomSheet;
    @BindView(R.id.ll_recommend_sheet)
    LinearLayout llRecommendSheet;
    @BindView(R.id.img_follow)
    ImageView imgFollow;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.img_recommend)
    ImageView imgRecommend;
    @BindView(R.id.cb_recommend)
    CheckBox cbRecommend;
    @BindView(R.id.cb_no_recommend)
    CheckBox cbNoRecommend;
//    @BindView(R.id.tv_kefu)
//    FloatingActionButton tv_kefu;
    @BindView(R.id.kefu2)
    ImageView kefu2;

    private String recommendNum;
    private String attention;

    private int mLines;

    private final int MESSAGE = 1000;

    private String clinicId;

    //诊所参数
    private String IsHaveContributing;
    private String AUniress;
    private String EvaCount;
    private List<String> ImagePaths;
    private String Tel;
    private String Latitude;
    private String CollectionCount;
    private String RecommendCount;
    private String Longitude;
    private String Distance;
    private String Notes;
    private String Name;

    //0否 1是
    private String IsDuty;
    private String Identifier;

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

    @OnClick({R.id.img_back, R.id.img_share, R.id.img_dial,R.id.ll_recommend,R.id.ll_follow,R.id.ll_comment,

//            R.id.tv_kefu,
            R.id.kefu2

    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.img_dial:
                photoDialog();
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

//            case  R.id.tv_kefu:
//                if(TextUtils.equals(IsDuty,"1")){
//
//                    if (isLogin()) {
//                        isCertification(MESSAGE);
//                    } else {
//                        goNewActivity(LoginActivity.class);
//                    }
//                }
//                break;
            case  R.id.kefu2:
                if(TextUtils.equals(IsDuty,"1")){

                    if (isLogin()) {
                        isCertification(MESSAGE);
                    } else {
                        goNewActivity(LoginActivity.class);
                        Log.d("linshi","LoginActivity:Hospital");
                    }
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

        mIn_vp.setPageTransformer(true,new DepthPageTransformer());

        listView.setFocusable(false);
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
        ImagePaths =clinicData.getImagePaths();
        Tel=clinicData.getTel();
        Latitude=clinicData.getLatitude();
        CollectionCount=clinicData.getCollectionCount();
        RecommendCount=clinicData.getRecommendCount();
        Longitude=clinicData.getLongitude();
        Distance=clinicData.getDistance();
        Notes=clinicData.getNotes();
        Name=clinicData.getName();
        Tips=clinicData.getTips();
        IsDuty=clinicData.getIsDuty();
        Identifier=clinicData.getIdentifier();
        /**
         *
         1		综合医院
         2		专科医院
         3		诊所
         4		动物医疗场所
         5		卫生院
         */
        switch (Integer.parseInt(clinicData.getParCategory())){
            case 1:
                clinicType=R.drawable.icon_hospital_zh;
                break;
            case 2:
                clinicType=R.drawable.icon_hospital_zk;
                break;
            case 3:
                clinicType=R.drawable.icon_hospital_zs;
                break;
            case 4:
                clinicType=R.drawable.icon_hospital_cw;
                break;
            case 5:
                clinicType=R.drawable.icon_hospital_sq;
                break;
        }

            mViewList = new ArrayList<View>();
            LayoutInflater lf = getLayoutInflater().from(HospitalDetailActivity.this);
            if(ImagePaths ==null||!(ImagePaths.size()>0)){
                View view1 = lf.inflate(R.layout.we_indicator1, null);
                mViewList.add(view1);
                ImageView im=view1.findViewById(R.id.im1);
                Glide.with(context)
                        .load(clinicType)
                       .into(im);
            }else{
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 40, 0);

                for(int i = 0; i< ImagePaths.size(); i++){
                    View view1 = lf.inflate(R.layout.we_indicator1, null);
                    mViewList.add(view1);
                    ImageView im=view1.findViewById(R.id.im1);
                    Glide.with(context)
                            .load(ImagePaths.get(i))
                            .placeholder(clinicType).dontAnimate()
                            .error(clinicType)
                            .into(im);
if(ImagePaths.size()>1){

    ImageView mOne_dot = new ImageView(this);
    mOne_dot.setImageResource(R.drawable.gray_dot);
    mIn_ll.addView(mOne_dot, layoutParams);
}
                }
                mLight_dots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //获得两个圆点之间的距离
                        mDistance = mIn_ll.getChildAt(1).getLeft() - mIn_ll.getChildAt(0).getLeft();
                        mLight_dots.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
                mIn_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                        float leftMargin = mDistance * (position + positionOffset);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                        params.leftMargin = (int) leftMargin;
                        mLight_dots.setLayoutParams(params);
                    }


                    @Override
                    public void onPageSelected(int position) {
                        position2=position;
                        //页面跳转时，设置小圆点的margin
                        float leftMargin = mDistance * position;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                        params.leftMargin = (int) leftMargin;
                        mLight_dots.setLayoutParams(params);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
        mIn_vp.setAdapter(new ViewPagerAdatper(mViewList));




        tvHospitalName.setText(Name);
        tvSummary.setText("简介:"+Notes);
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
            llRecommend.setClickable(false);

        }
        if (TextUtils.equals("5", clinicData.getIsRecom())) {
            cbRecommend.setChecked(true);
            imgRecommend.setImageResource(R.drawable.icon_upper_recommend_select);
            llRecommend.setClickable(false);
        }
        attention = clinicData.getIsAttention();
        if (TextUtils.equals("0", clinicData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_grey);
        }
        if (TextUtils.equals("1", clinicData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_red);
        }
        tvCommentNum.setText("评论" + clinicData.getEvaCount());

//        if(TextUtils.equals(IsDuty,"1")){
//            tv_kefu.setVisibility(View.VISIBLE);
//        }else{
//            tv_kefu.setVisibility(View.GONE);
//        }
        if(TextUtils.equals(IsDuty,"1")){
            kefu2.setVisibility(View.VISIBLE);
        }else{
            kefu2.setVisibility(View.GONE);
        }
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

    /**
     * 查询是否实名认证
     */
    private void isCertification(final int type) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.isCertification(token),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (MESSAGE == type) {
                                DoctorInfo doctorInfo = new DoctorInfo();
                                doctorInfo.setDoctorName(Name + "");
                                doctorInfo.setIdentifier(Identifier + "");
                                doctorInfo.setImagePath(ImagePaths + "");
                                TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
                            }


                        } else if (TextUtils.equals("4021", data.getCode())) {
                            gotoAuthDialog();
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }
    private void photoDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        TextView tvCall=contentView.findViewById(R.id.call);
        TextView tvCancel=contentView.findViewById(R.id.cancel);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + Tel);
                intent.setData(data);
                startActivity(intent);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
}
