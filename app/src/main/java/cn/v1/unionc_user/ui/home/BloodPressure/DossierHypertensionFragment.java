package cn.v1.unionc_user.ui.home.BloodPressure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.BloodPressure.data.HealthMoitorItemData;
import cn.v1.unionc_user.ui.home.BloodPressure.data.HealthMonitorData;
import cn.v1.unionc_user.ui.home.health.DossierWheelViewActivity;
import cn.v1.unionc_user.ui.home.health.HtmlFontUtil;
import cn.v1.unionc_user.ui.home.health.StringUtil;

/**
 * Created by An4 on 2016/9/22.
 */
public class DossierHypertensionFragment extends BaseFragment {

    @BindView(R.id.recyclerview_dossierdiabetes)
    RecyclerView mRecyclerView;
    @BindView(R.id.dossierdiabetes_week1)
    TextView weekTv1;
    @BindView(R.id.dossierdiabetes_week2)
    TextView weekTv2;
    @BindView(R.id.dossierdiabetes_week3)
    TextView weekTv3;
    @BindView(R.id.dossierdiabetes_week4)
    TextView weekTv4;
    @BindView(R.id.dossierdiabetes_week5)
    TextView weekTv5;
    @BindView(R.id.dossierdiabetes_week6)
    TextView weekTv6;
    @BindView(R.id.dossierdiabetes_week7)
    TextView weekTv7;

    public static final String KEY_STARTTIME = "key_starttime";
    public static final String KEY_ENDTIME = "key_endtime";
    public static final String KEY_MONITORID = "key_monitorid";
    public static final String KEY_PATIENTINFOID = "key_patientinfoid";
    MyAdapter myAdapter;
    String startTime, endTime, monitorId, patientInfoId;
    ArrayList<HealthMonitorData.DataBean.MonitorDataBean> mListData = new ArrayList<>();
    ArrayList<HealthMoitorItemData> itemValue = new ArrayList<>();
    ArrayList<TextView> textViewList = new ArrayList<>();
    ArrayList<String> picUrlList = new ArrayList<>();
    SimpleDateFormat sds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfMD = new SimpleDateFormat("MM-dd");

    ArrayList<String> beforeData = new ArrayList<>();
    ArrayList<String> afterData = new ArrayList<>();
    int chooseIndex = 0;
    public static DossierHypertensionFragment newInstance(String startTime, String endTime, String monitorId, String patientInfoId) {
        Bundle args = new Bundle();
        args.putString(KEY_STARTTIME, startTime);
        args.putString(KEY_ENDTIME, endTime);
        args.putString(KEY_MONITORID, monitorId);
        args.putString(KEY_PATIENTINFOID, patientInfoId);
        DossierHypertensionFragment fragment = new DossierHypertensionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = getArguments().getString(KEY_STARTTIME);
        endTime = getArguments().getString(KEY_ENDTIME);
        monitorId = getArguments().getString(KEY_MONITORID);
        patientInfoId = getArguments().getString(KEY_PATIENTINFOID);
        for (int i = 1 ; i < 300 ; i++){
            beforeData.add(i+"");
            afterData.add(i+"");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_dossierhypertension_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData("加载中...");
    }

    public void initData(String showStr) {
        showDialog("加载中...");
//        bindObservable(mAppClient.getHealthMonitorData("",monitorId, patientInfoId, startTime, endTime, "1", "7","",""), new Action1<HealthMonitorData>() {
//            @Override
//            public void call(HealthMonitorData healthMonitorData) {
//                closeDialog();
//                if (healthMonitorData.getCode().equals("0000")) {
//                    mListData.clear();
//                    itemValue.clear();
//                    picUrlList.clear();
//                    ((DossierHypertensionActivity) getActivity()).setMedicineStr(healthMonitorData.getData().getCureMedicine());
//                    ((DossierHypertensionActivity) getActivity()).setMessageConut(healthMonitorData.getData().getMessageCount());
//                    ((DossierHypertensionActivity) getActivity()).setDateStr(healthMonitorData.getData().getDateStr());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic1()))
//                        picUrlList.add(healthMonitorData.getData().getPic1());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic2()))
//                        picUrlList.add(healthMonitorData.getData().getPic2());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic3()))
//                        picUrlList.add(healthMonitorData.getData().getPic3());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic4()))
//                        picUrlList.add(healthMonitorData.getData().getPic4());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic5()))
//                        picUrlList.add(healthMonitorData.getData().getPic5());
//                    if(!TextUtils.isEmpty(healthMonitorData.getData().getPic6()))
//                        picUrlList.add(healthMonitorData.getData().getPic6());
//                    ((DossierHypertensionActivity) getActivity()).setPicUrlList(picUrlList);
//                    mListData.addAll(healthMonitorData.getData().getMonitorData());
//                    setItemValue();
//                    myAdapter.notifyDataSetChanged();
//                    try {
//                        long nowTime = new Date().getTime();
//                        long startTime = sds.parse(healthMonitorData.getData().getMonitorDateStart()).getTime();
//                        long endTime = sds.parse(healthMonitorData.getData().getMonitorDateEnd()).getTime();
//                        if (nowTime >= startTime && nowTime <= endTime) {
//                            ((DossierHypertensionActivity) getActivity()).setWeekStr("本周");
//                        } else {
//                            ((DossierHypertensionActivity) getActivity()).setWeekStr(sdfMD.format(sds.parse(healthMonitorData.getData().getMonitorDateStart()).getTime()) +
//                                    "~" +sdfMD.format(sds.parse(healthMonitorData.getData().getMonitorDateEnd()).getTime()));
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    showToast(healthMonitorData.getMessage());
//                }
//            }
//        }, new ErrorAction(getActivity()) {
//            @Override
//            public void call(Throwable throwable) {
//                closeDialog();
//                super.call(throwable);
//            }
//        });
    }

    private void setItemValue() {
        for (int i = 0; i < mListData.size(); i++) {
            if (i <= 6) {
                String mStr = mListData.get(i).getWeek().replace(" ","\n");
                textViewList.get(i).setText(StringUtil.showDiffSizeString(mStr,mStr.split("\\n")[1],"#DF000000",8));
            }
            for (int j = 1; j <= 3; j++) {
                HealthMoitorItemData datas = new HealthMoitorItemData();
                datas.setDataIndex(j + "");
                datas.setId(mListData.get(i).getId());
                datas.setMonitorDate(mListData.get(i).getMonitorDate());
                datas.setMonitorId(monitorId);
                switch (j) {
                    case 1:
                        datas.setDataValue(mListData.get(i).getData1());
                        break;
                    case 2:
                        datas.setDataValue(mListData.get(i).getData2());
                        break;
                    case 3:
                        datas.setDataValue(mListData.get(i).getData3());
                        break;
                }
                itemValue.add(datas);
            }
        }
    }

    private void initView() {
        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setHasFixedSize(true);
        textViewList.add(0, weekTv1);
        textViewList.add(1, weekTv2);
        textViewList.add(2, weekTv3);
        textViewList.add(3, weekTv4);
        textViewList.add(4, weekTv5);
        textViewList.add(5, weekTv6);
        textViewList.add(6, weekTv7);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dossierdiabetes_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final HealthMoitorItemData myData = itemValue.get(position);
            if (!TextUtils.isEmpty(myData.getDataValue())&&myData.getDataValue().split("/").length>=2) {
                holder.itemTv.setText(getStrColorString(myData.getDataValue()));
            } else {
                holder.itemTv.setText("+");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseIndex = position;
                    long nowTime = new Date().getTime();
                    long selectTime = 0;
                    try {
                        selectTime = sdfYMD.parse(myData.getMonitorDate()).getTime();
                        nowTime = sdfYMD.parse(sds.format(nowTime)).getTime();
                        if (nowTime < selectTime) {
                            showTost("不能选择将来日期");
                        } else {
                            int first_default = 120,second_default = 70;
                            if(itemValue.get(chooseIndex).getDataValue().contains("/")&&itemValue.get(chooseIndex).getDataValue().split("/").length>=2){
                                first_default = Integer.parseInt(itemValue.get(chooseIndex).getDataValue().split("/")[0]);
                                second_default = Integer.parseInt(itemValue.get(chooseIndex).getDataValue().split("/")[1]);
                            }
                            Intent intent = DossierWheelViewActivity.getPickViewActivityTwo(getActivity(), DossierWheelViewActivity.TYPE_TWO, "收缩压(高压)/舒张压(低压)", beforeData, afterData,first_default-1,second_default-1);
                            startActivityForResult(intent, 9999);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemValue.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.dossierdiabetes_item_tv)
            TextView itemTv;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    String data1, data2, data3, data4, data5, data6, data7, data8;

    public void setDataStr(String index, String value) {
        data1 = "";
        data2 = "";
        data3 = "";
        data4 = "";
        data5 = "";
        data6 = "";
        data7 = "";
        data8 = "";
        if (index.equals("1")) {
            data1 = value;
        } else if (index.equals("2")) {
            data2 = value;
        } else if (index.equals("3")) {
            data3 = value;
        } else if (index.equals("4")) {
            data4 = value;
        } else if (index.equals("5")) {
            data5 = value;
        } else if (index.equals("6")) {
            data6 = value;
        } else if (index.equals("7")) {
            data7 = value;
        } else if (index.equals("8")) {
            data8 = value;
        }
    }

    public Spannable getStrColorString(String mValue) {
        String beforeColor ;
        String afterColor ;
        int valueBefore = Integer.parseInt(mValue.split("/")[0]);
        int valueAfter = Integer.parseInt(mValue.split("/")[1]);
        //收缩压90-139为正常 正常值为黑色。收缩压<90时为偏低值，显示 蓝色；>139时为偏高值，显示红色。
        if (valueBefore >= 90 && valueBefore <= 139) {
            beforeColor = "#DF000000";
        } else if (valueBefore < 90) {
            beforeColor = "#13a3ff";
        } else {
            beforeColor = "#ff3b30";
        }
        //舒张压60-89为正常  舒张压<60时为偏低值，显示为蓝色；>89时为偏高值，显示为红色。
        if (valueAfter >= 60 && valueAfter <= 89) {
            afterColor = "#DF000000";
        } else if (valueAfter < 60) {
            afterColor = "#13a3ff";
        } else {
            afterColor = "#ff3b30";
        }
        return showDiffColorString(valueBefore + "", valueAfter+"",beforeColor,afterColor);
    }

    public Spannable showDiffColorString(String before, String after, String colorBefore, String colorAfter){
        return new HtmlFontUtil().toHtmlFormat(new HtmlFontUtil().getHtmlStr(colorBefore, (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity*12), before)
                + new HtmlFontUtil().getHtmlStr("#DF000000", (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity*12), "/")
                + new HtmlFontUtil().getHtmlStr(colorAfter, (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity*12), after));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 9999){
                setDataStr(itemValue.get(chooseIndex).getDataIndex(), data.getStringExtra("firstColum")+"/"+data.getStringExtra("secondColum"));
                ((DossierHypertensionActivity) getActivity()).updateHealthData(itemValue.get(chooseIndex).getId(), "", itemValue.get(chooseIndex).getMonitorId(), itemValue.get(chooseIndex).getMonitorDate(),
                        data1, data2, data3, data4, data5, data6, data7, data8, "", "", "", "", "", "", "");
                itemValue.get(chooseIndex).setDataValue(data.getStringExtra("firstColum")+"/"+data.getStringExtra("secondColum"));
                myAdapter.notifyItemChanged(chooseIndex);
            }
        }
    }



}
