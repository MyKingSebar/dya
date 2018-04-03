package cn.v1.unionc_user.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.network_frame.UnioncURL;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.URLEncoderURI;

import static android.content.ContentValues.TAG;

public class MapClinicWebViewActivity extends BaseActivity {

    @Bind(R.id.webview_search)
    WebView webviewSearch;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toplayout)
    RelativeLayout rl;

    private String provider;//位置提供器
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_web_view);
        ButterKnife.bind(this);
//        init();
        initView();
    }

    private void initView() {
        rl.setVisibility(View.GONE);
        tvTitle.setText("搜索周边");
        showDialog("加载中...");
        webviewSearch.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    closeDialog();
                }
            }
        });
        webviewSearch.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String token=(String) SPUtil.get(context, Common.USER_TOKEN, "");
                webviewSearch.loadUrl("javascript:app('"+token+","+latitude+","+longitude+"')");
                Log.d("linshi","load:"+token+","+latitude+","+longitude);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//设置支持https
            }
        });
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webviewSearch.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        WebSettings webSettings = webviewSearch.getSettings();
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        webviewSearch.requestFocusFromTouch();
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        String url="https://192.168.21.93:8085/unionApp/pages/index.html#/search?data{token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+","+(String) SPUtil.get(context, Common.LONGITUDE, "")+","+(String) SPUtil.get(context, Common.LATITUDE, "")+"\"}";
//        String url="https://192.168.21.93:8085/unionApp/pages/index.html#/search?data="+(String) SPUtil.get(context, Common.USER_TOKEN, "")+","+(String) SPUtil.get(context, Common.LONGITUDE, "")+","+(String) SPUtil.get(context, Common.LATITUDE, "");
//        String url="https://192.168.21.93:8085/unionApp/pages/index.html#/search?data={token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\",a:\""+(String) SPUtil.get(context, Common.LONGITUDE, "")+"\",b:\""+(String) SPUtil.get(context, Common.LATITUDE, "")+"\"}&encryption=false";
        String token=(String) SPUtil.get(context, Common.USER_TOKEN, "");
        try {
            String query = URLEncoder.encode(token, "utf-8");
            String url= UnioncURL.Unionc_WEB_Host+"pages/index.html#/search?"+query+","+(String) SPUtil.get(context, Common.LONGITUDE, "")+","+(String) SPUtil.get(context, Common.LATITUDE, "");
            webviewSearch.addJavascriptInterface(new JsInteration(), "android");
            Log.d("linshi", "url" + url);
            webviewSearch.loadUrl(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String urlStr;
//        try {
//            urlStr = URLEncoderURI.encode(url, "UTF-8");
//            webviewSearch.loadUrl(urlStr);
//            Log.d("linshi","url:"+url);
//            Log.d("linshi","urlStr:"+urlStr);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

//    private void init() {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "onCreate: 没有权限 ");
//            return;
//        }else{
//            LocationManager locationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            provider = judgeProvider(locationManager);
//            Location location = locationManager.getLastKnownLocation(provider);
//            if (location != null) {
//                latitude = location.getLatitude();
//                longitude= location.getLongitude();
//                Log.d("linshi",latitude+","+longitude);
//            } else {
//                Toast.makeText(context,"暂时无法获得当前位置",Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }

//    /**
//     * 判断是否有可用的内容提供器
//     * @return 不存在返回null
//     */
//    private String judgeProvider(LocationManager locationManager) {
//        List<String> prodiverlist = locationManager.getProviders(true);
//        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
//            return LocationManager.NETWORK_PROVIDER;
//        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
//            return LocationManager.GPS_PROVIDER;
//        }else{
//            Toast.makeText(context,"没有可用的位置提供器",Toast.LENGTH_SHORT).show();
//        }
//        return null;
//    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    public class JsInteration {
        @JavascriptInterface
        public void back() {
            finish();
        }
        @JavascriptInterface
        public void doctor(String doctorID) {
            Log.d("linshi","JavascriptInterface.doctorId:"+doctorID);
            if(!TextUtils.isEmpty(doctorID)&&doctorID!=null){
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("doctorId", doctorID);
                context.startActivity(intent);
            }

        }
        @JavascriptInterface
        public void clinic(String clinicId) {
            Log.d("linshi","JavascriptInterface.clinicID:"+clinicId);
            if(!TextUtils.isEmpty(clinicId)&&clinicId!=null){
                Intent intent = new Intent(context, HospitalDetailActivity.class);
                intent.putExtra("clinicId", clinicId);
                context.startActivity(intent);
            }

        }
        @JavascriptInterface
        public void call(String num) {
            Log.d("linshi","JavascriptInterface.num:"+num);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uriData = Uri.parse("tel:" + num);
            intent.setData(uriData);
            startActivity(intent);

        }
    }
}
