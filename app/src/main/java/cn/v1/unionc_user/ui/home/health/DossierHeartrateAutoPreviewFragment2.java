//package cn.v1.unionc_user.ui.home.health;
//
//
//import android.app.Dialog;
//import android.bluetooth.BluetoothAdapter;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.mhealth365.osdk.EcgOpenApiHelper;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//import cn.v1.unionc_user.R;
//import cn.v1.unionc_user.ui.base.BaseFragment;
//import cn.v1.unionc_user.ui.home.health.autoscrollviewpager.AutoScrollViewPager;
//
//
///**
// * 心率自动测量预览页面
// */
//public class DossierHeartrateAutoPreviewFragment2 extends BaseFragment {
//    private Unbinder unbinder;
//    //banner
//    @BindView(R.id.autoViewpager_main)
//    AutoScrollViewPager autoViewpagerMain;
//    //banner的小圆点
//    @BindView(R.id.ll_dots)
//    LinearLayout llDots;
//    //banner布局
//    @BindView(R.id.fl_banner)
//    FrameLayout flBanner;
//    //链接设备
//    @BindView(R.id.tv_link_device)
//    public TextView tvLinkDevice;
//    //蓝牙状态
//    @BindView(R.id.tv_bluetooth_state)
//    public TextView tvBluetoothState;
//    @BindView(R.id.dossierheartrate_connect_tv)
//    public TextView connectTv;
//    @BindView(R.id.dossierheartrate_disconnect_tv)
//    public TextView disConnectTv;
//
//    @OnClick(R.id.dossierheartrate_disconnect_tv)
//    void disConnectDevice(){
//        try {
//            parent.mOsdkHelper.close();
//            parent.mOsdkHelper = EcgOpenApiHelper.getInstance();
//            parent.mOsdkHelper.setDeviceType(EcgOpenApiHelper.DEVICE.CONNECT_TYPE_BLUETOOTH_DUAL);
//            connectTv.setText("请打开手机蓝牙和设备电源");
//            disConnectTv.setVisibility(View.GONE);
//            tvLinkDevice.setText("连接设备");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //连接设备
//    @OnClick(R.id.tv_link_device)
//    public void onClick() {
//        if(tvLinkDevice.getText().toString().equals("连接设备")) {
//            if (Build.VERSION.SDK_INT < 18) {
//              showTost("手机系统版本过低，不支持心电设备");
//                return;
//            }
//            if (isBTConnected()) {
//                Intent intent = new Intent(getActivity(), DossierHeartRateSelectBlueToothActivity.class);
//                getActivity().startActivity(intent);
//            } else {
//                showUploadDialog();
//            }
//        }else if(tvLinkDevice.getText().toString().equals("开始测量")){
//            new HeartRateContinueMeasureDialog(getActivity(), new HeartRateContinueMeasureDialog.MyDilogListener() {
//                @Override
//                public void btnConfirm(Dialog dialog) {
//                    dialog.dismiss();
//                    gotoMeasure(ONE_MINUTE_MEASURE);
//                }
//            });
//        }
//    }
//
//    private List<Integer> bannerImg = new ArrayList<>();
//    private List<ImageView> bannerDot = new ArrayList<>();
//    private static DossierHeartrateAutoPreviewFragment2 fragment;
//
//    public static DossierHeartrateAutoPreviewFragment2 newInstance(String userId, String healthInfoId, String monitorId) {
//
//        Bundle args = new Bundle();
//        args.putString("userId", userId);
//        args.putString("healthInfoId", healthInfoId);
//        args.putString("monitorId", monitorId);
//        if (null == fragment) {
//            fragment = new DossierHeartrateAutoPreviewFragment2();
//        }
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        userId = getArguments().getString("userId");
//        healthInfoId = getArguments().getString("healthInfoId");
//        monitorId = getArguments().getString("monitorId");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_dossier_hertrate_auto_preview, container, false);
//        unbinder=ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        bannerImg.clear();
//        bannerDot.clear();
//        initBanner();
//        initView();
//    }
//
//    private DossierHeartRateAutoActivity parent;
//    private String userId = "";
//    private String monitorId = "";
//    private String healthInfoId = "";
//    public final int ONE_MINUTE_MEASURE = 1;
//    private void initView() {
//        if( isBTConnected()){
//            tvBluetoothState.setText("蓝牙状态:打开");
//        }else{
//            tvBluetoothState.setText("蓝牙状态:关闭");
//        }
//        parent = (DossierHeartRateAutoActivity)getActivity();
//    }
//
//    /**
//     * 判断蓝牙是否打开
//     * @return
//     */
//    public boolean isBTConnected() {
//        boolean BLEState = false;
//        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
//        //adapter也有getState(), 可获取ON/OFF...其它状态
//        int state =  blueadapter.getState();
//        if(state == BluetoothAdapter.STATE_ON){
//            BLEState = true;
//        }
//        if(state == BluetoothAdapter.STATE_OFF){
//            BLEState = false;
//        }
//        return  BLEState;
//    }
//    /**
//     * 打开蓝牙的弹框
//     */
//    private void showUploadDialog() {
//
//        final AlarmDialog dialog = new AlarmDialog(getActivity(), AlarmDialog.CANCELANDOK, new IRespCallBack() {
//            @Override
//            public boolean callback(int callCode, Object... param) {
//                if (callCode == AlarmDialog.ALARMDIALOGOK) {
//
//                }
//                if (callCode == AlarmDialog.ALARMDIALOGCANCEL) {
//                    openSetting();
//                }
//                return true;
//            }
//        }, "", "打开蓝牙来允许“掌上心电”连接到配件");
//        dialog.tvSmallMessage.setVisibility(View.GONE);
//        dialog.setStr_okbtn("好");
//        dialog.setStr_cancelbtn("设置");
//        dialog.show();
//    }
//    /**
//     * 设置蓝牙
//     * @return
//     */
//    private void openSetting(){
//        Intent intent = new Intent();
//        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        try{
//            startActivity(intent);
//        } catch(ActivityNotFoundException ex){
//            ex.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        handler.removeCallbacksAndMessages(null);
//        unbinder.unbind();
//    }
//
//    // 延时轮播
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                autoViewpagerMain.setCurrentItem(autoViewpagerMain.getCurrentItem() + 1, true);
//                super.sendEmptyMessageDelayed(1, 3000);
//            }
//        }
//    };
//
//    /**
//     * 初始化banner
//     */
//    private void initBanner() {
//        bannerImg.add(R.drawable.img_step_one);
//        bannerImg.add(R.drawable.img_step_two);
//        bannerImg.add(R.drawable.img_step_three);
//        //宽高比是1.78(width/height)
//        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        float height = width / 1.13f;//得到对应的高
//        autoViewpagerMain.getLayoutParams().height = (int) height;
//        autoViewpagerMain.requestLayout();
//        // 添加小圆点
//        for (int i = 0; i < bannerImg.size(); i++) {
//            ImageView imgDot = new ImageView(getActivity());
//            LinearLayout.LayoutParams ll_dot_lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            ll_dot_lp.leftMargin = dip2px(9);
//            ll_dot_lp.width = dip2px(7);
//            ll_dot_lp.height = dip2px(7);
//            imgDot.setLayoutParams(ll_dot_lp);
//            if (i == 0) {
//                imgDot.setBackgroundResource(R.drawable.icon_dot_green);
//            } else {
//                imgDot.setBackgroundResource(R.drawable.icon_dot_grey);
//            }
//            bannerDot.add(imgDot);
//            llDots.addView(imgDot);
//        }
//        autoViewpagerMain.setAdapter(new BannerPagerAdapter(bannerImg));
//        // 默认设置中间的某个item
//        autoViewpagerMain.setCurrentItem(bannerImg.size() * 100000);
//        // 延时发送消息
//        handler.sendEmptyMessageDelayed(1, 3000);
//        autoViewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                for (int i = 0; i < bannerDot.size(); i++) {
//                    if (i == position % bannerDot.size()) {
//                        bannerDot.get(i).setBackgroundResource(
//                                R.drawable.icon_dot_green);
//                    } else {
//                        bannerDot.get(i).setBackgroundResource(
//                                R.drawable.icon_dot_grey);
//                    }
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//
//    }
//
//    private class BannerPagerAdapter extends PagerAdapter {
//
//        private List<Integer> list;
//        private DisplayImageOptions options;
//        private ImageLoader imageLoader = ImageLoader.getInstance();
//
//        public BannerPagerAdapter(List<Integer> bannerList) {
//            this.list = bannerList;
//        }
//
//        @Override
//        public int getCount() {
//            return Integer.MAX_VALUE;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object obj) {
//            return view == obj;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);// s设置图片的四周和imageView对齐
//            if (list.size() > 0) {
//                imageView.setImageResource(list.get(position % list.size()));
//            } else {
//                imageView.setImageResource(R.drawable.background_default_bigpic);
//            }
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//    }
//
//    private void gotoMeasure(int measureType){
//        Intent intent = new Intent(getActivity(),DossierHeartRateAutoMeasureActivity.class);
//        intent.putExtra("userId",userId);
//        intent.putExtra("healthInfoId",healthInfoId);
//        intent.putExtra("monitorId",monitorId);
//        intent.putExtra("measureType",measureType);
//        intent.putExtra("ecgSample",parent.ecgSample);
//        startActivity(intent);
//    }
//
//}
