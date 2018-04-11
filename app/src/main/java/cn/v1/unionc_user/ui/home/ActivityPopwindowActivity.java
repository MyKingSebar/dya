package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ActivityPopwindowActivity extends BaseActivity {

    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.slider)
    SliderLayout mDemoSlider;
    @BindView(R.id.custom_indicator2)
    PagerIndicator pagerIndicator;

private  List<HomeListData.DataData.HomeData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow_activity);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @OnClick({R.id.img_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("list")) {
            data = (List<HomeListData.DataData.HomeData>) getIntent().getSerializableExtra("list");//获取list方式
        }
    }

    private void initView() {
        if(data==null||data.size()==0){
            return;
        }
        for(HomeListData.DataData.HomeData homedata : data){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(homedata.getImagePath2())//image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                    .setOnSliderClickListener(onSliderClickListener);//图片点击
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("activityid",homedata.getActivityId());//传入参数
            mDemoSlider.addSlider(textSliderView);//添加一个滑动页面
        }

        mDemoSlider.setCustomIndicator(pagerIndicator);//自定义指示器
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        Log.d("linshi","data:::"+data.size());
        if(data.size()>1){

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);//滑动动画
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
            mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
        }else {
            mDemoSlider.stopAutoCycle();
        }
        mDemoSlider.addOnPageChangeListener(onPageChangeListener);

    }


    private BaseSliderView.OnSliderClickListener onSliderClickListener=new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {
            Intent intent = new Intent(context, ToDoorWebViewActivity.class);
            intent.putExtra("type", 3);
            intent.putExtra("activityid", slider.getBundle().get("activity")+"");
            Log.d("linshi","onSliderClickListener.activityid:"+slider.getBundle().get("activity"));
            context.startActivity(intent);
        }
    };
    //页面改变监听
    private ViewPagerEx.OnPageChangeListener onPageChangeListener=new ViewPagerEx.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            Log.d("linshi", "Page Changed: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

}
