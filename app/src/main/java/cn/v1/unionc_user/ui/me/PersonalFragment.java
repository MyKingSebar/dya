package cn.v1.unionc_user.ui.me;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.otto.Subscribe;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.UpdatePersonalEventData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.MainActivity;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.health.DossierHertRateHistoryActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {
    private Tencent mTencent;
    private Unbinder unbinder;

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.img_person_avator)
    CircleImageView imgAvator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.img_user_state)
    ImageView imgUserState;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_doctor_num)
    TextView tvDoctorNum;
    @BindView(R.id.tv_hospital_num)
    TextView tvHospitalNum;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    @BindView(R.id.ll_doctor_num)
    LinearLayout ll_doctor_num;
    @BindView(R.id.ll_hospital_num)
    LinearLayout ll_hospital_num;
    @BindView(R.id.ll_comment_num)
    LinearLayout ll_comment_num;

    private String servicePhone = "";
    private String workingHours = "";
    private UserInfoData.DataData userInfo;

    private Intent intent;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        mTencent = Tencent.createInstance("1106239874", context.getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserInfo();
    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.tv_edit, R.id.tv_yaoqing, R.id.tv_my_activity, R.id.tv_kefu,R.id.tv_my_heart,
//            R.id.tv_doctor_num,R.id.tv_hospital_num,R.id.tv_comment_num,
            R.id.ll_doctor_num,R.id.ll_hospital_num,R.id.ll_comment_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.tv_right:
                //设置
                goNewActivity(SettingActivity.class);
                break;
            case R.id.tv_edit:
                //编辑
                intent = new Intent(context, EditUserInfoActivity.class);
                intent.putExtra("userInfo", (Serializable) userInfo);
                startActivity(intent);
                break;

            case R.id.ll_doctor_num:
                //医生
                intent = new Intent(context, MyWatchinghActivity.class);
                intent.putExtra("type", Common.DOCTORTYPE);
                startActivity(intent);
                break;

            case R.id.ll_hospital_num:
                //医院
                intent = new Intent(context, MyWatchinghActivity.class);
                intent.putExtra("type", Common.HOSPITALTYPE);
                startActivity(intent);
                break;

            case R.id.ll_comment_num:
                //评论

                break;


            case R.id.tv_yaoqing:
                //邀请好友
//                showButtonDialog();


//                ShareListener myListener = new ShareListener();
//
//                final Bundle params = new Bundle();
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://www.baidu.com/img/bd_logo1.png");
//                mTencent.shareToQQ((Activity) context, params, myListener);
                break;
            case R.id.tv_my_activity:
                //我的活动
                goNewActivity(MyactivityActivity.class);
                break;
            case R.id.tv_my_heart:
                //心率
//                goNewActivity(HeartHistoryListActivity.class);
                goNewActivity(DossierHertRateHistoryActivity.class);
                break;
            case R.id.tv_kefu:
                //客服中心
                goNewActivity(ServerCenterActivity.class);
//                PromptDialog contactDialog = new PromptDialog(context);
//                contactDialog.show();
//                contactDialog.setTitle(servicePhone + "");
//                contactDialog.setMessage(workingHours + "");
//                contactDialog.setTvConfirm("取消");
//                contactDialog.setTvCancel("呼叫");
//                contactDialog.setOnButtonClickListener(new OnButtonClickListener() {
//                    @Override
//                    public void onConfirmClick() {
//                    }
//
//                    @Override
//                    public void onCancelClick() {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        Uri data = Uri.parse("tel:" + servicePhone);
//                        intent.setData(data);
//                        startActivity(intent);
//                    }
//                });
                break;
        }
    }
    private class ShareListener implements IUiListener {

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            showTost("分享取消");
        }

        @Override
        public void onComplete(Object arg0) {
            // TODO Auto-generated method stub
            showTost("分享成功");
        }

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            showTost("分享出错");
        }

    }

    @Subscribe
    public void subscribeUpdate(LoginUpdateEventData data) {
        getUserInfo();
    }

    @Subscribe
    public void subscribeUpdate(UpdatePersonalEventData data) {
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getUserInfo((String) SPUtil.get(context, Common.USER_TOKEN, "")),
                new BaseObserver<UserInfoData>(context) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(UserInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            servicePhone = data.getData().getServicePhone();
                            workingHours = data.getData().getWorkingHours();
                            userInfo = data.getData();
                            SPUtil.put(context, Common.USER_AVATOR, data.getData().getHeadImage());
                            Glide.with(context).load(data.getData().getHeadImage())
                                    .placeholder(imgAvator.getDrawable()).dontAnimate()
                                    .error(R.drawable.icon_default_avator)
                                    .into(imgAvator);
                            tvNumber.setText(data.getData().getUserName() + "");
                            tvDoctorNum.setText(data.getData().getDoctorCount() + "");
                            tvHospitalNum.setText(data.getData().getClinicCount() + "");
                            tvCommentNum.setText(data.getData().getEvaluateCount() + "");
                            if (data.getData().getIsCertification() == 0) {
                                imgUserState.setImageResource(R.drawable.icon_no_passed);
                                tvState.setText("未认证");
                                tvState.setTextColor(R.color.red_rz);
                            } else {
                                imgUserState.setImageResource(R.drawable.icon_passed);
                                tvState.setText("已认证");
                                tvState.setTextColor(R.color.qm_blue);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isLogin()){

            getUserInfo();
        }
    }

    private void showButtonDialog() {
        final Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_content_sharel, null);
        bottomDialog.setContentView(contentView);
        Button tvCancel=contentView.findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        RelativeLayout imWeixin=contentView.findViewById(R.id.im_weixin);
        RelativeLayout imPengyouquan=contentView.findViewById(R.id.im_pengyouquan);
        RelativeLayout imQq=contentView.findViewById(R.id.im_qq);
        RelativeLayout imWeibo=contentView.findViewById(R.id.im_weibo);
        imWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                wechatIntent.setPackage("com.tencent.mm");
                wechatIntent.setType("text/plain");
                wechatIntent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
                startActivity(wechatIntent);
            }
        });
        imPengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        imQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                ShareListener myListener = new ShareListener();
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://www.baidu.com/img/bd_logo1.png");
                mTencent.shareToQQ((Activity) context, params, myListener);
            }
        });
        imWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
