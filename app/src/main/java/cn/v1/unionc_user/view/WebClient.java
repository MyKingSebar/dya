package cn.v1.unionc_user.view;


import android.graphics.Bitmap;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.util.Stack;

public class WebClient extends WebViewClient {
	/**
	 * 记录URL的栈
	 * 规则:
	 * 1.不可在{@code WebView.onPageFinished();}中开始记录URL;
	 * 2.记录需要屏蔽重定向URL.
	 */
	private final Stack<String> mUrls = new Stack<>();
	/**
	 * 判断页面是否加载完成
	 */
	private boolean mIsLoading;
	private String mUrlBeforeRedirect;
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		if (mIsLoading && mUrls.size() > 0) {
			mUrlBeforeRedirect = mUrls.pop();
		}
		recordUrl(url);
		this.mIsLoading = true;
	}
	/**
	 * 记录非重定向链接, 避免刷新页面造成的重复入栈
	 *
	 * @param url 链接
	 */
	private void recordUrl(String url) {
		//这里还可以根据自身业务来屏蔽一些链接被放入URL栈
		if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastPageUrl())) {
			mUrls.push(url);
		} else if (!TextUtils.isEmpty(mUrlBeforeRedirect)) {
			mUrls.push(mUrlBeforeRedirect);
			mUrlBeforeRedirect = null;
		}
	}
	/**
	 * 获取上一页的链接
	 **/
	private synchronized String getLastPageUrl() {
		return mUrls.size() > 0 ? mUrls.peek() : null;
	}
	/**
	 * 推出上一页链接
	 */
	public String popLastPageUrl() {
		if (mUrls.size() >= 2) {
			mUrls.pop();//pop current page url
			return mUrls.pop();
		}
		return null;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		if (this.mIsLoading || url.startsWith("about:")) {
			this.mIsLoading = false;
		}
	}
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return false;
	}


}
