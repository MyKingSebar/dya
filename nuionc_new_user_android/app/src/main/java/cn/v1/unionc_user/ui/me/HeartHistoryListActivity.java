package cn.v1.unionc_user.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.HeartHistoryListData;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.TIMSigData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.LoginAgreementActivity;
import cn.v1.unionc_user.ui.MainActivity;
import cn.v1.unionc_user.ui.adapter.HeartHistoryListAdapter;
import cn.v1.unionc_user.ui.adapter.HomeListAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.health.DossierHeartRateAutoActivity;


public class HeartHistoryListActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.last)
    TextView last;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_recycleview)
    RecyclerView mainRecycleview;
    @BindView(R.id.start)
    Button start;

    HeartHistoryListAdapter hhladapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_hertrate_historylist);
        ButterKnife.bind(this);
        initView();
        getlist();
    }


    @OnClick({R.id.img_back,R.id.start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.start:
                if (isLogin()) {
                    Intent intent = new Intent(context, DossierHeartRateAutoActivity.class);
                    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
                    intent.putExtra("userId", token);
                    intent.putExtra("monitorId", "1");
                    startActivity(intent);
//            }
                } else {
                    goNewActivity(LoginActivity.class);
                }
                break;

        }
    }

    private void initView() {
        title.setText("心率检测历史");

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));

        hhladapter = new HeartHistoryListAdapter(context);
        mainRecycleview.setAdapter(hhladapter);

    }




    /**
     * 获取列表
     *
     */
    private void getlist() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getHeartListData(token, "1","1","200"), new BaseObserver<HeartHistoryListData>(context) {

            @Override
            public void onResponse(HeartHistoryListData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    if(data.getData().getHealthDatas().size()>0){
                        last.setText(data.getData().getHealthDatas().get(0).getHeartRate());
                        hhladapter.setData(data.getData().getHealthDatas());
                        hhladapter.notifyDataSetChanged();
                    }
                } else {

                }
            }

            @Override
            public void onFail(Throwable e) {
                showTost("获取历史记录失败");
            }
        });
    }






}
