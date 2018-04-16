package cn.v1.unionc_user.ui.home.health;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class DossierHeartRateECGPhotoActivity extends BaseActivity {

    @BindView(R.id.photoview)
    PhotoView photoview;
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
        setContentView(R.layout.activity_dossier_hert_rate_ecgphoto);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        tvTitle.setText("心电图");
        String fileName = getIntent().getStringExtra("pngFileName");
        if(fileName.startsWith("http")){
            GlideUtil.setNormalImage(this,fileName,photoview,0,0);
//            Glide.with(this)
//                    .load(fileName)
//                    .priority(Priority.HIGH)
//                    .into(photoview);
        }else{
            Glide.with(this)
                    .load("file://"+fileName)
                    .priority(Priority.HIGH)
                    .into(photoview);
        }

    }
}
