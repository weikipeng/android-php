package com.app.NAMESPACE.auth;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.app.AppBlogs;
import com.app.NAMESPACE.app.AppConfig;
import com.app.NAMESPACE.app.AppIndex;
import com.app.NAMESPACE.app.AppLogin;
import com.app.NAMESPACE.base.BaseApp;
import com.app.NAMESPACE.base.BaseAuth;
import com.app.NAMESPACE.model.Customer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class AuthApp extends BaseApp {
	
	protected static Customer customer = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!BaseAuth.isLogin()) {
			this.forward(AppLogin.class);
			this.onStop();
		} else {
			customer = BaseAuth.getCustomer();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		this.bindMainTop();
		this.bindMainTab();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, R.string.menu_logout).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		menu.add(0, 2, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1: {
				doLogout();
				doFinish();
				break;
			}
			case 2:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.menu_about);
				String appName = this.getString(R.string.app_name);
				String appVersion = this.getString(R.string.app_version);
				builder.setMessage(appName + " " + appVersion);
				builder.setIcon(R.drawable.bomb_w);
				builder.setPositiveButton(R.string.btn_cancel, null);
				builder.show();
				break;
		}
		return super.onOptionsItemSelected(item);
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
		ImageButton bTabHome = (ImageButton) findViewById(R.id.main_tab_home);
		ImageButton bTabBlog = (ImageButton) findViewById(R.id.main_tab_blog);
		ImageButton bTabConf = (ImageButton) findViewById(R.id.main_tab_conf);
		if (bTabHome != null && bTabBlog != null && bTabConf != null) {
			OnClickListener mOnClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.main_tab_home:
							forward(AppIndex.class);
							break;
						case R.id.main_tab_blog:
							forward(AppBlogs.class);
							break;
						case R.id.main_tab_conf:
							forward(AppConfig.class);
							break;
					}
				}
			};
			bTabHome.setOnClickListener(mOnClickListener);
			bTabBlog.setOnClickListener(mOnClickListener);
			bTabConf.setOnClickListener(mOnClickListener);
		}
	}
}