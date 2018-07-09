package cn.v1.unionc_user.ui.me.guardianship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.NearbyClinicData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ServiceOkActivity extends BaseActivity {




    @OnClick(R.id.img_back)
    void back() {
        finish();
    }
    @OnClick(R.id.tv_go)
    void tv_go() {
        goNewActivity(GuardianshipListActivity2.class);
        finish();
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ok);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        tvTitle.setText("服务申请");



    }





}
