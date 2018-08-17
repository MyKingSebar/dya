package cn.v1.unionc_user.ui.home;


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
import cn.v1.unionc_user.model.GetNurseListData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.NurseListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseListFragment extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView mainRecycleview;

    private NurseListAdapter nurseListAdapter;
    private List<GetNurseListData.DataData.DataDataData> datas = new ArrayList<>();

    public NurseListFragment() {
        // Required empty public constructor
        Log.d("linshi","NurseListFragment");
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
        getDoctorList();
    }




    private void initView() {

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        nurseListAdapter = new NurseListAdapter(context);
        mainRecycleview.setAdapter(nurseListAdapter);

    }

    private void getDoctorList() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getnurses("4","1","50"), new BaseObserver<GetNurseListData>(context) {
            @Override
            public void onResponse(GetNurseListData data) {
                Log.d("linshi","datas:"+new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getNursers().size() != 0) {
                        datas=data.getData().getNursers();
                    }
                    nurseListAdapter.setData(datas);
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
