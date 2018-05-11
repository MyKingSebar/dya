package cn.v1.unionc_user.ui.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.MyDutyDoctorsData;
import cn.v1.unionc_user.model.MyRecommenDoctorsData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.FindDutyDoctorListAdapter;
import cn.v1.unionc_user.ui.adapter.FindHomeDoctorListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class DutyDocActivity extends BaseActivity {


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


    private FindDutyDoctorListAdapter findDutyDoctorListAdapter;
    private List<MyRecommenDoctorsData.DataData.DoctorsData> datas = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_dutydoc_recycleview);
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
        ConnectHttp.connect(UnionAPIPackage.mydutydoctors((String)SPUtil.get(context,Common.LONGITUDE,""), (String)SPUtil.get(context,Common.LATITUDE,""),"50","1"), new BaseObserver<MyDutyDoctorsData>(context) {
            @Override
            public void onResponse(MyDutyDoctorsData data) {
                if(data.getData().getDutyDoctors().size()>0){

                    findDutyDoctorListAdapter.setData(data.getData().getDutyDoctors());
                }else{
                    mainRecycleview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(Throwable e) {
                mainRecycleview.setVisibility(View.GONE);
            }
        });
    }


    private void initView() {
        title.setText("值班医生");

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        findDutyDoctorListAdapter = new FindDutyDoctorListAdapter(context);
        mainRecycleview.setAdapter(findDutyDoctorListAdapter);

    }



}
