package com.app.plugs.download;

import com.app.demos.download.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DownLoadDemo extends Activity {
	
	private Button btnD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 下载配置
		String downloadUrl = "http://116.211.21.79/files/GamePlus_v1.5.0.4_20120525.apk";
//		String downloadUrl = "http://116.211.21.79/files/GamePlus.apk";
		String saveFileName = "GamePlus.apk";
		final DownloadView dv = new DownloadView(this, downloadUrl, saveFileName);
		dv.setDownloadListener(new DownloadView.DownloadListener(){
			@Override
			public void onComplete(String downloadFile) {
				Log.e("DownloadDemo", downloadFile);
//				dv.install();
			}
			@Override
			public void onError(String errorMsg) {
				Log.e("DownloadDemo", errorMsg);
				dv.halt();
			}
		});
		// 点击下载
		btnD = (Button) findViewById(R.id.btnDownLoad);
		btnD.setText("点击后下载");
		btnD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dv.start();
			}
		});
	}
}