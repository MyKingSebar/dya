package cn.v1.unionc_user.ui.home.BloodPressure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.BannerView;

public class OMRONBannerActivity extends BaseActivity {

    @BindView(R.id.bannerview)
    BannerView bannerview;
    @BindView(R.id.tv_buy_omron)
    TextView tvBuyOmron;
    @BindView(R.id.tv_bind_omron)
    TextView tvBindOmron;


    private List<View> viewList = new ArrayList<>();
    private int[] omronImge = new int[]{R.drawable.img_omron_1, R.drawable.img_omron_2,
            R.drawable.img_omron_3, R.drawable.img_omron_4};
    private String[] omronContent = new String[]{"欧姆龙HEM-9200TK", "高清大屏，视力不好的老人也可以轻松读取",
            "智能加压，运用欧姆龙核心感应技术缩减测量时间", "可通过不规则脉波提前预知心律不齐"};
    private String deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omronbanner);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.tv_buy_omron, R.id.tv_bind_omron})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_buy_omron:
//                Intent intentMall = new Intent(this, MallWebActivity.class);
//                intentMall.putExtra("h5_url","http://h5.yihu365.com/page/mall/goodDetails.jsp?id=10004");
//                startActivity(intentMall);
                break;
            case R.id.tv_bind_omron:
                Intent intent = new Intent(this, BindOMRONStepActivity.class);
                intent.putExtra("deviceName",deviceName);
                startActivity(intent);
                break;
        }
    }


    private void initData() {
        if(getIntent().hasExtra("deviceName")){
            deviceName = getIntent().getStringExtra("deviceName");
        }
        deviceName="血压仪";
    }

    private void initView() {

        setTitle(deviceName);

        for (int i = 0; i < omronImge.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER);
            image.setImageResource(omronImge[i]);
            viewList.add(image);
        }
        bannerview.setViewList(this, viewList);
        bannerview.startLoop(true);
    }

}
