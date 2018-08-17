package cn.v1.unionc_user.ui;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

import static cn.v1.unionc_user.network_frame.UnioncURL.Unionc_WEB_Host;

public class LoginAgreementActivity extends BaseActivity {

    @BindView(R.id.webview_login_agreement)
    WebView webviewLoginAgreement;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_agreement);
        unbinder=ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("用户服务协议");
        showDialog("加载中...");
        webviewLoginAgreement.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    closeDialog();
                }
            }
        });
        webviewLoginAgreement.setWebViewClient(new WebViewClient() {

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
            webviewLoginAgreement.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        WebSettings webSettings = webviewLoginAgreement.getSettings();
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        webviewLoginAgreement.requestFocusFromTouch();
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
        webviewLoginAgreement.loadUrl(Unionc_WEB_Host+"pages/index.html#/agreement");

    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
