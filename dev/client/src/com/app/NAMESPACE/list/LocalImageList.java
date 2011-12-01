package com.app.NAMESPACE.list;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LocalImageList extends BaseAdapter {

	private Context context;
	private Integer[] imageIds = {};
	
	public LocalImageList (Context context, Integer[] imageIds) {
		this.context = context;
		this.imageIds = imageIds;
	}
	
	@Override
	public int getCount() {
		return imageIds.length;
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
		imageView.setImageResource(imageIds[position]);
		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return imageView;
	}
	
}