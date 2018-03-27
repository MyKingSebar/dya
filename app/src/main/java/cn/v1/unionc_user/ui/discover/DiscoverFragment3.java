package cn.v1.unionc_user.ui.discover;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.ClinicInfoData;
import cn.v1.unionc_user.model.MapClinicData;
import cn.v1.unionc_user.model.Position;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.ui.home.SearchWebViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment3 extends BaseFragment implements LocationSource,
        AMapLocationListener {


    @Bind(R.id.map)
    MapView mapView;
    @Bind(R.id.tv_title)
    TextView tvtitle;

    @Bind(R.id.rel_activity_home_search)
    RelativeLayout homeSearch;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.webview)
    WebView webView;

    @Bind(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.right)
    RelativeLayout relativeLayoutRight;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.ll2)
    LinearLayout ll2;
    @Bind(R.id.ll3)
    LinearLayout ll3;
    @Bind(R.id.ll4)
    LinearLayout ll4;
    @Bind(R.id.ll5)
    LinearLayout ll5;
    @Bind(R.id.im1)
    ImageView im1;
    @Bind(R.id.im2)
    ImageView im2;
    @Bind(R.id.im3)
    ImageView im3;
    @Bind(R.id.im4)
    ImageView im4;
    @Bind(R.id.im5)
    ImageView im5;
    @Bind(R.id.cb1)
    CheckBox cb1;
    @Bind(R.id.cb2)
    CheckBox cb2;
    @Bind(R.id.cb3)
    CheckBox cb3;
    @Bind(R.id.cb4)
    CheckBox cb4;
    @Bind(R.id.cb5)
    CheckBox cb5;

    private String type;
    private List<MapClinicData.DataData.MapClinic> mapCliniclist;

    private Marker locationMarker; // 选择的点

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;
    private String provider;//位置提供器
    private Position position = new Position();
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private Circle circle;
    private UiSettings mUiSettings;
    private Marker marker = null;

    private List<Marker> markerList = new ArrayList<>();


    private String keyWord = "";
    private String cb1text = "";
    private String cb2text = "";
    private String cb3text = "";
    private String cb4text = "";
    private String cb5text = "";

    boolean isFirst = true;

    private ProgressDialog progressdialog;

    public DiscoverFragment3() {
        // Required empty public constructor
    }


    private final String url = "https://192.168.21.93:8085/unionApp/page/index.html";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        showDialog("加载中...");
        initmap(savedInstanceState);

        init();
    }

    private void initmap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        //初始化定位
        mlocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mlocationClient.setLocationListener(this);
        if (aMap == null) {

            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            setupmap();
        }

    }


    private void setupmap() {


        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_now)));
        aMap.setLocationSource(this);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        aMap.setLocationSource(this);// 设置定位监听
        mUiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
        mUiSettings.setZoomControlsEnabled(false);


        setUpMap();

    }

//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

    private void init() {

        homeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNewActivity(SearchWebViewActivity.class);
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camerafrash();
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()) {
                    im1.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_off_1));
                    cb1text = "";
                    cb1.setChecked(false);
                } else {
                    im1.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_no_1));
                    cb1text = "综合医院";
                    cb1.setChecked(true);
                }
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb2.isChecked()) {
                    im2.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_off_2));
                    cb2text = "";
                    cb2.setChecked(false);
                } else {
                    im2.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_no_2));
                    cb2text = "专科医院";
                    cb2.setChecked(true);
                }
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb3.isChecked()) {
                    im3.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_off_3));
                    cb3text = "";
                    cb3.setChecked(false);
                } else {
                    im3.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_no_3));
                    cb3text = "民营诊所";
                    cb3.setChecked(true);
                }
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb4.isChecked()) {
                    im4.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_off_4));
                    cb4text = "";
                    cb4.setChecked(false);
                } else {
                    im4.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_no_4));
                    cb4text = "动物医疗场所";
                    cb4.setChecked(true);
                }
            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb5.isChecked()) {
                    im5.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_off_5));
                    cb5text = "";
                    cb5.setChecked(false);
                } else {
                    im5.setImageDrawable(getResources().getDrawable(R.drawable.icon_choose_no_5));
                    cb5text = "社区卫生服务站";
                    cb5.setChecked(true);
                }
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //失去焦点
                showDialog("加载中");
                ConnectHttp.connect(UnionAPIPackage.getMapClinic(getTypeString(),
                        (String) SPUtil.get(context, Common.LONGITUDE, ""),
                        (String) SPUtil.get(context, Common.LATITUDE, "")),
                        new BaseObserver<MapClinicData>(context) {
                            @Override
                            public void onResponse(MapClinicData data) {
                                closeDialog();
                                if (TextUtils.equals("4000", data.getCode())) {
                                    if (markerList.size() > 0) {
                                        for (Marker marker : markerList) {
                                            if (marker != null) {
                                                marker.remove();
                                            }
                                        }
                                        markerList.clear();
                                    }
                                    mapCliniclist = data.getData().getClinicDatas();
                                    if (mapCliniclist.size() > 0) {
                                        for (int i = 0; i < mapCliniclist.size(); i++) {
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(new LatLng(Double.parseDouble(mapCliniclist.get(i).getLatitude()), Double.parseDouble(mapCliniclist.get(i).getLongitude())));
                                            markerOptions.title(mapCliniclist.get(i).getName());
                                            markerOptions.visible(true);
                                            BitmapDescriptor bitmapDescriptor;
                                            View view;
                                            view= View.inflate(getActivity(),R.layout.map_maker_drawable, null);
                                            TextView tv_price = (TextView) view.findViewById(R.id.marker);
                                            tv_price.setText(mapCliniclist.get(i).getName());
                                            Drawable drawable=null;
                                            switch (Integer.parseInt(mapCliniclist.get(i).getParCategory())) {
                                                case 1:
//                                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clinic1));
//                                                    markerOptions.icon(bitmapDescriptor);
//                                                    tv_price.setCompoundDrawables(null, ContextCompat.getDrawable(context,R.drawable.clinic1),null,null);
                                                    drawable=ContextCompat.getDrawable(context,R.drawable.clinic1);
                                                    break;
                                                case 2:
//                                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clinic2));
//                                                    markerOptions.icon(bitmapDescriptor);


//                                                    tv_price.setCompoundDrawables(null, ContextCompat.getDrawable(context,R.drawable.clinic2),null,null);

                                                    drawable=ContextCompat.getDrawable(context,R.drawable.clinic2);
//                                                    marker.setIcon(BitmapDescriptorFactory.fromView(view));
//                                                    Bitmap bitmap = CommentActivity.convertViewToBitmap(view);
//
//                                                    drawMarkerOnMap(new LatLng(Double.parseDouble(positionEneityList.get(i).getLatitude())
//
//                                                            , Double.parseDouble(positionEneityList.get(i).getLongitude())), bitmap, positionEneityList.get(i).getId());
                                                    break;
                                                case 3:
//                                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clinic3));
//                                                    markerOptions.icon(bitmapDescriptor);
                                                    drawable=ContextCompat.getDrawable(context,R.drawable.clinic3);
                                                    break;
                                                case 4:
//                                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clinic4));
//                                                    markerOptions.icon(bitmapDescriptor);
//                                                    tv_price.setCompoundDrawables(null, ContextCompat.getDrawable(context,R.drawable.clinic4),null,null);
                                                    drawable=ContextCompat.getDrawable(context,R.drawable.clinic4);
                                                    break;
                                                case 5:
//                                                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clinic5));
//                                                    markerOptions.icon(bitmapDescriptor);
//                                                    tv_price.setCompoundDrawables(null, ContextCompat.getDrawable(context,R.drawable.clinic5),null,null);
                                                    drawable=ContextCompat.getDrawable(context,R.drawable.clinic5);
                                                    break;


                                            }
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                                            tv_price.setCompoundDrawables(null, drawable,null,null);
                                            Marker marker = aMap.addMarker(markerOptions);

                                                marker.setIcon(BitmapDescriptorFactory.fromView(view));
                                            markerList.add(marker);

                                        }
                                    }
                                } else {
                                    showTost(data.getMessage());
                                }
                            }

                            @Override
                            public void onFail(Throwable e) {
                                closeDialog();
                            }
                        });

                refrash();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
//        relativeLayoutRight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    //失去焦点
//                    Log.d("linshi", "失去焦点");
////            keyWord = mSearchText.getText().toString().trim();
////            if ("".equals(keyWord)) {
////
////            } else {
////                doSearchQuery();
////            }
//                }
//            }
//        });


    }
private String getTypeString(){
    type = "";
    if (cb1.isChecked()) {
        type += "1";
    }
    if (cb2.isChecked()) {
        type += "2";
    }
    if (cb3.isChecked()) {
        type += "3";
    }
    if (cb4.isChecked()) {
        type += "4";
    }
    if (cb5.isChecked()) {
        type += "5";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < type.length(); i++) {
        if (i > 0) {
            sb.append(",");
        }
        sb.append(type.charAt(i));
    }
    return sb.toString();
}
    /**
     * 配置定位参数
     */
    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }


    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(context);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private void camerafrash() {
        // 设置当前地图显示为当前位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
    }

    private void refrash() {

        if (marker != null) {
            marker.remove();
        }
        if (circle != null) {
            circle.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lon));
        markerOptions.title("当前位置");
        markerOptions.visible(true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_now));
        markerOptions.icon(bitmapDescriptor);
        marker = aMap.addMarker(markerOptions);

        // 绘制一个圆形
        circle = aMap.addCircle(new CircleOptions().center(new LatLng(lat, lon))
                .radius(1000).strokeColor(R.color.circle)
                .fillColor(R.color.circle).strokeWidth(0));

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息

                lat = amapLocation.getLatitude();
                lon = amapLocation.getLongitude();
                Log.v("pcw", "lat : " + lat + " lon : " + lon);
                IsFirst();
                refrash();


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void IsFirst() {

        if (isFirst) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
            isFirst = false;
        }
    }
}

