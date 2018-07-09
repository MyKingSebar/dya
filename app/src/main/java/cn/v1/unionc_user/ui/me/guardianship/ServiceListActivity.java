package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.BindSuccessReturnEventData;
import cn.v1.unionc_user.model.ClinicServerListData;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.MeguardianshipListAdapter;
import cn.v1.unionc_user.ui.adapter.MeguardianshipServiceListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.CaptureActivity;

public class ServiceListActivity extends BaseActivity {
    String clinicId;
    String elderlyId;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.recycleview)
    RecyclerView recycleview;


    private MeguardianshipServiceListAdapter meguardianshipServiceListAdapter;
    private List<ClinicServerListData.DataData.DataDataData> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship_servicelist);
        BusProvider.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initView();
        getList();
    }
    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("elderlyId")){
            elderlyId=intent.getStringExtra("elderlyId");
            Log.d("linshi","elderlyId:"+elderlyId);
        }
        if(intent.hasExtra("clinicId")){
            clinicId=intent.getStringExtra("clinicId");
            Log.d("linshi","clinicId:"+clinicId);
        }
    }
    private void initView() {
        tvTitle.setText("服务列表");
        recycleview.setLayoutManager(new LinearLayoutManager(context));
        meguardianshipServiceListAdapter = new MeguardianshipServiceListAdapter(context);
        meguardianshipServiceListAdapter.setClinicId(clinicId);
        meguardianshipServiceListAdapter.setElderlyId(elderlyId);
        meguardianshipServiceListAdapter.setType(Common.SERVER_OLDMEN);
        recycleview.setAdapter(meguardianshipServiceListAdapter);

    }



    private void getList() {
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getclinicserverlist(token, clinicId,elderlyId), new BaseObserver<ClinicServerListData>(context) {
            @Override
            public void onResponse(ClinicServerListData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (null == data.getData().getService()) {
                        return;
                    }
                    if (data.getData().getService().size() != 0) {
                        datas = data.getData().getService();
                    }
                    meguardianshipServiceListAdapter.setData(datas);
                    closeDialog();
                    Logger.json(new Gson().toJson(datas));
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
