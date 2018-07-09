package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.HomeToHomeData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.UnioncURL;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ToDoorWebViewActivity extends BaseActivity {

    @BindView(R.id.webview_search)
    cn.v1.unionc_user.view.X5WebView webviewSearch;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.myProgressBar)
     ProgressBar bar;
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
//        Log.d("linshi", "todoor.type:" + type);
//        Log.d("linshi", "todoor.hasExtra:" + getIntent().hasExtra("activityid"));
//        Log.d("linshi", "todoor.hasExtra2:" + getIntent().getStringExtra("activityid"));
        if (getIntent().hasExtra("activityid")) {
            activityId = getIntent().getStringExtra("activityid");
        }
    }

    private void initView() {
        webviewSearch.addJavascriptInterface(new JsInteration(), "android");
        webviewSearch.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
        webviewSearch.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                closeDialog();
//                if (!TextUtils.isEmpty(activityId) && null != activityId) {
//
//                    String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//                    webviewSearch.loadUrl("javascript:app('" + token + "," + (String) SPUtil.get(context, Common.LATITUDE, "") + "," + (String) SPUtil.get(context, Common.LONGITUDE, "") + "," + activityId + "')");
//                    Log.d("linshi", "load:" + token + "," + (String) SPUtil.get(context, Common.LATITUDE, "") + "," + (String) SPUtil.get(context, Common.LONGITUDE, "") + "," + activityId);
//                }
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
                iniactivity();

                break;
            case 4:
                //心电图使用说明
                initHelp();

                break;
            case 5:
                //oml使用说明
                initOMPHelp();

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

    private void iniactivity() {
//        showDialog("加载中...");
        toplayout.setVisibility(View.GONE);
        initactivityInfo();
    }


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private void initYiHu() {
        tvTitle.setText("医护上门");
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getyihu(token), new BaseObserver<HomeToHomeData>(context) {
            @Override
            public void onResponse(HomeToHomeData data) {
                String url = data.getData().getRedirectUrl();
                webviewSearch.loadUrl(url);

            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    private void initHelp() {
        toplayout.setVisibility(View.GONE);
        String url = UnioncURL.Unionc_WEB_Host + "pages/index.html#/help" ;
        Log.d("linshi", "url" + url);
        webviewSearch.loadUrl(url);

    }
    private void initOMPHelp() {
        tvTitle.setText("使用说明");
//        toplayout.setVisibility(View.GONE);
        String url = "http://m.yihu365.com/hzb/message/oumul.shtml" ;
        Log.d("linshi", "url" + url);
        webviewSearch.loadUrl(url);

    }
    private void initSongYao() {
            tvTitle.setText("送药上门");
//            showDialog("加载中...");
            String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        Log.d("linshi","送药上门:"+token);
        ConnectHttp.connect(UnionAPIPackage.getsongyao(token), new BaseObserver<HomeToHomeData>(context) {
                @Override
                public void onResponse(HomeToHomeData data) {
                    String url = data.getData().getRedirectUrl();
                    webviewSearch.loadUrl(url);

                }

                @Override
                public void onFail(Throwable e) {
                    closeDialog();
                }
            });

    }

    private void initactivityInfo() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        try {
            String query = URLEncoder.encode(token, "utf-8");
            String url = UnioncURL.Unionc_WEB_Host + "pages/index.html#/clinicDetails?" + query + "," + (String) SPUtil.get(context, Common.LATITUDE, "") + "," + (String) SPUtil.get(context, Common.LONGITUDE, "") + "," + activityId;
            Log.d("linshi", "url" + url);
            webviewSearch.loadUrl(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


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

        @JavascriptInterface
        public void login() {
            Log.d("linshi", "JavascriptInterface.login:" );
            goNewActivity(LoginActivity.class);
            finish();

        }
    }


}
