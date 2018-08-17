package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.ClinicServerListData;
import cn.v1.unionc_user.model.NearbyClinicData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.MeguardianshipClinicServiceListAdapter;
import cn.v1.unionc_user.ui.adapter.MeguardianshipServiceListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ServiceClinicListActivity extends BaseActivity {
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


    private MeguardianshipClinicServiceListAdapter meguardianshipServiceListAdapter;
    private List<NearbyClinicData.DataData.DataDataData> datas = new ArrayList<>();

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
    }

    private void initView() {
        tvTitle.setText("关联医院");
        recycleview.setLayoutManager(new LinearLayoutManager(context));
        meguardianshipServiceListAdapter = new MeguardianshipClinicServiceListAdapter(context);
        meguardianshipServiceListAdapter.setElderlyId(elderlyId);
        recycleview.setAdapter(meguardianshipServiceListAdapter);

    }



    private void getList() {
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.nearbyclinic(token,elderlyId,(String)SPUtil.get(context,Common.LONGITUDE,""), (String)SPUtil.get(context,Common.LATITUDE,""),"1","50"), new BaseObserver<NearbyClinicData>(context) {
            @Override
            public void onResponse(NearbyClinicData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (null == data.getData().getClinicDatas()) {
                        return;
                    }
                    if (data.getData().getClinicDatas().size() != 0) {
                        datas = data.getData().getClinicDatas();
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
