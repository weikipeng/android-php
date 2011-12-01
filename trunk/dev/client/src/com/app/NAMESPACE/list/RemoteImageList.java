package com.app.NAMESPACE.list;

import com.app.NAMESPACE.util.IOUtil;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RemoteImageList extends BaseAdapter {

	private Context context;
	private String[] imageUrls = {};
	
	public RemoteImageList (Context context, String[] imageUrls) {
		this.context = context;
		this.imageUrls = imageUrls;
	}
	
	@Override
	public int getCount() {
		return imageUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setPadding(10, 10, 10, 10);
		// get pic from remote
		Bitmap bitmap = IOUtil.getBitmapRemote(imageUrls[position]);
		imageView.setImageBitmap(bitmap);
		return imageView;
	}
	
}