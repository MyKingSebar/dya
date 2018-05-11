package cn.v1.unionc_user.ui.find;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
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
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.me.EditUserInfoActivity;
import cn.v1.unionc_user.ui.me.HeartHistoryListActivity;
import cn.v1.unionc_user.ui.me.MyWatchinghActivity;
import cn.v1.unionc_user.ui.me.MyactivityActivity;
import cn.v1.unionc_user.ui.me.ServerCenterActivity;
import cn.v1.unionc_user.ui.me.SettingActivity;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.tv_my_healthcircle)
    TextView tvHealthcircle;
    @BindView(R.id.tv_homedoc)
    TextView tvHomedoc;
    @BindView(R.id.tv_zbdoc)
    TextView tvZbdoc;


    private Intent intent;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.tv_my_healthcircle, R.id.tv_homedoc, R.id.tv_zbdoc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_healthcircle:
                //健康圈
                break;
            case R.id.tv_homedoc:
                //家庭医生
                goNewActivity(HomeDocActivity.class);
                break;
            case R.id.tv_zbdoc:
                //值班医生
                if (isLogin()) {
                    goNewActivity(DutyDocActivity.class);
                } else {
                    goNewActivity(LoginActivity.class);
                }

                break;


        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
