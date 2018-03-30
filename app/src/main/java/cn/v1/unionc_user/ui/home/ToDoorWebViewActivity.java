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
import android.widget.LinearLayout;
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
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.HomeSongYaoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.URLEncoderURI;

import static android.content.ContentValues.TAG;

public class ToDoorWebViewActivity extends BaseActivity {

    @Bind(R.id.webview_search)
    cn.v1.unionc_user.view.X5WebView webviewSearch;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toplayout)
    LinearLayout toplayout;

    int type = 0;
    String activityId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_x5_web_view);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }

        if (getIntent().hasExtra("activityid")) {
            activityId = getIntent().getStringExtra("activityid");
        }
    }

    private void initView() {
        webviewSearch.addJavascriptInterface(new JsInteration(), "android");
        webviewSearch.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (TextUtils.isEmpty(activityId) | null == activityId) {

                    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
                    webviewSearch.loadUrl("javascript:app('" + token + "," + (String) SPUtil.get(context, Common.LATITUDE, "") + "," + (String) SPUtil.get(context, Common.LONGITUDE, "") + "," + activityId + "')");
                    Log.d("linshi", "load:" + token + "," + (String) SPUtil.get(context, Common.LATITUDE, "") + "," + (String) SPUtil.get(context, Common.LONGITUDE, "") + "," + activityId);
                }
            }
        });
        switch (type) {
            case 1:
                //医护上门
                initYiHu();
                break;
            case 2:
                //送药上门
                initSongYao();
                break;
            case 3:
                //活动详情
                toplayout.setVisibility(View.GONE);
                initactivityInfo();
                break;
        }


//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data={token:\"YAMLT6U6eYTmoBDjRvnbLg==\"}";
//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data=%7btoken%3a%22YAMLT6U6eYTmoBDjRvnbLg%3d%3d%22%7d";
//        String url="http://192.168.21.160:8082/yihudaojia/intoYihuPage?partner=yibashi&phone=16601139826&thirdPartId=ybs_9&sign=4A6F3A8DAECE5390FB16E587CDD807B9";
//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data={token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\"}";
//        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage";

//        String urlStr;
//        try {
//            urlStr = URLEncoderURI.encode(url, "UTF-8");
//            webviewSearch.loadUrl(urlStr);
//            Log.d("linshi","urlStr:"+urlStr);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//                try {
//            urlStr = URLEncoderURI.encode("{token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\"}", "UTF-8");
////            webviewSearch.loadUrl(urlStr);
////                    Log.d("linshi","urlStr:"+urlStr);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//        webviewSearch.postUrl(url,("data="+"{token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\"}").getBytes());
//        Log.d("linshi","url:"+url+"data="+"{token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\"}");
//        webviewSearch.loadUrl(url);
//        Log.d("linshi","url:"+url);
    }


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private void initYiHu() {
        tvTitle.setText("医护上门");
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getsongyao(token), new BaseObserver<HomeSongYaoData>(context) {
            @Override
            public void onResponse(HomeSongYaoData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    webviewSearch.loadUrl(data.getData().getRedirectUrl());
                    Log.d("linshi", "songyao:" + data.getData().getRedirectUrl());
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    private void initSongYao() {
        tvTitle.setText("送药上门");
    }

    private void initactivityInfo() {
        String url = "https://192.168.21.93:8085/unionApp/pages/index.html#/clinicDetails";
        webviewSearch.loadUrl(url);
    }

    public class JsInteration {
        @JavascriptInterface
        public void back() {
            finish();
        }

        @JavascriptInterface
        public void doctor(String doctorID) {
            Log.d("linshi", "JavascriptInterface.doctorId:" + doctorID);
            if (!TextUtils.isEmpty(doctorID) && doctorID != null) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("doctorId", doctorID);
                context.startActivity(intent);
            }

        }

        @JavascriptInterface
        public void call(String num) {
            Log.d("linshi", "JavascriptInterface.num:" + num);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uriData = Uri.parse("tel:" + num);
            intent.setData(uriData);
            startActivity(intent);

        }
    }
}
