package cn.v1.unionc_user.ui.me.oldregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.SaveOldBaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class OldRegisterActivity1 extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView bakc;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.et_id)
    EditText id;
    @BindView(R.id.et_address)
    EditText address;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @OnClick(R.id.bt_next)
    void next(){
        if(TextUtils.isEmpty(phone.getText().toString())){
            showTost("请输入电话号码");
            return;
        }
        if(TextUtils.isEmpty(name.getText().toString())){
            showTost("请输入姓名");
            return;
        }
        if(TextUtils.isEmpty(id.getText().toString())){
            showTost("请输入身份证号");
            return;
        }
        if(TextUtils.isEmpty(address.getText().toString())){
            showTost("请输入地址");
            return;
        }
        saveoldbase();

    }


    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_old_register1);
        unbinder= ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    protected void saveoldbase() {
        showDialog("请稍侯...");
        ConnectHttp.connect(UnionAPIPackage.saveoldbase(getToken(), name.getText().toString(),phone.getText().toString(),id.getText().toString(),address.getText().toString()), new BaseObserver<SaveOldBaseData>(context) {

            @Override
            public void onResponse(SaveOldBaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    Intent intent=new Intent(OldRegisterActivity1.this,OldRegisterActivity2.class);
                    intent.putExtra("ElderlyUserId",data.getData().getElderlyUserId());
                    startActivity(intent);
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
}
