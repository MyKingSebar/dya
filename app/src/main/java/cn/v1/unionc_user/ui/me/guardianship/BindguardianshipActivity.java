package cn.v1.unionc_user.ui.me.guardianship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.BindSuccessReturnEventData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class BindguardianshipActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

//    @OnClick(R.id.tv_bind)
//    void bind() {
//        if (TextUtils.isEmpty(ralationText)) {
//            showTost("请选择关系");
//            return;
//        }
//        showDialog("加载中...");
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        ConnectHttp.connect(UnionAPIPackage.SaveGuardianship(token, elderlyUserId, ralationText), new BaseObserver<BaseData>(context) {
//            @Override
//            public void onResponse(BaseData data) {
//                closeDialog();
//                if (TextUtils.equals("4000", data.getCode())) {
//                    showTost("绑定成功");
//                    finish();
//                    BindSuccessReturnEventData eventData = new BindSuccessReturnEventData();
//                    BusProvider.getInstance().post(eventData);
//                } else {
//                    showTost(data.getMessage() + "");
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                closeDialog();
//            }
//        });
//    }
@OnClick(R.id.tv_bind)
    void bind2() {
        if (TextUtils.isEmpty(ralationText)) {
            showTost("请选择关系");
            return;
        }
        showDialog("加载中...");

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.BindGuardianship(token, elderlyUserId, ralationText), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("绑定成功");
                    finish();
                    BindSuccessReturnEventData eventData = new BindSuccessReturnEventData();
                    BusProvider.getInstance().post(eventData);
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindViews({R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6})
    CheckBox[] cb;

//    @BindView(R.id.cb2)
//    CheckBox cb2;
//    @BindView(R.id.cb3)
//    CheckBox cb3;
//    @BindView(R.id.cb4)
//    CheckBox cb4;
//    @BindView(R.id.cb5)
//    CheckBox cb5;
//    @BindView(R.id.cb6)
//    CheckBox cb6;

    private String elderlyUserId;
    private String ralationText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship_bindguardianship);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if (getIntent().hasExtra("elderlyUserId")) {
            elderlyUserId = getIntent().getStringExtra("elderlyUserId");
        }
    }

    private void initView() {
        for (int i = 0; i < cb.length; i++) {
            final int fi = i;
            cb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (int i = 0; i < cb.length; i++) {
                            if (i != fi) {
                                cb[i].setChecked(false);
                            }
                        }
                        ralationText = cb[fi].getText().toString();
                    }
                }
            });
        }


    }

}
