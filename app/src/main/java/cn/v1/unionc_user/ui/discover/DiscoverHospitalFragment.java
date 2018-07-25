package cn.v1.unionc_user.ui.discover;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.model.NearbyClinicData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.DiscoverHospitalListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverHospitalFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.recycleview)
    RecyclerView mainRecycleview;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;



    @OnClick(R.id.tv_add)
    void changeaddress(){

    }

    private DiscoverHospitalListAdapter meWatchingHospitalListAdapter;
    private List<NearbyClinicData.DataData.DataDataData> datas = new ArrayList<>();

    public DiscoverHospitalFragment() {
        // Required empty public constructor
        Log.d("linshi","WatchingHospitalFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        getHospitalList();
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
        View view = inflater.inflate(R.layout.fragment_disscover_cliniclist, container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }


    private void initView() {
        // listen refresh event
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                getHospitalList();
            }
        });


        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        meWatchingHospitalListAdapter = new DiscoverHospitalListAdapter(context);
        mainRecycleview.setAdapter(meWatchingHospitalListAdapter);

    }

    private void getHospitalList() {
//        showDialog("加载中...");

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.nearbyclinic(token,"",(String)SPUtil.get(context,Common.LONGITUDE,""), (String)SPUtil.get(context,Common.LATITUDE,""),"1","50"), new BaseObserver<NearbyClinicData>(context) {
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
                    meWatchingHospitalListAdapter.setData(datas);
                    closeDialog();
                    Logger.json(new Gson().toJson(datas));
                    pullRefreshLayout.setRefreshing(false);
                } else {
                    showTost(data.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                pullRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe
    public void subscribeLocationUpdate(LoginData data) {
        Log.d("linshi","DiscoverHospitalFragment,subscribeLocationUpdate");

        getHospitalList();
    }


}
