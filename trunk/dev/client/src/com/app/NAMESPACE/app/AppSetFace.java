package com.app.NAMESPACE.app;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.list.LocalImageList;
import com.app.NAMESPACE.list.RemoteImageList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AppSetFace extends AuthApp {
	
	GridView faceGridView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_face);
		
		
		
		Integer[] imageIds = {
			R.drawable.arrow_1,
			R.drawable.blog_1,
			R.drawable.body_1,
			R.drawable.bomb_r
		};
		String[] imageUrls = {
			C.web.base + "/faces/default/face_1.png",
			C.web.base + "/faces/default/face_2.png",
			C.web.base + "/faces/default/face_3.png",
			C.web.base + "/faces/default/face_4.png"
		};
		faceGridView = (GridView) this.findViewById(R.id.app_face_grid);
//		faceGridView.setAdapter(new LocalImageList(this, imageIds));
		faceGridView.setAdapter(new RemoteImageList(this, imageUrls));
		faceGridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				toast("select:"+position);
			}
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}