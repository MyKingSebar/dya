package cn.v1.unionc_user.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.URLEncoderURI;

import static android.content.ContentValues.TAG;

public class ToDoorWebViewActivity extends BaseActivity {

    @Bind(R.id.webview_search)
    WebView webviewSearch;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("医护上门");
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
//        Log.d("linshi","")
//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data={token:\"YAMLT6U6eYTmoBDjRvnbLg==\"}";
//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data=%7btoken%3a%22YAMLT6U6eYTmoBDjRvnbLg%3d%3d%22%7d";
        String url="http://192.168.21.160:8082/yihudaojia/intoYihuPage?partner=yibashi&phone=16601139826&thirdPartId=ybs_9&sign=4A6F3A8DAECE5390FB16E587CDD807B9";
        webviewSearch.loadUrl(url);
        Log.d("linshi","url:"+url);
        String urlStr;
//        try {
//            urlStr = URLEncoderURI.encode(url, "UTF-8");
//            webviewSearch.loadUrl(urlStr);
//            Log.d("linshi","urlStr:"+urlStr);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }



    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
