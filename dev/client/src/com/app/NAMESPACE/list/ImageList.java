package com.app.NAMESPACE.list;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageList extends BaseAdapter {

	private Context context;
	private Integer[] mImageIds = {
			R.drawable.btn_minus,
			R.drawable.btn_default,
			R.drawable.btn_dialog,
			R.drawable.btn_dropdown
	};
	
	public ImageList (Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return mImageIds.length;
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
		imageView.setImageResource(mImageIds[position]);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return imageView;
	}
	
}