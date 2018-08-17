package cn.v1.unionc_user.ui.me;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.ButtomDialogView;

public class ServerCenterActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_online)
    TextView tvOnline;
    @BindView(R.id.tv_call)
    TextView tvCall;


    private String servicePhone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_servercenter);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_online, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_online:
                break;
            case R.id.tv_call:
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                Uri data = Uri.parse("tel:" + servicePhone);
//                intent.setData(data);
//                startActivity(intent);
                showButtonDialog();
                break;

        }
    }


    private void initView() {
        tvTitle.setText("客服中心");
        servicePhone="4000123789";
    }
    private void showButtonDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        TextView tvCall=contentView.findViewById(R.id.call);
        TextView tvCancel=contentView.findViewById(R.id.cancel);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + servicePhone);
                intent.setData(data);
                startActivity(intent);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
}
