package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.WebClient;


public class TeachWebViewActivity extends BaseActivity {

    @Bind(R.id.webview_search)
    WebView webviewSearch;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private String provider;//位置提供器
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("健康课堂");
        showDialog("加载中...");
        WebClient client = new WebClient();
        webviewSearch.setWebViewClient(client);

        String url="192.168.21.93:8080/unionApp/yh_h5/yhpage?data={token:\""+(String) SPUtil.get(context, Common.USER_TOKEN, "")+"\"}";
        webviewSearch.loadUrl(url);

    }



    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
