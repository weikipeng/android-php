package com.app.NAMESPACE.base;

import com.app.NAMESPACE.app.AppIndex;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

abstract public class BaseWebApp extends BaseApp {
	
	private static final int MAX_PROGRESS = 100;
	private static final int DIALOG_PROGRESS = 1;
	
	private WebView webView;
	private int mProgress = 0;
	private ProgressDialog mProgressDialog;
	
	public WebView getWebView () {
		return this.webView;
	}
	
	public void setWebView (WebView webView) {
		this.webView = webView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// before webview loaded
		showDialog(DIALOG_PROGRESS);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
	}
	
	public void startWebview() {
		// start loading webview
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int progress){
				mProgress = progress;
				mProgressDialog.setProgress(mProgress);
				if (mProgress >= MAX_PROGRESS) {
					mProgressDialog.dismiss();
				}
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		switch (id) {
			case DIALOG_PROGRESS:
				mProgressDialog = new ProgressDialog(this);
				mProgressDialog.setTitle("Loading ...");
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setMax(MAX_PROGRESS);
				return mProgressDialog;
		}
		return null;
	}
	
	@Override
	protected void onPause() {
		webView.stopLoading();
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			forward(AppIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
}
