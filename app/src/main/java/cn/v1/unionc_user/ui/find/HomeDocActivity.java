package cn.v1.unionc_user.ui.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

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
import cn.v1.unionc_user.model.MeWatchingDoctorListData;
import cn.v1.unionc_user.model.MyRecommenDoctorsData;
import cn.v1.unionc_user.model.RecommendDoctorsData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.FindHomeDoctorListAdapter;
import cn.v1.unionc_user.ui.adapter.MeWatchingDoctorListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class HomeDocActivity extends BaseActivity {


    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView mainRecycleview;
    @BindView(R.id.img_back)
    ImageView bakc;
    @BindView(R.id.tv_title)
    TextView title;

    @OnClick(R.id.img_back)
     void back(){
        finish();
    }


    private FindHomeDoctorListAdapter findHomeDoctorListAdapter;
    private List<MyRecommenDoctorsData.DataData.DoctorsData> datas = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_homedoc_recycleview);
        unbinder= ButterKnife.bind(this);
        initView();
        initrecommenddoctor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    private void initrecommenddoctor() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.myrecommenddoctors((String)SPUtil.get(context,Common.LONGITUDE,""), (String)SPUtil.get(context,Common.LATITUDE,""),"50","1",token), new BaseObserver<MyRecommenDoctorsData>(context) {
            @Override
            public void onResponse(MyRecommenDoctorsData data) {
                findHomeDoctorListAdapter.setData(data.getData().getRecommendDoctors());
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }


    private void initView() {
        title.setText("家庭医生");

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        findHomeDoctorListAdapter = new FindHomeDoctorListAdapter(context);
        mainRecycleview.setAdapter(findHomeDoctorListAdapter);

    }



}
