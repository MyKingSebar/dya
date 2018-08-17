package cn.v1.unionc_user.ui.home.BloodPressure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.HeartHistoryData;
import cn.v1.unionc_user.model.OMLHistoryData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.home.BloodPressure.adapter.BloodPresureHistoryAdapter;
import cn.v1.unionc_user.ui.home.BloodPressure.adapter.BloodPresureHistoryAdapter2;
import cn.v1.unionc_user.ui.home.BloodPressure.data.BloodPresureHistoryData;
import cn.v1.unionc_user.ui.home.BloodPressure.utils.RecyclerViewUtil;
import cn.v1.unionc_user.ui.home.health.AlarmDialog;
import cn.v1.unionc_user.ui.home.health.IRespCallBack;

public class BloodPresureHistoryActivity extends BaseActivity implements IonSlidingViewClickListener {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecord;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;
    @OnClick(R.id.img_back)
    public void back(){
        finish();
    }

    private BloodPresureHistoryAdapter2 bloodPresureHistoryAdapter;
    private String patientInfoId = "";
//    private List<BloodPresureHistoryData.DataData.BloodPressureData> listDatas = new ArrayList<>();
    private List<OMLHistoryData.DataData.OMLdatta> listDatas = new ArrayList<>();
//    private String fromWhere = "";//0从健康档案跳历史默认，1从血压计绑定的设备详情页面跳转到数据记录;
    private String BdaCode = "";
    private RecyclerViewUtil mRecyclerViewUtil;
    private boolean isRefresh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presure_history);
        ButterKnife.bind(this);
        initView();
        initData();
        getBloodPressureData(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRefresh = true;
        getBloodPressureData(1);
    }

    private void initData() {
        if (getIntent().hasExtra("patientInfoId")) {
            patientInfoId = getIntent().getStringExtra("patientInfoId");
        }
//        if (getIntent().hasExtra("fromWhere")) {
//            fromWhere = getIntent().getStringExtra("fromWhere");
//        }
        if (getIntent().hasExtra("BdaCode")) {
            BdaCode = getIntent().getStringExtra("BdaCode");
        }
    }

    private void initView() {
        tvTitle.setText("历史记录");
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        bloodPresureHistoryAdapter = new BloodPresureHistoryAdapter2(this);
        recycleview.setAdapter(bloodPresureHistoryAdapter);
        mRecyclerViewUtil = new RecyclerViewUtil(new RecyclerViewUtil.RecyclerCallBack() {
            @Override
            public void doRefresh() {

            }

            @Override
            public void doLoadMore() {
                isRefresh = false;
                getBloodPressureData(getpageNum());
            }
        }, recycleview, bloodPresureHistoryAdapter, true);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteBtnCilck(View view, final int position) {
            deleteRecord(listDatas.get(position).getDataId() + "", position);


    }

    @Override
    public void onDeleteDevice(String Bda) {

    }


    private void deleteRecord(String dataId, final int position) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.deleteOMLData(token, "2", dataId), new BaseObserver<BaseData>(context) {

            @Override
            public void onResponse(BaseData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("删除成功");
                    listDatas.remove(position);
                    bloodPresureHistoryAdapter.notifyItemRemoved(position);
                    if (0 == listDatas.size()) {
                        llNoRecord.setVisibility(View.VISIBLE);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dataBind(listDatas);
                        }
                    }, 500);
                } else {

                }
            }

            @Override
            public void onFail(Throwable e) {
                showTost("删除失败");
            }
        });
//        showDialog("删除中...");
//        bindObservable(mAppClient.deleteBloodPressureData(recordId, getUserId()), new Action1<BaseData>() {
//            @Override
//            public void call(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    showToast("删除成功");
//                    listDatas.remove(position);
//                    bloodPresureHistoryAdapter.notifyItemRemoved(position);
//                    if (0 == listDatas.size()) {
//                        llNoRecord.setVisibility(View.VISIBLE);
//                    }
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dataBind(listDatas);
//                        }
//                    }, 500);
//                } else {
//                    showToast(data.getMessage());
//                }
//
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                closeDialog();
//            }
//        });
    }


    /**
     * 获取血压数据
     */
    private void getBloodPressureData(int pagenum) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getOMLData(token, "2", "1","20"), new BaseObserver<OMLHistoryData>(context) {

            @Override
            public void onResponse(OMLHistoryData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("获取成功");
                                        if (isRefresh) {
                        listDatas.clear();
                    }
                    listDatas.addAll(data.getData().getHealthDatas());
                    dataBind(listDatas);
                    mRecyclerViewUtil.onLoadComplete(data.getData().getHealthDatas().size() < CommonContract.TIMELINE_PAGE_SIZE);
                    }
                 else {
showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                showTost("删除失败");
            }
        });
//        showDialog("加载中...");
//        bindObservable(mAppClient.getBloodPressureData("0", pagenum + "", getUserId(), BdaCode), new Action1<BlueBloodHistoryData>() {
//            @Override
//            public void call(BlueBloodHistoryData data) {
//                closeDialog();
//                if (TextUtils.equals("0000", data.getCode())) {
//                    if (isRefresh) {
//                        listDatas.clear();
//                    }
//                    listDatas.addAll(data.getData());
//                    dataBind(listDatas);
//                    mRecyclerViewUtil.onLoadComplete(data.getData().size() < CommonContract.TIMELINE_PAGE_SIZE);
//                } else {
//                    showToast(data.getCode());
//                }
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                mRecyclerViewUtil.onLoadMoreFailed();
//                closeDialog();
//            }
//        });
    }


    private String measureData = "";
    private List<OMLHistoryData.DataData.OMLdatta> originalDatas = new ArrayList<>();
    private List<OMLHistoryData.DataData.OMLdatta> binddatas = new ArrayList<>();
//    private List<BloodPresureHistoryData.DataData.BloodPressureData> originalDatas = new ArrayList<>();
//    private List<BloodPresureHistoryData.DataData.BloodPressureData> binddatas = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (0 == binddatas.size()) {
                    llNoRecord.setVisibility(View.VISIBLE);
                } else {
                    llNoRecord.setVisibility(View.GONE);
                }
                bloodPresureHistoryAdapter.setData(binddatas);
                closeDialog();
            }
        }
    };

    private void dataBind(final List<OMLHistoryData.DataData.OMLdatta> listDatas) {
        showDialog("数据加载中...");
        new Thread() {
            @Override
            public void run() {
                super.run();
                measureData = null;
                originalDatas.clear();
                originalDatas.addAll(listDatas);
                ok:
                for (int i = 0; i < originalDatas.size(); i++) {
                    if (TextUtils.equals("0", originalDatas.get(i).getHighPressure()) &&
                            TextUtils.equals("0", originalDatas.get(i).getLowPressure()) ||
                            TextUtils.isEmpty(originalDatas.get(i).getHighPressure()) &&
                                    TextUtils.isEmpty(originalDatas.get(i).getLowPressure())
                            ) {
                        originalDatas.remove(i);
                        break ok;
                    }
//                    if (TextUtils.equals(measureData, originalDatas.get(i).getMonitorDate())) {
//                        originalDatas.get(i).setHead(false);
//                    } else {
//                        measureData = originalDatas.get(i).getMonitorDate();
//                        originalDatas.get(i).setHead(true);
//                        originalDatas.get(i).setHeadText(measureData);
//                    }
                }
                binddatas.clear();
                binddatas.addAll(originalDatas);
                handler.sendEmptyMessage(0);//handler发送消息
            }
        }.start();
    }
//    private void dataBind(final List<BloodPresureHistoryData.DataData.BloodPressureData> listDatas) {
//        showDialog("数据加载中...");
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                measureData = null;
//                originalDatas.clear();
//                originalDatas.addAll(listDatas);
//                ok:
//                for (int i = 0; i < originalDatas.size(); i++) {
//                    if (TextUtils.equals("0", originalDatas.get(i).getHighPressure()) &&
//                            TextUtils.equals("0", originalDatas.get(i).getLowPressure()) ||
//                            TextUtils.isEmpty(originalDatas.get(i).getHighPressure()) &&
//                                    TextUtils.isEmpty(originalDatas.get(i).getLowPressure())
//                            ) {
//                        originalDatas.remove(i);
//                        break ok;
//                    }
//                    if (TextUtils.equals(measureData, originalDatas.get(i).getMeasureDate())) {
//                        originalDatas.get(i).setHead(false);
//                    } else {
//                        measureData = originalDatas.get(i).getMeasureDate();
//                        originalDatas.get(i).setHead(true);
//                        originalDatas.get(i).setHeadText(measureData);
//                    }
//                }
//                binddatas.clear();
//                binddatas.addAll(originalDatas);
//                handler.sendEmptyMessage(0);//handler发送消息
//            }
//        }.start();
//    }


    /**
     * 获取也数
     */
    private int getpageNum() {
        int pageNum = 0;
        if (listDatas.size() > 0 && listDatas.size() % 20 == 0) {
            pageNum = listDatas.size() / 20 + 1;
        } else {
            mRecyclerViewUtil.onLoadComplete(true);
        }
        return pageNum;
    }

}
