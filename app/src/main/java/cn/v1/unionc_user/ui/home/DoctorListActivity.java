package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.GetLiveDoctorListData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.DoctorListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class DoctorListActivity extends BaseActivity {
    private Unbinder unbinder;
    private DoctorListAdapter doctorListAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.img_back)
    ImageView im_back;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlist);
        unbinder= ButterKnife.bind(this);
        initView();
        initdoclist();
    }

    private void initdoclist() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getvideodoctors(token,"1","50"), new BaseObserver<GetLiveDoctorListData>(context) {
            @Override
            public void onResponse(GetLiveDoctorListData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    if(data.getData().getVideoDoctors().size()>0){

                        doctorListAdapter.setData(data.getData().getVideoDoctors());
                    }else{
                        recyclerview.setVisibility(View.GONE);
                    }

                }else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                recyclerview.setVisibility(View.GONE);
            }
        });
    }
    private void initView() {
        title.setText("视频复诊");

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        doctorListAdapter = new DoctorListAdapter(context);
        recyclerview.setAdapter(doctorListAdapter);

    }


}
