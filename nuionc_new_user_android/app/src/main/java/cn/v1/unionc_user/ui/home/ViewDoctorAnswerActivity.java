package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMConversationType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.DoctorAnswerDetailData;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.CircleImageView;

public class ViewDoctorAnswerActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.tv_looks)
    TextView tvLooks;
    @BindView(R.id.img_doctor_avator)
    CircleImageView imgDoctorAvator;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_online_ask)
    TextView tvOnlineAsk;

    private String doctorId = "";
    private String questionId = "";

    private String identifier = "";
    private String doctorPhone;
    private final int MESSAGE = 1000;
    private final int PHONE = 2000;
    private String doctorAvator;
    private String doctorName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_answer);
        ButterKnife.bind(this);
        initData();
    }


    @OnClick({R.id.img_back, R.id.tv_online_ask})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_online_ask:
                if (isLogin()) {
                    isCertification(MESSAGE);
                } else {
                    goNewActivity(LoginActivity.class);
                }
                break;
        }
    }


    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
        if (getIntent().hasExtra("questionId")) {
            questionId = getIntent().getStringExtra("questionId");
        }

        getDoctorInfo();
    }

    /**
     * 医生回答的问题
     */
    private void getAnswer() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getDoctorAnswer(token, questionId),
                new BaseObserver<DoctorAnswerDetailData>(context) {
                    @Override
                    public void onResponse(DoctorAnswerDetailData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            tvQuestion.setText(data.getData().getQuestionInfo().getQuestion() + "");
                            tvAnswer.setText(data.getData().getQuestionInfo().getAnswer() + "");
                            tvLooks.setText(data.getData().getQuestionInfo().getPageView() + "人看过");
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
                ""
                ),
                new BaseObserver<DoctorInfoData>(context) {
                    @Override
                    public void onResponse(DoctorInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            DoctorInfoData.DataData.DoctorsData doctorsData = data.getData().getDoctors();
                            Glide.with(context).load(doctorsData.getImagePath()).into(imgDoctorAvator);
                            tvDoctorName.setText(doctorsData.getDoctorName() + "");
                            tvDepartment.setText(doctorsData.getDepartName() + "  " + doctorsData.getProfessLevel());
                            tvHospital.setText(doctorsData.getFirstClinicName() + "");
                            doctorAvator = doctorsData.getImagePath();
                            doctorName = doctorsData.getDoctorName();
                            identifier = doctorsData.getIdentifier();
                            getAnswer();
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
}
