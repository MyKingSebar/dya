package cn.v1.unionc_user.ui.home;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.baoyz.widget.PullRefreshLayout;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.jiguang.ExampleUtil;
import cn.v1.unionc_user.jiguang.LocalBroadcastManager;
import cn.v1.unionc_user.model.ActivityListReturnEventData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.JiGuangData;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.model.LogOutEventData;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.Position;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.tim_model.MessageFactory;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TimeUtil;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.adapter.HomeListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.health.DossierHeartRateAutoActivity;
import cn.v1.unionc_user.utils.Location;
import cn.v1.unionc_user.view.BannerView;
import cn.v1.unionc_user.view.LocationDialog;
import cn.v1.unionc_user.view.PromptOnebtnDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements LocationSource,
        AMapLocationListener {
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;
    private String provider;//位置提供器
    private Position position = new Position();
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;


    private Unbinder unbinder;

    @BindView(R.id.banner)
    BannerView banner;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_saoma)
    TextView tvSaoma;
    @BindView(R.id.tv_guahao)
    TextView tvGuahao;
    @BindView(R.id.tv_yihu)
    TextView tvYihu;
    @BindView(R.id.tv_health)
    TextView tvHealth;
    @BindView(R.id.main_recycleview)
    RecyclerView mainRecycleview;
    @BindView(R.id.tv_recommond)
    TextView tvRecommond;
    @BindView(R.id.rl_recommond)
    RelativeLayout rlRecommond;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout pullRefreshLayout;



    private int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private HomeListAdapter homeListAdapter;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();
    private List<HomeListData.DataData.HomeData> newConversations = new ArrayList<>();
    private List<HomeListData.DataData.HomeData> pushdatas = new ArrayList<>();
    private List<HomeListData.DataData.HomeData> pushactivitydatas = new ArrayList<>();
    private HomeListData.DataData.HomeData pushactivitydata;
    private String currentPoiname;
    private String longitude;
    private String latitude;
    private LocationDialog locationDialog;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private int dialogtime = 0;
    private boolean refrash = false;

    Gson gson = new Gson();

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        mlocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mlocationClient.setLocationListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("linshi", "MessageFragment.onViewCreated");
        initView();
        initLocation2();
        registerMessageReceiver();  // used for receive msg
        getActivityPush();
    }

    @OnClick({R.id.tv_address, R.id.ll_search, R.id.tv_saoma, R.id.tv_guahao, R.id.tv_yihu, R.id.tv_health})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                confirmLocationDialog();
                break;
            case R.id.ll_search:
                goNewActivity(MapClinicWebViewActivity.class);
                break;
            case R.id.tv_saoma:
                //扫一扫
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PHONE_PERMISSIONS);
                    } else {
                        goNewActivity(CaptureActivity.class);
                    }
                } else {
                    goNewActivity(CaptureActivity.class);
                }
                break;
            case R.id.tv_guahao:
                //心率检测
                if (isLogin()) {
//            if(TextUtils.isEmpty(healthInfoId)){
//                showDialog();
//            }else{
                    Intent intent = new Intent(getActivity(), DossierHeartRateAutoActivity.class);
                    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
                    intent.putExtra("userId", token);
                    intent.putExtra("monitorId", "1");
                    startActivity(intent);
//            }
                } else {
                    goNewActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_yihu:
                //医护上门
                if (isLogin()) {
                    Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                    intent.putExtra("type", Common.WEB_YIHUSHANGMEN);
                    startActivity(intent);
                } else {
                    goNewActivity(LoginActivity.class);
                }

                break;
            case R.id.tv_health:
                //送药上门
                if (isLogin()) {
                    Intent intent = new Intent(context, ToDoorWebViewActivity.class);
                    intent.putExtra("type", Common.WEB_SONGYAOSHANGMEN);
                    startActivity(intent);
                } else {
                    goNewActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goNewActivity(CaptureActivity.class);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initView() {
        // listen refresh event
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                getHomeList(longitude, latitude);
            }
        });




        rlRecommond.setVisibility(View.GONE);
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        banner.setViewList(viewList);
        banner.startLoop(true);

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));

        homeListAdapter = new HomeListAdapter(context);
        mainRecycleview.setAdapter(homeListAdapter);

        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {

                Iterator<HomeListData.DataData.HomeData> it = datas.iterator();
                while (it.hasNext()) {
                    String type = it.next().getType();
                    if (TextUtils.equals(Common.CONVERSATIONS, type)) {
                        it.remove();
                    }
                }
                getCoversationList();
                datas.addAll(newConversations);
                Logger.d(new Gson().toJson(datas));
                homeListAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Subscribe
    public void subscribeLocationUpdate(LocationUpdateEventData data) {
        currentPoiname = data.getPoiName();
        tvAddress.setText(data.getPoiName());
        latitude = data.getLat() + "";
        longitude = data.getLon() + "";
        SPUtil.put(context, Common.ADDRESS, data.getPoiName());
        SPUtil.put(context, Common.LONGITUDE, longitude);
        SPUtil.put(context, Common.LATITUDE, latitude);
        getHomeList(longitude, latitude);
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (isLogin() && !TextUtils.equals(Common.LOCATYPE_NET, data.getType())) {
            updateAdd(token, data.getPoiName(), latitude, longitude);
            SPUtil.put(context, Common.USER_ADD, data.getPoiName());
            Log.d("linshi", "put:" + data.getPoiName());
            return;
        }

    }

    @Subscribe
    public void subscribeUpdate(LoginUpdateEventData data) {
        refrash = true;
        getHomeList(longitude, latitude);
    }

    @Subscribe
    public void logoutReturn(LogOutEventData data) {
        getHomeList(longitude, latitude);
    }

    @Subscribe
    public void activityListReturn(final ActivityListReturnEventData data) {
        PromptOnebtnDialog promptOnebtnDialog = new PromptOnebtnDialog(context) {
            @Override
            public void onClosed() {
                Log.d("linshi", "ActivityListReturnEventData" + data.getClinicId());
                if (!TextUtils.isEmpty(data.getClinicId())) {
                    Intent intent = new Intent(context, HospitalDetailActivity.class);
                    intent.putExtra("clinicId", data.getClinicId() + "");
                    startActivity(intent);
                } else {
                    Log.d("linshi", "activityListReturn.data.getClinicId()isEmpty");
                }

            }
        };
        promptOnebtnDialog.show();
        promptOnebtnDialog.setTitle("小巴提示：");
        promptOnebtnDialog.setMessage("暂无活动");
    }


    private void initLocation() {
        showDialog("定位中...");
        new Location(context) {
            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                currentPoiname = amapLocation.getPoiName();
//                    if (isLogin()&&!SPUtil.contains(context,Common.USER_ADD)) {
//                        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//                        updateAdd(token, amapLocation.getPoiName(),latitude,longitude);
//                        SPUtil.put(context, Common.USER_ADD, amapLocation.getPoiName());
//                        return;
//                    }else {
//                        tvAddress.setText((String)SPUtil.get(context,Common.USER_ADD,""));
//                    }
                String add = (String) SPUtil.get(context, Common.USER_ADD, "");
                Log.d("linshi", "isLogin():" + isLogin());
                Log.d("linshi", "SPUtil.contains(context, Common.USER_ADD):" + SPUtil.contains(context, Common.USER_ADD));
                Log.d("linshi", "TextUtils.isEmpty(add):" + TextUtils.isEmpty(add));
                if (isLogin() && SPUtil.contains(context, Common.USER_ADD) && !TextUtils.isEmpty(add)) {
                    tvAddress.setText(add);

                } else {
                    tvCity.setText(amapLocation.getCity());
                    tvAddress.setText(amapLocation.getPoiName());
                }
                stopLocation();
                //位置确定弹框
//                confirmLocationDialog();
                //获取经纬度
                longitude = amapLocation.getLongitude() + "";
                latitude = amapLocation.getLatitude() + "";
                SPUtil.put(context, Common.ADDRESS, amapLocation.getPoiName());
                SPUtil.put(context, Common.LONGITUDE, longitude);
                SPUtil.put(context, Common.LATITUDE, latitude);
                closeDialog();
                //请求数据
                getHomeList(longitude, latitude);
            }

            @Override
            protected void locationFailure() {
                tvAddress.setText("请选择");
                stopLocation();
                closeDialog();
            }
        };


    }

    /**
     * 位置确定弹框
     */
    private void confirmLocationDialog() {
        if (null == locationDialog) {
            locationDialog = new LocationDialog(context);
        }
        locationDialog.show();
        locationDialog.setCurrentPOI(currentPoiname);
        locationDialog.setOnButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onCancelClick() {
                goNewActivity(LocationUpdateActivity.class);
            }
        });
    }


    private void getHomeList(String longitude, String latitude) {
        Log.d("linshi", "getHomeList");
        showDialog("加载中...");
        dialogtime = 0;
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getHomeList(token, longitude, latitude), new BaseObserver<HomeListData>(context) {
            @Override
            public void onResponse(HomeListData data) {
                if (TextUtils.equals("4000", data.getCode())) {
//                    if (!isLogin()) {
//                        rlRecommond.setVisibility(View.VISIBLE);
//                    } else {
//                        if (data.getData().getSignedDoctros().size() != 0 ||
//                                data.getData().getAttendingDoctors().size() != 0) {
//                            rlRecommond.setVisibility(View.GONE);
//                        } else {
//                            rlRecommond.setVisibility(View.VISIBLE);
//                        }
//                    }
                    datas.clear();
                    if (data.getData().getRecommendDoctors().size() != 0) {
                        int index = datas.size();
                        int recommendDoctors = data.getData().getRecommendDoctors().size();
                        if (recommendDoctors == 0) {
                            rlRecommond.setVisibility(View.GONE);
                        } else {
                            rlRecommond.setVisibility(View.VISIBLE);
                        }
                        tvRecommond.setText("向您推荐附近" + recommendDoctors + "名家庭医生为您服务");
                        for (int i = 0; i < recommendDoctors; i++) {
                            datas.add(data.getData().getRecommendDoctors().get(i));
                            datas.get(index + i).setType(Common.RECOMMEND_DOCTOR);
                        }
                    } else {
                        rlRecommond.setVisibility(View.GONE);
                    }
                    if (data.getData().getSignedDoctros().size() != 0) {
                        int index = datas.size();
                        int SignedDoctros = data.getData().getSignedDoctros().size();
                        for (int i = 0; i < SignedDoctros; i++) {
                            datas.add(data.getData().getSignedDoctros().get(i));
                            datas.get(index + i).setType(Common.SIGNED_DOCTROS);
                        }
                    }
                    if (data.getData().getAttendingDoctors().size() != 0) {
                        int index = datas.size();
                        int attendingDoctors = data.getData().getAttendingDoctors().size();
                        for (int i = 0; i < attendingDoctors; i++) {
                            datas.add(data.getData().getAttendingDoctors().get(i));
                            datas.get(index + i).setType(Common.ATTENDING_DOCTORS);
                        }
                    }
                    if (isLogin()) {

                        getCoversationList();
                        for (int i = 0; i < newConversations.size(); i++) {
                            String conversationIdentifier = newConversations.get(i).getIdentifier();
                            Iterator<HomeListData.DataData.HomeData> it = datas.iterator();
                            while (it.hasNext()) {
                                String datasIdentifier = it.next().getIdentifier();
                                if (TextUtils.equals(conversationIdentifier, datasIdentifier)) {
                                    it.remove();
                                }
                            }
                        }
                        Log.d("linshi", "Tim3:" + new Gson().toJson(newConversations));
                        datas.addAll(newConversations);
                        getPushActivity();
                        if (pushactivitydatas != null && pushactivitydatas.size() != 0) {
                            datas.addAll((List<HomeListData.DataData.HomeData>) pushactivitydatas);
                        }
                    }

//                    getActivityPush();
//                    datas.addAll(pushdatas);
//                    connectclosedialog();
                    closeDialog();
                    homeListAdapter.setData(datas);
//                    homeListAdapter.setData(datas);
                    Logger.json(new Gson().toJson(datas));
                    // refresh complete
                    pullRefreshLayout.setRefreshing(false);
                } else {
                    showTost(data.getMessage());
                    // refresh complete
                    pullRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
//                connectclosedialog();
                // refresh complete
                pullRefreshLayout.setRefreshing(false);
            }
        });

    }

    /**
     * 获得本地活动列表
     */

    private void getPushActivity() {
//        pushactivitydatas=(List<HomeListData.DataData.HomeData>) SPUtil.get(context,Common.PUSH_ACTIVITY_LOCAL,null);
        pushactivitydatas = SPUtil.getDataList(context, Common.PUSH_ACTIVITY_LOCAL);
        if (pushactivitydatas != null) {
            Log.d("linshi", "getPushActivity:" + pushactivitydatas.toString());

        } else {
            Log.d("linshi", "getPushActivity:null");
        }
    }


    private void connectclosedialog() {
        dialogtime++;
        if (dialogtime > 1) {
            closeDialog();
//            datas.addAll(pushdatas);
//            homeListAdapter.setData(datas);


        }
    }

    private void getCoversationList() {
        //获取会话列表
        if (isLogin()) {
            newConversations.clear();
            List<TIMConversation> conversations = TIMManagerExt.getInstance().getConversationList();
            Logger.e(new Gson().toJson(conversations));
            for (int i = 0; i < conversations.size(); i++) {
                if (TIMConversationType.System != conversations.get(i).getType()) {
                    TIMConversationExt conExt = new TIMConversationExt(conversations.get(i));
                    List<TIMMessage> timMessages = conExt.getLastMsgs(10);
                    Logger.e(new Gson().toJson(timMessages.get(0)));
                    //获取会话未读数
                    long num = conExt.getUnreadMessageNum();
                    HomeListData.DataData.HomeData homeData = new HomeListData.DataData.HomeData();
                    homeData.setIdentifier(conversations.get(i).getPeer());
                    homeData.setLastMessage(MessageFactory.getMessage(timMessages.get(0)));
                    homeData.setLasttime(TimeUtil.getTimeStr(timMessages.get(0).timestamp()) + "");
                    homeData.setType(Common.CONVERSATIONS);
                    if (0 != num) {
                        homeData.setUnReadMessage(num + "");
                    }
                    newConversations.add(homeData);
                }
            }

        }
    }

    private void getActivityPush() {
        Log.d("linshi", "getActivityPush()");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        pushdatas.clear();
        ConnectHttp.connect(UnionAPIPackage.getPushList(token), new BaseObserver<HomeListData>(context) {
            @Override
            public void onResponse(HomeListData data) {

                if (TextUtils.equals("4000", data.getCode())) {
                    Log.d("linshi", "data:" + new Gson().toJson(data));
                    Log.d("linshi", "size:" + data.getData().getActivities().size());

                    if (data.getData().getActivities().size() != 0) {
                        Intent intent = new Intent(context, ActivityPopwindowActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) data.getData().getActivities());//序列化,要注意转化(Serializable)
                        intent.putExtras(bundle);//发送数据
                        startActivity(intent);//启动intent
                    }

                } else {
                    showTost(data.getMessage());
                }
                connectclosedialog();
            }

            @Override
            public void onFail(Throwable e) {
                connectclosedialog();
            }
        });

    }
//    private void getActivityPush() {
//        Log.d("linshi", "getActivityPush()");
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        pushdatas.clear();
//        ConnectHttp.connect(UnionAPIPackage.getPushList(token), new BaseObserver<HomeListData>(context) {
//            @Override
//            public void onResponse(HomeListData data) {
//                Log.d("linshi", "data:" + new Gson().toJson(data));
//                Log.d("linshi", "size:" + data.getData().getActivities().size());
//
//                if (TextUtils.equals("4000", data.getCode())) {
//
//                    if (data.getData().getActivities().size() != 0) {
//                        int index = pushdatas.size();
//                        int activities = data.getData().getActivities().size();
//                        for (int i = 0; i < activities; i++) {
//                            pushdatas.add(data.getData().getActivities().get(i));
//                            pushdatas.get(index + i).setType(Common.ACTIVITY_PUSH);
//                        }
//                    }
//
//                } else {
//                    showTost(data.getMessage());
//                }
//                connectclosedialog();
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                connectclosedialog();
//            }
//        });
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Common.MESSAGE_JGPUSH_ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("linshi", "action():" + intent.getAction());
            if (Common.MESSAGE_JGPUSH_ACTION.equals(intent.getAction())) {
                if (!isLogin()) {
                    return;
                }
                String extras = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);
                StringBuilder showMsg = new StringBuilder();
                if (!ExampleUtil.isEmpty(extras)) {
                    Log.d("linshi", "extras():" + extras);
                    JiGuangData child2 = gson.fromJson(extras, JiGuangData.class);
                    pushactivitydata = new HomeListData.DataData.HomeData();
                    pushactivitydata.setName(child2.getTitle());
                    pushactivitydata.setStartTime(child2.getStartTime());
                    pushactivitydata.setEndTime(child2.getEndTime());
                    pushactivitydata.setAddress(child2.getActionAddr());
                    pushactivitydata.setCreatedTime(child2.getPublishTime());
                    pushactivitydata.setActivityId(child2.getActivityId());
                    pushactivitydata.setType(Common.ACTIVITY_PUSH);

                }
                if (pushactivitydata != null) {
//                    pushactivitydatas=(List<HomeListData.DataData.HomeData>) SPUtil.get(context,Common.PUSH_ACTIVITY_LOCAL,null);
                    pushactivitydatas = SPUtil.getDataList(context, Common.PUSH_ACTIVITY_LOCAL);
                    if (pushactivitydatas != null) {

                        pushactivitydatas.add(pushactivitydata);
//                        SPUtil.put(context,Common.PUSH_ACTIVITY_LOCAL,pushactivitydatas);
                        SPUtil.setDataList(context, Common.PUSH_ACTIVITY_LOCAL, pushactivitydatas);
                    }
//                    pushactivitydatas.clear();
//                    pushactivitydatas.add(pushactivitydata);
//                    datas.addAll(pushactivitydatas);
                    datas.add(pushactivitydata);
                    homeListAdapter.setData(datas);

                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refrash) {
            getHomeList(longitude, latitude);
            refrash = false;
        }
    }

    private void initLocation2() {
        showDialog("定位中...");
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1800 * 1000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.d("linshi", "amapLocation.getPoiName()" + (amapLocation != null) + "amapLocation.getLongitude():" + (amapLocation.getErrorCode() == 0));
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
//                        amapLocation.getAOIName();//获取当前定位点的AOI信息
                currentPoiname = amapLocation.getPoiName();
//                    if (isLogin()&&!SPUtil.contains(context,Common.USER_ADD)) {
//                        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//                        updateAdd(token, amapLocation.getPoiName(),latitude,longitude);
//                        SPUtil.put(context, Common.USER_ADD, amapLocation.getPoiName());
//                        return;
//                    }else {
//                        tvAddress.setText((String)SPUtil.get(context,Common.USER_ADD,""));
//                    }
                String add = (String) SPUtil.get(context, Common.USER_ADD, "");
                Log.d("linshi", "isLogin():" + isLogin());
                Log.d("linshi", "SPUtil.contains(context, Common.USER_ADD):" + SPUtil.contains(context, Common.USER_ADD));
                Log.d("linshi", "TextUtils.isEmpty(add):" + TextUtils.isEmpty(add));
                if (isLogin() && SPUtil.contains(context, Common.USER_ADD) && !TextUtils.isEmpty(add)) {
                    tvAddress.setText(add);

                } else {
                    tvCity.setText(amapLocation.getCity());
                    tvAddress.setText(amapLocation.getPoiName());
                }
                mlocationClient.stopLocation();
                //位置确定弹框
//                confirmLocationDialog();
                //获取经纬度
                longitude = amapLocation.getLongitude() + "";
                latitude = amapLocation.getLatitude() + "";
                SPUtil.put(context, Common.ADDRESS, amapLocation.getPoiName());
                SPUtil.put(context, Common.LONGITUDE, longitude);
                SPUtil.put(context, Common.LATITUDE, latitude);
                //请求数据
                getHomeList(longitude, latitude);
                closeDialog();


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                tvAddress.setText("请选择");
                mlocationClient.stopLocation();
                closeDialog();
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(context);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
