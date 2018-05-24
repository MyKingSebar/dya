package cn.v1.unionc_user.ui.home.health;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.HeartHistoryListData;
import cn.v1.unionc_user.model.RecommendDoctorsData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 健康档案心率历史记录页面
 */
public class DossierHertRateHistoryActivity extends BaseActivity {
    @BindView(R.id.ptrframelayout)
    @Nullable
    PtrFrameLayout mPtrFrameLayout;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.img_back)
    ImageView img_back;
    @OnClick(R.id.img_back)
void back(){
        finish();
    }

    private LinearLayout mChatContainer;
    private ChartView chat;
    private ImageView imgLeftBtn, imgRightBtn;
    private TextView tvStartTime, tvStopTime, tvHeatRateMin, tvHeatRateMiddle,
            tvHeatRateMax, tvHertRateTotal, tvHertRateLow, tvHertRateNormal, tvHertRateHightvHertRateSerious,
            tvHertRateHigh, tvHertRateSerious;
    private RecyclerView rvHertRateHistory;
    private HistoryAdapter mHistoryAdapter;
    private List<HeartHistoryListData.DataData.Heartdata> mListData = new ArrayList<>();
    private String userId;
    private String monitorId;
    private String healthInfoId;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String startTime;
    private String endTime;
    private long nowTime;
    private TextView tvBottom;
    private ScrollView historySc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_hert_rate_history);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        mListData.clear();
        getlist();
//        gethertRateHistoryData(startTime, endTime);
        super.onResume();
    }

    private void initView() {
        tv_title.setText("历史记录");
        //初始化图表
        // chat = new ChartView(this);
        //左边选择时间按钮
        imgLeftBtn = (ImageView) findViewById(R.id.img_left_btn);
        imgLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTost("事件后移");
            }
        });
        //左边时间
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        //右边时间
        tvStopTime = (TextView) findViewById(R.id.tv_stop_time);
        //右边选择时间按钮
        imgRightBtn = (ImageView) findViewById(R.id.img_right_btn);
        imgRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTost("时间前移");
            }
        });
        //图表容器
        mChatContainer = (LinearLayout) findViewById(R.id.ll_chat_container);

        //心率最低值
        tvHeatRateMin = (TextView) findViewById(R.id.tv_heat_rate_min);
        //心率均值
        tvHeatRateMiddle = (TextView) findViewById(R.id.tv_heat_rate_middle);
        //心率最高值
        tvHeatRateMax = (TextView) findViewById(R.id.tv_heat_rate_max);
        //记录总数
        tvHertRateTotal = (TextView) findViewById(R.id.tv_hert_rate_total);
        //记录偏低次数
        tvHertRateLow = (TextView) findViewById(R.id.tv_hert_rate_low);
        //记录正常次数
        tvHertRateNormal = (TextView) findViewById(R.id.tv_hert_rate_normal);
        //记录偏高次数
        tvHertRateHigh = (TextView) findViewById(R.id.tv_hert_rate_high);
        //记录严重次数
        tvHertRateSerious = (TextView) findViewById(R.id.tv_hert_rate_serious);
        //历史列表,recycleview
        rvHertRateHistory = (RecyclerView) findViewById(R.id.rv_hert_rate_history);
        rvHertRateHistory.setFocusable(false);
        rvHertRateHistory.setLayoutManager(new ForinFullyLinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter();
        rvHertRateHistory.setAdapter(mHistoryAdapter);
        //底部文字
        tvBottom = (TextView) findViewById(R.id.tv_bottom);
        historySc = (ScrollView) findViewById(R.id.history_top_sc);
        //下拉刷新
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mListData.clear();
                getlist();
//                gethertRateHistoryData(startTime, endTime);
            }
        });
    }


    private void initData() {
        userId = getIntent().getStringExtra("userId");
        monitorId = getIntent().getStringExtra("monitorId");
        healthInfoId = getIntent().getStringExtra("healthInfoId");
        //时间处理
        nowTime = new Date().getTime();
        endTime = sdf.format(new Date(nowTime));
        startTime = sdf.format(new Date(nowTime - 7 * 24 * 3600 * 1000));
        mListData.clear();
        getlist();
//        gethertRateHistoryData(startTime, endTime);
    }
    /**
     * 获取列表
     *
     */
    private void getlist() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getHeartListData(token, "1","1","30"), new BaseObserver<HeartHistoryListData>(context) {

            @Override
            public void onResponse(HeartHistoryListData data) {
//                closeDialog();
//                if (TextUtils.equals("4000", data.getCode())) {
//                    if(data.getData().getHealthDatas().size()>0){
//                        last.setText(data.getData().getHealthDatas().get(0).getHeartRate());
//                        hhladapter.setData(data.getData().getHealthDatas());
//                        hhladapter.notifyDataSetChanged();
//                    }
//                } else {
//
//                }
                    if (TextUtils.equals("4000", data.getCode())) {
                    //历史记录集合
                        Log.d("linshi","data.getData().getHealthDatas():"+data.getData().getHealthDatas().size());
                        mListData.clear();
                    mListData.addAll(data.getData().getHealthDatas());
                    //图表数据处理
                    AssignmentChat(data.getData().getHealthDatas());
                    //控件赋值
                    AssignView(data.getData());
                    mHistoryAdapter.notifyDataSetChanged();
                    if(data.getData().getHealthDatas()!=null&&data.getData().getHealthDatas().size()>0){
                        historySc.setVisibility(View.VISIBLE);
                    }else{
                        historySc.setVisibility(View.GONE);
                    }

                } else {
                    showTost(data.getMessage() + "");
                }
                mPtrFrameLayout.refreshComplete();
                closeDialog();
            }

            @Override
            public void onFail(Throwable e) {
                showTost("获取历史记录失败");
                                mPtrFrameLayout.refreshComplete();
                closeDialog();
            }
        });
    }
//    /**
//     * 获取服务器数据
//     *
//     * @param monitorStart
//     * @param monitorEnd
//     */
//    private void gethertRateHistoryData(String monitorStart, String monitorEnd) {
//
//        showDialog("加载中...");
//        bindObservable(mAppClient.getHistoryHeartRate(monitorId, healthInfoId, monitorStart, monitorEnd, "1", "20", userId), new Action1<DosseierBloodFatHistoryData>() {
//            @Override
//            public void call(DosseierBloodFatHistoryData data) {
//                LogUtils.LOGD("HeartRate", data.toString());
//                if (TextUtils.equals("0000", data.getCode())) {
//                    //历史记录集合
//                    mListData.addAll(data.getData().getResultList());
//                    //图表数据处理
//                    AssignmentChat(data.getData().getResultList());
//                    //控件赋值
//                    AssignView(data.getData());
//                    mHistoryAdapter.notifyDataSetChanged();
//                    if(data.getData().getResultList()!=null&&data.getData().getResultList().size()>0){
//                        historySc.setVisibility(View.VISIBLE);
//                    }else{
//                        historySc.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    showToast(data.getMessage() + "");
//                }
//                mPtrFrameLayout.refreshComplete();
//                closeDialog();
//            }
//        }, new ErrorAction(this) {
//            @Override
//            public void call(Throwable throwable) {
//                super.call(throwable);
//                mPtrFrameLayout.refreshComplete();
//                closeDialog();
//                LogUtils.LOGE("HeartRate", throwable.toString());
//            }
//        });
//    }

    /**
     * 控件赋值
     *
     * @param data
     */
    private void AssignView(HeartHistoryListData.DataData data) {

        if (mListData.size() == 0) {
            //心率最低值
            tvHeatRateMin.setText("无");
            //心率均值
            tvHeatRateMiddle.setText("无");
            //心率最高值
            tvHeatRateMax.setText("无");
            //记录总数
            tvHertRateTotal.setText("共" + 0 + "次");
            //记录偏低次数
            tvHertRateLow.setText("偏低" + 0 + "次");
            //记录正常次数
            tvHertRateNormal.setText("正常" + 0 + "次");
            //记录偏高次数
            tvHertRateHigh.setText("偏高" + 0 + "次");
            //记录严重次数
            tvHertRateSerious.setText("严重" + 0 + "次");
            //
            tvBottom.setVisibility(View.GONE);
        } else {
            //心率最低值
            tvHeatRateMin.setText(data.getMinData() + "次/分");
            //心率均值
            tvHeatRateMiddle.setText(data.getAvgData() + "次/分");
            //心率最高值
            tvHeatRateMax.setText(data.getMaxData() + "次/分");
            //记录总数
            tvHertRateTotal.setText("共" + data.getTotalCount() + "次");
            //记录偏低次数
            tvHertRateLow.setText("偏低" + data.getRecord1() + "次");
            //记录正常次数
            tvHertRateNormal.setText("正常" + data.getRecord2() + "次");
            //记录偏高次数
            tvHertRateHigh.setText("偏高" + data.getRecord3() + "次");
            //记录严重次数
            tvHertRateSerious.setText("严重" + data.getRecord4() + "次");
            //
            tvBottom.setVisibility(View.VISIBLE);
            tvBottom.setText("以上数据是最新的" + mListData.size() + "条数据");
        }

    }

    /**
     * 图表数据
     *
     * @param resultList
     */
    private void AssignmentChat(List<HeartHistoryListData.DataData.Heartdata> resultList) {

        String[] noDataTime = {endTime,
                sdf.format(new Date(nowTime - 1 * 24 * 3600 * 1000)),
                sdf.format(new Date(nowTime - 2 * 24 * 3600 * 1000)),
                sdf.format(new Date(nowTime - 3 * 24 * 3600 * 1000)),
                sdf.format(new Date(nowTime - 4 * 24 * 3600 * 1000)),
                sdf.format(new Date(nowTime - 5 * 24 * 3600 * 1000)),
                sdf.format(new Date(nowTime - 6 * 24 * 3600 * 1000)),
                startTime};
        String[] XLabels = new String[noDataTime.length];
        for (int i = 0; i < noDataTime.length; i++) {
            if (i == 0) {
                String[] xData = noDataTime[noDataTime.length - 1].split("-");
                LogUtils.LOGD("noDataTime_7", Arrays.toString(xData) + "");
                XLabels[i] = xData[1] + "-" + xData[2];
            } else {
                XLabels[i] = noDataTime[noDataTime.length - 1 - i].split("-")[2];
            }
        }
        if (mListData.size() == 0) {
            //无数据时的显示
            try {
                //左边时间
                tvStartTime.setText(startTime + "");
                //右边时间
                tvStopTime.setText(endTime + "");

                LogUtils.LOGD("noDataTime", Arrays.toString(noDataTime));
                String[] AllData = new String[noDataTime.length];
                for (int i = 0; i < noDataTime.length; i++) {
                    AllData[i] = "";
                }
                LogUtils.LOGD("ChatData", Arrays.toString(XLabels));
                LogUtils.LOGD("AllData", Arrays.toString(AllData));
                if (XLabels.length == 0 || AllData.length == 0) {
                    return;
                }
                //TODO
                //初始化图表
                chat = new ChartView(this);
                mChatContainer.removeAllViews();
                mChatContainer.addView(chat);
                chat.setInfo(noDataTime.length, XLabels, // X轴刻度
                        AllData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String[] AllData = new String[resultList.size()];
            try {
                //左边时间
                tvStartTime.setText(startTime + "");
                //右边时间
                tvStopTime.setText(endTime + "");
                for (int i = 0; i < resultList.size(); i++) {
                    AllData[i] = resultList.get(resultList.size() - 1 - i).getHeartRate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogUtils.LOGD("ChatData", Arrays.toString(XLabels));
            LogUtils.LOGD("AllData", Arrays.toString(AllData));
            if (XLabels.length == 0 || AllData.length == 0) {
                return;
            }
            //TODO
            //初始化图表
            chat = new ChartView(this);
            mChatContainer.removeAllViews();
            mChatContainer.addView(chat);
            chat.setInfo(resultList.size(), XLabels, // X轴刻度
                    AllData // 数据
            );
        }

    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DossierHertRateHistoryActivity.this).inflate(R.layout.item_dossier_hertrate_history, parent, false);
            HistoryViewHolder viewHolder = new HistoryViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, final int position) {
            holder.tvTime.setText(mListData.get(position).getMonitorDate() + "");
            //一分钟测量
            if (!TextUtils.isEmpty(mListData.get(position).getHeartRateImagePath())) {
                //查看心电图
                holder.tvLookEcg.setVisibility(View.VISIBLE);
            }else{
                holder.tvLookEcg.setVisibility(View.GONE);
            }
            holder.tvHeartImg.setVisibility(View.GONE);
            holder.imgRedDot.setVisibility(View.GONE);
            //心率之
            holder.tvHeartRate.setText(mListData.get(position).getHeartRate() + "次/分");
            try {
                int valueAfter = Integer.parseInt(mListData.get(position).getHeartRate());
                //心率值    60～100 正常 低于60 心率偏低 高于100 小于160 偏高 大于160 严重
                if (valueAfter < 60) {
                    holder.tv_heart_rate_state.setVisibility(View.VISIBLE);
                    holder.tv_heart_rate_state.setTextColor(Color.parseColor("#4085E7"));
                    holder.tv_heart_rate_state.setText("偏低");

                } else if (valueAfter > 100) {
                    holder.tv_heart_rate_state.setVisibility(View.VISIBLE);
                    holder.tv_heart_rate_state.setTextColor(Color.parseColor("#F15252"));
                    holder.tv_heart_rate_state.setText("偏高");
                } else {
                    holder.tv_heart_rate_state.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //不适应症
            if (TextUtils.isEmpty(mListData.get(position).getDisorder())) {
                holder.tvHeartNoIndications.setText("未填写");
            } else {
                holder.tvHeartNoIndications.setText(mListData.get(position).getDisorder() + "");
            }
            //药物
            if (TextUtils.isEmpty(mListData.get(position).getCureMedicine())) {
                holder.tvHeartDrug.setText("未填写");
            } else {
                holder.tvHeartDrug.setText(mListData.get(position).getCureMedicine() + "");
            }
            //心脏病类型
            if (TextUtils.isEmpty(mListData.get(position).getDiabetesType())) {
                holder.tvHeartDiseaseType.setText("未填写");
            } else {
                holder.tvHeartDiseaseType.setText(mListData.get(position).getDiabetesType() + "");
            }

            holder.tvLookEcg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentECGPhoto = new Intent(DossierHertRateHistoryActivity.this, DossierHeartRateECGPhotoActivity.class);
                    intentECGPhoto.putExtra("pngFileName", mListData.get(position).getHeartRateImagePath());
                    startActivity(intentECGPhoto);
                }
            });
//            holder.tvHeartImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intentECGPhoto = new Intent(DossierHertRateHistoryActivity.this, DossierHertRateUploadFileActivity.class);
//                    intentECGPhoto.putExtra("userId", userId);
//                    intentECGPhoto.putExtra("monitorId", monitorId);
//                    intentECGPhoto.putExtra("healthInfoId", healthInfoId);
//                    intentECGPhoto.putExtra("staticId", mListData.get(position).getStaticId());
//                    intentECGPhoto.putExtra("isRead", mListData.get(position).getIsRead());
//                    intentECGPhoto.putExtra("readType", mListData.get(position).getReadType());
//                    intentECGPhoto.putExtra("measureTime", mListData.get(position).getMonitorDate().trim());
//                    intentECGPhoto.putExtra("isUpLoad", mListData.get(position).getIsUpload());
//                    intentECGPhoto.putExtra("isHasResult", mListData.get(position).getIsHasResult());
//                    startActivity(intentECGPhoto);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }

        class HistoryViewHolder extends RecyclerView.ViewHolder {

            TextView tvTime, tvHeartImg, tvHeartRate, tv_heart_rate_state, tvHeartNoIndications, tvHeartDrug, tvHeartDiseaseType, tvLookEcg;
            ImageView imgEdit, imgRedDot;
            LinearLayout llNoIndications;
            LinearLayout llHeartDrug;
            LinearLayout llHertDiseaseType;

            public HistoryViewHolder(View itemView) {
                super(itemView);
                tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
                tvHeartImg = (TextView) itemView.findViewById(R.id.tv_heart_img);
                tvHeartRate = (TextView) itemView.findViewById(R.id.tv_heart_rate);
                tv_heart_rate_state = (TextView) itemView.findViewById(R.id.tv_heart_rate_state);
                tvHeartNoIndications = (TextView) itemView.findViewById(R.id.tv_heart_no_indications);
                llNoIndications = (LinearLayout) itemView.findViewById(R.id.ll_no_indications);
                tvHeartDrug = (TextView) itemView.findViewById(R.id.tv_heart_drug);
                llHeartDrug = (LinearLayout) itemView.findViewById(R.id.ll_heart_drug);
                tvHeartDiseaseType = (TextView) itemView.findViewById(R.id.tv_heart_disease_type);
                llHertDiseaseType = (LinearLayout) itemView.findViewById(R.id.ll_hert_disease_type);
                tvLookEcg = (TextView) itemView.findViewById(R.id.tv_look_ecg);
                imgRedDot = (ImageView) itemView.findViewById(R.id.img_red_dot);
            }
        }
    }

    /**
     * 处理低压数据颜色
     *
     * @return
     */
    private Spannable getHertrateState(String value) {
        String valueColor = "";
        String valueSate = "";
        int valueAfter = 0;
        try {
            valueAfter = Integer.parseInt(value);
        } catch (Exception e) {
        }
        //心率值    60～100 正常 低于60 心率偏低 高于100 小于160 偏高 大于160 严重
        if (valueAfter < 60) {
            valueColor = "#4085E7";
            valueSate = "偏低";
        } else if (valueAfter > 100) {
            valueColor = "#F15252";
            valueSate = "偏高";
        } else {
            valueColor = "#F15252";
            valueSate = "";
        }
        return new HtmlFontUtil().toHtmlFormat(new HtmlFontUtil().getHtmlStr(valueColor, (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity * 12), valueSate + ""));
    }
}
