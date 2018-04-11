package cn.v1.unionc_user.ui.me;


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

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.WatchingActivityData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.ActivityAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyActivity1Fragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView mainRecycleview;
private String type;
    private ActivityAdapter activityAdapter;
    private List<WatchingActivityData.DataData.ActivitiesData> datas = new ArrayList<>();

    public MyActivity1Fragment() {
        // Required empty public constructor
        Log.d("linshi","WatchingHospitalFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        if(getArguments()!=null){
            //取出保存的值
            type=getArguments().getString("type");
            Log.d("linshi","type："+type);
        }
    }

    public   MyActivity1Fragment newInstance(String text){
        MyActivity1Fragment myActivity1Fragment = new MyActivity1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", text);
        //fragment保存参数，传入一个Bundle对象
        myActivity1Fragment.setArguments(bundle);
        return myActivity1Fragment;
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
        getList();
    }




    private void initView() {
        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        activityAdapter = new ActivityAdapter(context);
        mainRecycleview.setAdapter(activityAdapter);

    }

    private void getList() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if(TextUtils.equals(type,Common.APPLYACTIVITY)){
            initapply(token);
        }else if(TextUtils.equals(type,Common.COLLECTACTIVITY)){
            initcollect(token);
        }



    }

private void initcollect(String token){
    ConnectHttp.connect(UnionAPIPackage.getMeWatchingActivityList(token), new BaseObserver<WatchingActivityData>(context) {
        @Override
        public void onResponse(WatchingActivityData data) {
            Log.d("linshi","datas:"+new Gson().toJson(datas));

            if (TextUtils.equals("4000", data.getCode())) {
                datas.clear();
                if (data.getData().getActivities().size() != 0) {
                    datas=data.getData().getActivities();
                }
                activityAdapter.setData(datas);
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
private void initapply(String token){
    ConnectHttp.connect(UnionAPIPackage.getMeApplyActivityList(token), new BaseObserver<WatchingActivityData>(context) {
        @Override
        public void onResponse(WatchingActivityData data) {
            Log.d("linshi","datas:"+new Gson().toJson(data));

            if (TextUtils.equals("4000", data.getCode())) {
                datas.clear();
                if (data.getData().getActivities().size() != 0) {
                    datas=data.getData().getActivities();
                }
                Log.d("linshi","datas:"+(datas==null));
                if(datas!=null){

                    activityAdapter.setData(datas);
                }else{
                    datas = new ArrayList<>();
                }
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
