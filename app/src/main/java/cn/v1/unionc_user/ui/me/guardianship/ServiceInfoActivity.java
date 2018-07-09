package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.MeguardianshipListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ServiceInfoActivity extends BaseActivity {
    String elderlyIds, clinicId, serviceCode;
    int type;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_go)
    void tv_go() {
        switch (type){
            case 2:
                applyservice();
                break;
            case 1:
Intent intent =new Intent(ServiceInfoActivity.this,GuardianshipListActivityServerType.class);
intent.putExtra("ServiceCode",serviceCode);
intent.putExtra("clinicId",clinicId);
startActivity(intent);
                break;

        }
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        tvTitle.setText("健康监护");


    }

    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("elderlyId")){
            elderlyIds=intent.getStringExtra("elderlyId");
            Log.d("linshi","elderlyIds:"+elderlyIds);
        }
        if(intent.hasExtra("clinicId")){
            clinicId=intent.getStringExtra("clinicId");
            Log.d("linshi","clinicId:"+clinicId);
        }
        if(intent.hasExtra("ServiceCode")){
            serviceCode=intent.getStringExtra("ServiceCode");
            Log.d("linshi","serviceCode:"+serviceCode);
        }
        if(intent.hasExtra("type")){
            type=intent.getIntExtra("type",0);
            Log.d("linshi","type:"+type);
        }
    }


    private void applyservice() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.applyservice(token, elderlyIds, clinicId, serviceCode), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {

                if (TextUtils.equals("4000", data.getCode())) {
                    goNewActivity(ServiceOkActivity.class);
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
