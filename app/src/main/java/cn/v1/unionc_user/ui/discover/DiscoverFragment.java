package cn.v1.unionc_user.ui.discover;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.Position;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.utils.NetWorkUtils;

import static android.content.ContentValues.TAG;
import static cn.v1.unionc_user.data.Common.APP_CACAHE_DIRNAME;
import static cn.v1.unionc_user.data.Common.QM_SET;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BaseFragment {


    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.tv_title)
    TextView tvtitle;
    private String provider;//位置提供器
    double latitude;
    double longitude;
    Position position=new Position();

    private ProgressDialog progressdialog;

    public DiscoverFragment() {
        // Required empty public constructor
    }


        private final String url = "https://192.168.21.93:8085/unionApp/page/index.html";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDialog("加载中...");
        //测试用
        tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                webview.loadUrl("javascript:app(\"aaaaaaa\")");
                    webview.loadUrl("javascript:app('"+latitude+","+longitude+"')");
            }
        });
        init();
        webViewConfigure();
    }

    private void init() {
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: 没有权限 ");
            return;
        }else{
            LocationManager locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            provider = judgeProvider(locationManager);
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude= location.getLongitude();
                position.setLat(latitude);
                position.setLng(longitude);
                Log.d("linshi",latitude+","+longitude);
            } else {
                Toast.makeText(context,"暂时无法获得当前位置",Toast.LENGTH_SHORT).show();
            }
        }


    }

    /**
     * 判断是否有可用的内容提供器
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            return LocationManager.NETWORK_PROVIDER;
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }else{
            Toast.makeText(context,"没有可用的位置提供器",Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    /**
     * 配置webview
     */
    private void webViewConfigure() {
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    closeDialog();
                }
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webview.loadUrl("javascript:app('"+latitude+","+longitude+"')");
                Log.d("linshi","load:"+latitude+","+longitude);
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
            webview.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        WebSettings webSettings = webview.getSettings();
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        webview.requestFocusFromTouch();
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

//        //启用数据库
//        webSettings.setDatabaseEnabled(true);
//        String dir = context.getDir("database", Context.MODE_PRIVATE).getPath();
//       //启用地理定位
//        webSettings.setGeolocationEnabled(true);
//       //设置定位的数据库路径
//        webSettings.setGeolocationDatabasePath(dir);
//       //最重要的方法，一定要设置，这就是出不来的主要原因
//       webSettings.setDomStorageEnabled(true);

        webview.loadUrl(url);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (webview != null) {
            webview.clearHistory();
            webview.destroy();
            webview = null;
        }
    }
}
