package cn.v1.unionc_user.ui.me.guardianship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
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
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MeguardianshipData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.MeWatchingDoctorListAdapter;
import cn.v1.unionc_user.ui.adapter.MeguardianshipListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.CaptureActivity;

public class GuardianshipListActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.ll_saomiao)
    LinearLayout ll_saomiao;
    @OnClick(R.id.ll_saomiao)
    void saomiao(){
        goNewActivity(CaptureActivity.class);
    }

    private MeguardianshipListAdapter meguardianshipListAdapter;
    private List<MeguardianshipData.DataData.GuardianshipData> datas=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship_guardianshiplist);
        BusProvider.getInstance().register(this);
        ButterKnife.bind(this);
        initView();
        getList();
    }

    private void initView() {
        tvTitle.setText("亲情监护");
        recycleview.setLayoutManager(new LinearLayoutManager(context));
        meguardianshipListAdapter = new MeguardianshipListAdapter(context);
        recycleview.setAdapter(meguardianshipListAdapter);
        meguardianshipListAdapter.setOnClickMyTextView(new MeguardianshipListAdapter.onMyClick() {
            @Override
            public void myTextViewClick(int id) {
                final int iid=id;
                showDialog("加载中...");
                String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
                ConnectHttp.connect(UnionAPIPackage.UnbindGuardianship(token,datas.get(id).getElderlyUserId()), new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            showTost("解绑成功");
                            datas.remove(iid);
                            meguardianshipListAdapter.notifyDataSetChanged();

                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
            }
        });
    }
    private void getList() {
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.GetGuardianshipListInfo(token), new BaseObserver<MeguardianshipData>(context) {
            @Override
            public void onResponse(MeguardianshipData data) {
                Log.d("linshi","datas:"+new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getGuardian().size() != 0) {
                        datas=data.getData().getGuardian();
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

    @Subscribe
    public void bindsuccessReturn(final BindSuccessReturnEventData data) {
        getList();
    }
}
