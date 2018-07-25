package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.OldmanInfoData;
import cn.v1.unionc_user.model.PrescriptionInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class OldManInfoActivity extends BaseActivity {
private String elderlyUserId;


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
        getOldman();
    }

    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("elderlyUserId")){
            elderlyUserId=intent.getStringExtra("elderlyUserId");
            Log.d("linshi","elderlyUserId:"+elderlyUserId);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("linshi", "on destroy");
        super.onDestroy();
        unbinder.unbind();
    }




    protected void getOldman() {
        if(TextUtils.isEmpty(elderlyUserId)){
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.getOldmanInfo(getToken(),elderlyUserId), new BaseObserver<OldmanInfoData>(context) {

            @Override
            public void onResponse(OldmanInfoData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    OldmanInfoData.DataData.DataDataData datadata=data.getData().getElderLyInfo();
                    String src="姓名："+datadata.getUserName()+"\nid："+datadata.getCardNo()+"\n地址："+datadata.getAddress()+"\n电话："+datadata.getTelphone();
                    tv.setText(src);

                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                showTost("保存失败");
            }
        });
    }
    private void initView() {
        title.setText("老人详情");

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
