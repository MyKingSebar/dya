package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.MeguardianshipListAdapterServerType;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.CaptureActivity;
import cn.v1.unionc_user.ui.me.oldregister.OldRegisterActivity;

public class GuardianshipListActivityServerType extends BaseActivity {
String serviceCode,clinicId;


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
    @BindView(R.id.ll_saomiao)
    LinearLayout ll_saomiao;


    @BindView(R.id.ll_no)
    LinearLayout ll_no;
    @BindView(R.id.bt_next)
    Button next;

    @OnClick(R.id.ll_saomiao)
    void saomiao() {
        goNewActivity(CaptureActivity.class);
    }

    @OnClick(R.id.ll_luru)
    void luru() {
        goNewActivity(OldRegisterActivity.class);

    }

    @OnClick(R.id.bt_next)
    void next() {
        applyservice();
    }

    private MeguardianshipListAdapterServerType meguardianshipListAdapter;
    private List<MeguardianshipData.DataData.GuardianshipData> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship_guardianshiplist_servertype);
        BusProvider.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initView();
        getList();
    }

    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("ServiceCode")){
            serviceCode=intent.getStringExtra("ServiceCode");
            Log.d("linshi","serviceCode:"+serviceCode);
        }
        if(intent.hasExtra("clinicId")){
            clinicId=intent.getStringExtra("clinicId");
            Log.d("linshi","clinicId:"+clinicId);
        }
    }

    private void initView() {
        tvTitle.setText("亲情监护");
        recycleview.setLayoutManager(new LinearLayoutManager(context));
        meguardianshipListAdapter = new MeguardianshipListAdapterServerType(context);
        recycleview.setAdapter(meguardianshipListAdapter);



    }



    private void getList() {
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.GetGuardianshipListInfo(token, serviceCode), new BaseObserver<MeguardianshipData>(context) {
            @Override
            public void onResponse(MeguardianshipData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (null == data.getData().getGuardian()) {
                        ll_no.setVisibility(View.VISIBLE);
                        recycleview.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);


                        return;
                    }
                    if (data.getData().getGuardian().size() != 0) {
                        ll_no.setVisibility(View.GONE);
                        recycleview.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);

                        datas = data.getData().getGuardian();
                    }else {
                        ll_no.setVisibility(View.VISIBLE);
                        recycleview.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                    }
                    meguardianshipListAdapter.setData(datas);
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
    private void applyservice() {
        String elders=meguardianshipListAdapter.getelders();
        if(TextUtils.isEmpty(elders)){
            showTost("请选择服务");
            return;
        }
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.applyservice(token,elders , clinicId, serviceCode), new BaseObserver<BaseData>(context) {
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




    @Subscribe
    public void bindsuccessReturn(final BindSuccessReturnEventData data) {
        getList();
    }
}
