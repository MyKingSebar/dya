package cn.v1.unionc_user.ui.find;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.neliveplayer.sdk.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.NESDKConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.demo.activity.NEVideoPlayerActivity;
import cn.v1.demo.receiver.NELivePlayerObserver;
import cn.v1.demo.receiver.Observer;
import cn.v1.demo.util.HttpPostUtils;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.NetCouldPullData;
import cn.v1.unionc_user.model.PrescriptionInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.FindLiveListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class PrescriptionActivity extends BaseActivity {
private String prescriptionId;


    private Unbinder unbinder;

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.img_back)
    ImageView bakc;
    @BindView(R.id.tv_title)
    TextView title;

    @OnClick(R.id.img_back)
     void back(){
        finish();
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rescription_info);
        unbinder= ButterKnife.bind(this);
        initData();
        initView();
        initprescription();
    }

    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("prescriptionId")){
            prescriptionId=intent.getStringExtra("prescriptionId");
            Log.d("linshi","prescriptionId:"+prescriptionId);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("linshi", "on destroy");
        super.onDestroy();
        unbinder.unbind();
    }



    private void initprescription() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if(TextUtils.isEmpty(prescriptionId)){
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.prescriptioninfo(token,prescriptionId), new BaseObserver<PrescriptionInfoData>(context) {
            @Override
            public void onResponse(PrescriptionInfoData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    String src="医生姓名："+data.getData().getPrescription().getDoctName()+"\n补充说明:"+data.getData().getPrescription().getSupplement()+"\n临床诊断:"+data.getData().getPrescription().getClinicalDiagnosis()
                            +"\n医院:"+data.getData().getPrescription().getClinicName()+"\n价格:"+data.getData().getPrescription().getCharge();
//                    tv.setText(new Gson().toJson(data.getData()));
                    tv.setText(src);
                }else{
                    showTost(data.getMessage());
                }

            }
            @Override
            public void onFail(Throwable e) {
            }
        });
    }


    private void initView() {
        title.setText("处方详情");

    }


    @Override
    public void onPause() {
        Log.d("linshi", "on pause");
        super.onPause();
    }



    @Override
    public void onRestart() {
        Log.d("linshi", "on restart");
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.d("linshi", "on resmue");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d("linshi", "on start");
        super.onStart();
    }


}
