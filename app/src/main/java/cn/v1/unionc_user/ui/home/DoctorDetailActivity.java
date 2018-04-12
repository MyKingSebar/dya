package cn.v1.unionc_user.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.model.DoctorInfoIdentifierData;
import cn.v1.unionc_user.model.IsDoctorSignData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.me.RealNameAuthActivity;
import cn.v1.unionc_user.utils.DP_PX;
import cn.v1.unionc_user.view.CircleImageView;
import cn.v1.unionc_user.view.ObserverScrollView;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.PromptOnebtnDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

public class DoctorDetailActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp_doctor)
    ViewPager vpDoctor;
    @BindView(R.id.scrollView)
    ObserverScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.img_doctor_avator)
    CircleImageView imgDoctorAvator;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_major)
    TextView tvMajor;
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
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.img_follow)
    ImageView imgFollow;
    @BindView(R.id.ll_follow)
    LinearLayout llFollow;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_bottom_sheet)
    LinearLayout llBottomSheet;
    @BindView(R.id.cb_recommend)
    CheckBox cbRecommend;
    @BindView(R.id.cb_no_recommend)
    CheckBox cbNoRecommend;
    @BindView(R.id.ll_recommend_sheet)
    LinearLayout llRecommendSheet;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.img_recommend)
    ImageView imgRecommend;
    @BindView(R.id.rl_sign)
    RelativeLayout rlSign;

    boolean isDoctorSign;

    private String[] tabItemText = new String[]{"Ta回答的问题", "坐诊时间"};
    private int[] tabItemIcon = new int[]{R.drawable.selector_doctor_tab_1, R.drawable.selector_doctor_tab_2};
    private List<Fragment> doctorDetailFragments = new ArrayList<>();
    private List<DoctorInfoData.DataData.QuestionsData> questionsDataList = new ArrayList<>();
    private String doctorId = "";
    private int mLines;
    private DoctorAnswerFragment doctorAnswerFragment;
    private DoctorOnlineFragment doctorOnlineFragment;
    private String recommendNum;
    private String attention;
    private String identifier = "";
    private String doctorPhone;
    private final int MESSAGE = 1000;
    private final int PHONE = 2000;
    private String doctorAvator;
    private String doctorName;
    private DoctorDetailPageAdapter doctorDetailPageAdapter;
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initBottomSheet();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(doctorId)) {
            getDoctorInfo();
        }
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.ll_message, R.id.ll_phone, R.id.rl_sign,
            R.id.ll_recommend, R.id.ll_follow, R.id.ll_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.ll_message:
                if (isLogin()) {
                    isCertification(MESSAGE);
                } else {
                    goNewActivity(LoginActivity.class);
                    Log.d("linshi","LoginActivity:Doctorll_message");
                }
                break;
            case R.id.ll_phone:
                if (isLogin()) {
                    isCertification(PHONE);
                } else {
                    goNewActivity(LoginActivity.class);
                    Log.d("linshi","LoginActivity:Doctorll_phone");
                }
                break;
            case R.id.rl_sign:
                Intent signIntent = new Intent(context, SignDoctorAgreeMentWebViewActivity.class);
                signIntent.putExtra("doctorId", doctorId);
                startActivity(signIntent);
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
                    Intent intent = new Intent(context, EvaluateActivity.class);
                    intent.putExtra("doctorId", doctorId);
                    startActivity(intent);
                }
                break;
        }
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

    private void initView() {
        //影藏签约
        rlSign.setVisibility(View.GONE);
        //处理tablayout中间的分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(context, R.drawable.divider_line));
        linearLayout.setDividerPadding(DP_PX.dip2px(context, 15));
        //tablayout添加自定义布局
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //依次获取标签
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.layout_tabitem_doctor);
            ImageView icon = tab.getCustomView().findViewById(R.id.img_icon);
            icon.setImageResource(tabItemIcon[i]);
            TextView title = tab.getCustomView().findViewById(R.id.tv_title);
            title.setText(tabItemText[i]);
        }
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        //viewpager初始化
        doctorDetailPageAdapter = new DoctorDetailPageAdapter(getSupportFragmentManager());
        vpDoctor.setAdapter(doctorDetailPageAdapter);

        //滑动监听
        scrollView.setOnScrollChangedListener(new ObserverScrollView.OnScrollChangedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onScrollChanged(ObserverScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (DP_PX.dip2px(context, 44) < t) {
                    rlToolbar.setBackgroundResource(R.color.qm_blue);
                } else {
                    rlToolbar.setBackgroundResource(Color.TRANSPARENT);
                }
            }
        });
        //展开
        tvSummary.post(new Runnable() {
            @Override
            public void run() {
                mLines = tvSummary.getLineCount();
                if (mLines > 1) {
                    tvSummary.setMaxLines(2);
                    tvSummary.setEllipsize(TextUtils.TruncateAt.END);
                    tvOpen.setVisibility(View.VISIBLE);
                } else {
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

    }


    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
            initfragmentData();
            Logger.d(doctorId);
        }
        if (getIntent().hasExtra("source")) {
            source = getIntent().getStringExtra("source");

        }
        if (getIntent().hasExtra("doctorIdentifier")) {
            String doctorIdentifier = getIntent().getStringExtra("doctorIdentifier");
            String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
            showDialog("加载中...");
            Log.d("linshi","doctorIdentifier:"+doctorIdentifier);
            ConnectHttp.connect(UnionAPIPackage.doctorInfoByParam(token, doctorIdentifier),
                    new BaseObserver<DoctorInfoIdentifierData>(context) {
                        @Override
                        public void onResponse(DoctorInfoIdentifierData data) {
                            closeDialog();
                            if (TextUtils.equals("4000", data.getCode())) {
                                doctorId = data.getData().getDoctInfo().getDoctId();
                                initfragmentData();
                                getDoctorInfo();
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
    }

    private void initfragmentData() {
        doctorAnswerFragment = DoctorAnswerFragment.newInstance(doctorId);
        doctorOnlineFragment = DoctorOnlineFragment.newInstance(doctorId);
        doctorDetailFragments.add(doctorAnswerFragment);
        doctorDetailFragments.add(doctorOnlineFragment);
        doctorDetailPageAdapter.notifyDataSetChanged();
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
                                doctorInfo.setDoctorName(doctorName + "");
                                doctorInfo.setIdentifier(identifier + "");
                                doctorInfo.setImagePath(doctorAvator + "");
                                TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
                            }
                            if (PHONE == type) {
                                isDoctorSign();
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


    /**
     * 电话提示框
     */
    private void photoDialog() {
        PromptDialog signDoctor = new PromptDialog(context);
        signDoctor.show();
        signDoctor.setTitle("电话咨询");
        signDoctor.setMessage("是否要进行电话咨询");
        signDoctor.setTvCancel("确定");
        signDoctor.setTvConfirm("取消");
        signDoctor.setOnButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onCancelClick() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri uriData = Uri.parse("tel:" + doctorPhone);
                intent.setData(uriData);
                startActivity(intent);
            }
        });
    }

    /**
     * 推荐和不推荐医生
     */
    private void evaluateDoctor(String starCount) {
        showDialog("提交...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.evaluateDoctor(token, doctorId, starCount),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            llBottomSheet.setVisibility(View.VISIBLE);
                            llRecommendSheet.setVisibility(View.GONE);
                            getDoctorInfo();
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
     * 关注和取消关注医生
     */
    private void attentionDoctor(String attentFlag) {
        showDialog("提交...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.attentionDoctor(token, "1", doctorId, attentFlag),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            getDoctorInfo();
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
     * 获取医生信息
     */
    private void getDoctorInfo() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getDoctorInfo(token, doctorId,
                (String) SPUtil.get(context, Common.LONGITUDE, ""),
                (String) SPUtil.get(context, Common.LATITUDE, ""),
                source
                ),
                new BaseObserver<DoctorInfoData>(context) {
                    @Override
                    public void onResponse(DoctorInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            bindData(data);
                            if(TextUtils.equals(source,"1")){
                                //关注
                                if (!isLogin()) {
                                    showTost("登录后关注");
                                } else {
                                    if (TextUtils.equals("0", attention)) {
                                        attentionDoctor("0");
                                        source="0";
                                    }

                                }
                            }
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

    private void bindData(DoctorInfoData data) {
        DoctorInfoData.DataData.DoctorsData doctorsData = data.getData().getDoctors();
        doctorAvator = doctorsData.getImagePath();
        doctorName = doctorsData.getDoctorName();
        identifier = doctorsData.getIdentifier();
        doctorPhone = doctorsData.getDoctTelphone();
        Log.d("linshi","bindData.doctorsData.getImagePath():"+doctorsData.getImagePath());
        if(TextUtils.isEmpty(doctorsData.getImagePath())){

            imgDoctorAvator.setImageResource(R.drawable.icon_doctor_default);
        }else{
            Glide.with(context)
                    .load(doctorsData.getImagePath())
                    .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                    .error(R.drawable.icon_doctor_default)
                    .into(imgDoctorAvator);

        }

        tvDoctorName.setText(doctorsData.getDoctorName() + "");
        tvDepartment.setText(doctorsData.getDepartName() + "  " + doctorsData.getProfessLevel());
        tvHospital.setText(doctorsData.getFirstClinicName() + "");
        tvMajor.setText("擅长："+doctorsData.getMajor() + "");
        tvSummary.setText("简介: "+doctorsData.getRemarks() + "");

        List<DoctorInfoData.DataData.QuestionsData> questionsData = data.getData().getQuestions();
        questionsDataList.clear();
        questionsDataList.addAll(questionsData);
        doctorAnswerFragment.setData(questionsDataList);

        //查看医生是否提供家医签约服务
        String familyDoctFlag = data.getData().getFamilyDoctFlag();
        if (TextUtils.equals("0", familyDoctFlag)) {
            rlSign.setVisibility(View.GONE);
        }
        if (TextUtils.equals("1", familyDoctFlag)) {
            rlSign.setVisibility(View.VISIBLE);
        }

        tvRecommend.setText("推荐" + doctorsData.getRecommendCount());
        recommendNum = doctorsData.getIsRecom();
        if (TextUtils.equals("0", doctorsData.getIsRecom())) {
            imgRecommend.setImageResource(R.drawable.icon_recommend_btn);
//            llComment.setClickable(false);
        }
        if (TextUtils.equals("1", doctorsData.getIsRecom())) {
            cbNoRecommend.setChecked(true);
            imgRecommend.setImageResource(R.drawable.icon_upper_no_recommend_select);
//            llComment.setClickable(false);
        }
        if (TextUtils.equals("5", doctorsData.getIsRecom())) {
            cbRecommend.setChecked(true);
            imgRecommend.setImageResource(R.drawable.icon_upper_recommend_select);
//            llComment.setClickable(false);
        }
        attention = doctorsData.getIsAttention();
        if (TextUtils.equals("0", doctorsData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_grey);
        }
        if (TextUtils.equals("1", doctorsData.getIsAttention())) {
            imgFollow.setImageResource(R.drawable.icon_follow_red);
        }
        tvCommentNum.setText("评论" + doctorsData.getEvaluateCount());
    }


    /**
     * tablayout选择监听
     */
    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vpDoctor.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };


    class DoctorDetailPageAdapter extends FragmentPagerAdapter {

        public DoctorDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return doctorDetailFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return doctorDetailFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItemText[position];
        }
    }



    /**
     * 查询医生是否签约
     */
    private void isDoctorSign() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.isDoctorSign(token, doctorId),
                new BaseObserver<IsDoctorSignData>(context) {
                    @Override
                    public void onResponse(IsDoctorSignData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            String signState = data.getData().getIsSigned();
                            if (TextUtils.equals("1", signState)) {
                                //已签约
                                photoDialog();
                            } else if (TextUtils.equals("-1", signState)) {
                                //审核
                                isDoctorSign=false;
                                showPromptDialog("您的签约正在审核中，审核后即可开通此功能！");
                            } else if (TextUtils.equals("0", signState)) {
                                //可以签约
                                isDoctorSign=false;
                                showPromptDialog("与医生签约后即可开通此功能！");
                            }
                        } else if (TextUtils.equals("4021", data.getCode())) {
                            gotoAuthDialog();
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


    private void showPromptDialog(String message){
        PromptOnebtnDialog promptOnebtnDialog = new PromptOnebtnDialog(context){
            @Override
            public void onClosed() {

            }
        };
        promptOnebtnDialog.show();
        promptOnebtnDialog.setTitle("小巴提示：");
        promptOnebtnDialog.setMessage(message);
    }


}
