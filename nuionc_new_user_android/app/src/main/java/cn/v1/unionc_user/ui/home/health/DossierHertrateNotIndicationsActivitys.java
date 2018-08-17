package cn.v1.unionc_user.ui.home.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.HeartIndicationData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class DossierHertrateNotIndicationsActivitys extends BaseActivity {

    private ImageView img_selceted;
    private boolean isRLNoIndicationSelected = false;
    private RecyclerView notIndicationsRecycleView;
    private List<NoIndicationsModel> mListData = new ArrayList<>();
    private String nodicationId = "";

    @BindView(R.id.labels)
    LabelsView labelsView;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;
    @OnClick(R.id.img_back)
    public void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_hertrate_not_indications22);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        //根据类型查询字典数据（type ：001-医生级别 002-客服电话 003-不适应症 004-心脏病类型）
        ConnectHttp.connect(UnionAPIPackage.getIntelligentHardwareIndication("004"), new BaseObserver<HeartIndicationData>(context) {
            @Override
            public void onResponse(HeartIndicationData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    if (data.getData().getBasicDicts().size() > 0) {
                        mListData.clear();
                        for (HeartIndicationData.DataData.HeartIndicationDataData mData : data.getData().getBasicDicts()) {
                                mListData.add(new NoIndicationsModel(mData.getBasicName(), false,mData.getBasicCode()));
                        }
                        Log.d("linshi","mListData:"+mListData.size());
                        labelsView.setLabels(mListData, new LabelsView.LabelTextProvider<NoIndicationsModel>() {
                            @Override
                            public CharSequence getLabelText(TextView label, int position, NoIndicationsModel data) {
                                //根据data和position返回label需要显示的数据。
                                return data.getTitle();
                            }
                        });
                    } else {
                        showTost(data.getMessage());
                    }
                }
            }

                @Override
                public void onFail (Throwable e){
                    closeDialog();
                }
            });
        }

        private void doAfterReceiveData () {
            //上个页面数据
            String noIndication = getIntent().getStringExtra("noIndication");
            if (TextUtils.isEmpty(noIndication)) {
            } else if (noIndication.contains("无症状")) {
                isRLNoIndicationSelected = true;
                img_selceted.setImageResource(R.drawable.icon_choice);
            } else {
                String[] familyHistoryArray = noIndication.split(",");
                for (int i = 0; i < familyHistoryArray.length; i++) {
                    for (int j = 0; j < mListData.size(); j++) {
                        if (TextUtils.equals(familyHistoryArray[i], mListData.get(j).getTitle())) {
                            mListData.get(j).setState(true);
                        }
                    }
                }
            }

        }

        private void initView () {
            tvTitle.setText("心脏病类型");
            findViewById(R.id.title_save_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResultData();
                }
            });

            //标签的选中监听
            labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
                @Override
                public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                    if (mListData.get(position).isState()) {
                        mListData.get(position).setState(false);
                    } else {
                        mListData.get(position).setState(true);
                    }
                }
            });
            labelsView.setLabelSize();

        }

        private void setResultData () {

            String noIndicationData = "";
            String noIndicationDataId = "";
                for (int i = 0; i < mListData.size(); i++) {
                    if (mListData.get(i).isState()) {
                        noIndicationData += "," + mListData.get(i).getTitle();
                        noIndicationDataId += "," + mListData.get(i).getNoDicationId();
                    }
                }
            if (noIndicationData.startsWith(",")) {
                noIndicationData = noIndicationData.substring(1, noIndicationData.length());
                noIndicationDataId = noIndicationDataId.substring(1, noIndicationDataId.length());
                LogUtils.LOGE("截取", noIndicationData);
            }
            Intent intent = new Intent();
            intent.putExtra("firstColum", noIndicationData);
            setResult(RESULT_OK, intent);
            finish();
        }


        @Override
        public void onBackPressed () {
            super.onBackPressed();
        }



        private class NoIndicationsModel {
            private String title;
            private boolean state;
            private String noDicationId;

            public NoIndicationsModel(String title, boolean state, String noDicationId) {
                this.title = title;
                this.state = state;
                this.noDicationId = noDicationId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public boolean isState() {
                return state;
            }

            public void setState(boolean state) {
                this.state = state;
            }

            public String getNoDicationId() {
                return noDicationId;
            }

            public void setNoDicationId(String noDicationId) {
                this.noDicationId = noDicationId;
            }
        }
    }

