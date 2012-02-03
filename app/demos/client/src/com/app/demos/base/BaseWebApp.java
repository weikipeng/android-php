package com.app.demos.base;

import com.app.demos.R;
import com.app.demos.app.AppBlogs;
import com.app.demos.app.AppConfig;
import com.app.demos.app.AppIndex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.webkit.JsResult;
import android.graphics.Bitmap;

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
	
	public void startWebView() {
		
		this.bindMainTop();
		this.bindMainTab();
		
		// load url link in webview
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
            @Override  
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // call on page started
            	Log.w("BaseWebApp", "onPageStarted");
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // call on page finished
            	Log.w("BaseWebApp", "onPageFinished");
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(BaseWebApp.this, "Get Error : " + description, Toast.LENGTH_SHORT).show();
            }
		});
		// show loading progress bar
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int progress){
				mProgress = progress;
				mProgressDialog.setProgress(mProgress);
				if (mProgress >= MAX_PROGRESS) {
					mProgressDialog.dismiss();
				}
			}
			@Override
            public boolean onJsAlert(WebView view, String url,  
                    String message, final JsResult result) {  
                // replace with android widget
                new AlertDialog.Builder(BaseWebApp.this)
                    .setTitle("Notification")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
                return true;
            }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// before webview loaded
		showDialog(DIALOG_PROGRESS);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
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
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
				return true;
			} else {
				forward(AppIndex.class);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void bindMainTop () {
		Button bTopQuit = (Button) findViewById(R.id.main_top_quit);
		if (bTopQuit != null) {
			OnClickListener mOnClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.main_top_quit:
							doFinish();
							break;
					}
				}
			};
			bTopQuit.setOnClickListener(mOnClickListener);
		}
	}
	
	private void bindMainTab () {
		ImageButton bTabHome = (ImageButton) findViewById(R.id.main_tab_1);
		ImageButton bTabBlog = (ImageButton) findViewById(R.id.main_tab_2);
		ImageButton bTabConf = (ImageButton) findViewById(R.id.main_tab_3);
		ImageButton bTabWrite = (ImageButton) findViewById(R.id.main_tab_4);
		if (bTabHome != null && bTabBlog != null && bTabConf != null) {
			OnClickListener mOnClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.main_tab_1:
							forward(AppIndex.class);
							break;
						case R.id.main_tab_2:
							forward(AppBlogs.class);
							break;
						case R.id.main_tab_3:
							forward(AppConfig.class);
							break;
						case R.id.main_tab_4:
							doEditBlog();
							break;
					}
				}
			};
			bTabHome.setOnClickListener(mOnClickListener);
			bTabBlog.setOnClickListener(mOnClickListener);
			bTabConf.setOnClickListener(mOnClickListener);
			bTabWrite.setOnClickListener(mOnClickListener);
		}
	}
}
