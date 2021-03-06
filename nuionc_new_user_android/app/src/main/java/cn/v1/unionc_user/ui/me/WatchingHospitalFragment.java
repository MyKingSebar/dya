package cn.v1.unionc_user.ui.me;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.MeWatchingHospitalListData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.tim_model.MessageFactory;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TimeUtil;
import cn.v1.unionc_user.ui.adapter.HomeListAdapter;
import cn.v1.unionc_user.ui.adapter.MeWatchingHospitalListAdapter;
import cn.v1.unionc_user.ui.adapter.MeWatchingHospitalListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.CaptureActivity;
import cn.v1.unionc_user.ui.home.LocationUpdateActivity;
import cn.v1.unionc_user.ui.home.SearchWebViewActivity;
import cn.v1.unionc_user.utils.Location;
import cn.v1.unionc_user.view.BannerView;
import cn.v1.unionc_user.view.LocationDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchingHospitalFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView mainRecycleview;

    private MeWatchingHospitalListAdapter meWatchingHospitalListAdapter;
    private List<MeWatchingHospitalListData.DataData.HospitalData> datas = new ArrayList<>();

    public WatchingHospitalFragment() {
        // Required empty public constructor
        Log.d("linshi","WatchingHospitalFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
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
        View view = inflater.inflate(R.layout.layout_recycleview, container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getHospitalList();
    }


    private void initView() {
        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        meWatchingHospitalListAdapter = new MeWatchingHospitalListAdapter(context);
        mainRecycleview.setAdapter(meWatchingHospitalListAdapter);

    }

    private void getHospitalList() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        String la=(String)SPUtil.get(context,Common.LATITUDE,"");
        String lo=(String)SPUtil.get(context,Common.LONGITUDE,"");

        ConnectHttp.connect(UnionAPIPackage.getMeWatchingHospitalList(token,lo,la), new BaseObserver<MeWatchingHospitalListData>(context) {
            @Override
            public void onResponse(MeWatchingHospitalListData data) {
                Log.d("linshi","datas:"+new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getClinics().size() != 0) {
                        datas = data.getData().getClinics();
                    }
                    meWatchingHospitalListAdapter.setData(datas);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
