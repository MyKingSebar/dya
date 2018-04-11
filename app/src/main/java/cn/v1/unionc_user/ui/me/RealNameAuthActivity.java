package cn.v1.unionc_user.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.TimePicker;

public class RealNameAuthActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.cb_maile)
    CheckBox cbMaile;
    @BindView(R.id.cb_female)
    CheckBox cbFemale;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_age)
    TextView tvAge;

    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_auth);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_confirm, R.id.tv_age})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_confirm:
                certification();
                break;
            case R.id.tv_age:
                showSelectTime();
                break;
        }
    }


    private void showSelectTime() {
        new TimePicker(context, TimePicker.YEAR_MONTH_DATE, TimePicker.SHIELD_AFTER) {
            @Override
            public void onTimeSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectDate = df.format(new Date());
                tvAge.setText(selectDate + "");
            }
        };
    }


    private void initView() {
        etRealName.setText((String) SPUtil.get(context, "realName", ""));
        String sex = (String) SPUtil.get(context, "gender", "");
        //性别（0：男 1：女）
        if (TextUtils.equals("0", sex)) {
            cbMaile.setChecked(true);
        }
        if (TextUtils.equals("1", sex)) {
            cbFemale.setChecked(true);
        }
        tvAge.setText((String) SPUtil.get(context, "birthDay", ""));
        etPhone.setText((String) SPUtil.get(context, Common.USER_PHONE, ""));
        String updatePhone = (String) SPUtil.get(context, "telephone", "");
        if (!TextUtils.isEmpty(updatePhone)) {
            etPhone.setText(updatePhone);
        }

        cbMaile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMaile.setClickable(false);
                    cbFemale.setChecked(false);
                } else {
                    cbMaile.setClickable(true);
                }
            }
        });
        cbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbFemale.setClickable(false);
                    cbMaile.setChecked(false);
                } else {
                    cbFemale.setClickable(true);
                }
            }
        });

        etRealName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
    }


    /**
     * 提交实名认证
     */
    private void certification() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        final String realName = etRealName.getText().toString().trim();
        String gender = "";
        if (cbMaile.isChecked()) {
            gender = "0";
        }
        if (cbFemale.isChecked()) {
            gender = "1";
        }
        final String birthDay = tvAge.getText().toString().trim();
        final String telephone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(realName)) {
            showTost("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            showTost("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(birthDay)) {
            showTost("请选择生日");
            return;
        }
        if (TextUtils.isEmpty(telephone)) {
            showTost("请填写电话");
            return;
        }
        showDialog("提交中...");
        final String finalGender = gender;
        ConnectHttp.connect(UnionAPIPackage.certification(token, realName, gender, birthDay, telephone),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            SPUtil.put(context, "realName", realName);
                            SPUtil.put(context, "gender", finalGender);
                            SPUtil.put(context, "birthDay", birthDay);
                            SPUtil.put(context, "telephone", telephone);
                            setResult(Activity.RESULT_OK, new Intent());
                            finish();
                        } else {
                            showTost(data.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });
    }

}
