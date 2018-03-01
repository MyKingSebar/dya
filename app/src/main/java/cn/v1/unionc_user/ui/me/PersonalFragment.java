package cn.v1.unionc_user.ui.me;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.view.CircleImageView;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.img_avator)
    CircleImageView imgAvator;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_edit)
    TextView tvEdit;
    @Bind(R.id.img_user_state)
    ImageView imgUserState;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_doctor_num)
    TextView tvDoctorNum;
    @Bind(R.id.tv_hospital_num)
    TextView tvHospitalNum;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @Bind(R.id.tv_about_us)
    TextView tvAboutUs;
    @Bind(R.id.tv_kefu)
    TextView tvKefu;
    @Bind(R.id.tv_logout)
    TextView tvLogout;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserInfo();
    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.tv_edit, R.id.tv_yaoqing, R.id.tv_about_us, R.id.tv_kefu, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.tv_right:
                break;
            case R.id.tv_edit:
                break;
            case R.id.tv_yaoqing:
                break;
            case R.id.tv_about_us:
                break;
            case R.id.tv_kefu:
                break;
            case R.id.tv_logout:
                PromptDialog logoutDialog = new PromptDialog(context);
                logoutDialog.show();
                logoutDialog .setTitle("退出登录");
                logoutDialog.setMessage("确认退出登录？");
                logoutDialog.setOnButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onConfirmClick() {
                        logout();
                        goNewActivity(LoginActivity.class);
                        tvLogout.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCancelClick() {
                    }
                });
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getUserInfo((String) SPUtil.get(context, Common.USER_TOKEN,"")),
                new BaseObserver<UserInfoData>(context) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(UserInfoData data) {
                        closeDialog();
                        if(TextUtils.equals("4000",data.getCode())){
                            Glide.with(context).load(data.getData().getHeadImage())
                                    .placeholder(R.drawable.icon_default_avator)
                                    .error(R.drawable.icon_default_avator)
                                    .into(imgAvator);
                            tvNumber.setText(data.getData().getUserName()+"");
                            tvDoctorNum.setText(data.getData().getDoctorCount()+"");
                            tvHospitalNum.setText(data.getData().getClinicCount()+"");
                            tvCommentNum.setText(data.getData().getEvaluateCount()+"");
                            if(data.getData().getIsCertification() == 0){
                                imgUserState.setImageResource(R.drawable.icon_no_passed);
                                tvState.setText("未认证");
                                tvState.setTextColor(R.color.red_rz);
                            }else{
                                imgUserState.setImageResource(R.drawable.icon_passed);
                                tvState.setText("未认证");
                                tvState.setTextColor(R.color.qm_blue);
                            }

                        }else{
                            showTost(data.getMessage());
                        }
                    }
                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}