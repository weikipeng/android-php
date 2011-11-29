package com.app.NAMESPACE.demo;

import android.view.KeyEvent;
import android.webkit.WebView;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.app.AppIndex;
import com.app.NAMESPACE.base.BaseWebApp;
import com.app.NAMESPACE.base.C;

public class DemoWeb extends BaseWebApp {
	
	private WebView mWebViewForm;
	
	@Override
	public void onStart() {
		super.onStart();
		
		// start loading webview
		setContentView(R.layout.demo_web);
		mWebViewForm = (WebView) findViewById(R.id.web_form);
		mWebViewForm.getSettings().setJavaScriptEnabled(true);
		mWebViewForm.loadUrl(C.web.index);
		
		this.setWebView(mWebViewForm);
		this.startWebview();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			forward(AppIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	
}