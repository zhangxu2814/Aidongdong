package com.kdcm.aidongdong.GoodsTools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.listviewTool.TitleProvider;
/**
 * 
 * @author zhangxu
 * 物品详情里面无法用这个
 *
 */
public class GoodsAdapter extends BaseAdapter implements TitleProvider {
	private static final int VIEW1 = 0;
	private static final int VIEW2 = 1;
	private static final int VIEW3=2;
	private final String[] names = { "宝贝详情", "图文详情","评价" };

	private LayoutInflater mInflater;

	public GoodsAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return names.length;
	}

	@Override
	public int getCount() {
		return  names.length;
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
		int view = getItemViewType(position);
		if (convertView == null) {
			switch (view) {
			case VIEW1:
				convertView = mInflater.inflate(R.layout.view_baobeixiangqing,
						null);
				break;
			case VIEW2:
				convertView = mInflater.inflate(R.layout.activity_tab_jieshou,
						null);
				break;
			case VIEW3:
				convertView = mInflater.inflate(R.layout.activity_tab_jieshou,
						null);
				break;
				
			}
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
	 */
	public String getTitle(int position) {
		return names[position];
	}

}
