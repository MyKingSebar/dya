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
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_x5_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("医护上门");
        showDialog("加载中...");
        showDialog("加载中...");
        String token=(String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getsongyao(token), new BaseObserver<HomeSongYaoData>(context) {
            @Override
            public void onResponse(HomeSongYaoData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    webviewSearch.loadUrl(data.getData().getRedirectUrl());
                    Log.d("linshi","songyao:"+data.getData().getRedirectUrl());
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
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
}
